package org.grahnj.buildlife;

import org.ballerinalang.compiler.plugins.AbstractCompilerPlugin;
import org.ballerinalang.compiler.plugins.SupportedAnnotationPackages;
import org.ballerinalang.model.elements.PackageID;
import org.ballerinalang.model.tree.AnnotationAttachmentNode;
import org.ballerinalang.model.tree.ServiceNode;
import org.ballerinalang.util.diagnostic.Diagnostic;
import org.ballerinalang.util.diagnostic.DiagnosticLog;
import org.wso2.ballerinalang.compiler.tree.BLangAnnotationAttachment;
import org.wso2.ballerinalang.compiler.tree.expressions.BLangRecordLiteral;
import org.wso2.ballerinalang.compiler.tree.expressions.BLangRecordLiteral.BLangRecordKeyValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SupportedAnnotationPackages(
        value = "grahnj/buildlife:0.1.0"
)
public class ArtifactPlugin extends AbstractCompilerPlugin {

    private final String NAME_PARAM = "name";
    private final String SUFFIX_PARAM = "suffix";
    private final String EXTENSION_PARAM ="extension";

    private DiagnosticLog dlog;

    @Override
    public void init(DiagnosticLog diagnosticLog) {
        this.dlog = diagnosticLog;
    }

    @Override
    public  void process(ServiceNode serviceNode, List<AnnotationAttachmentNode> annotations) {
        for (AnnotationAttachmentNode annotation: annotations) {
            if (!"ArtifactConfig".equals(annotation.getAnnotationName().getValue())) {
                continue;
            }

            List<BLangRecordKeyValue> fields = ((BLangRecordLiteral) ((BLangAnnotationAttachment) annotation).expr).getKeyValuePairs();

            fields.forEach(x -> {
                if (x.getKey().toString().equals(NAME_PARAM)) {
                    ArtifactModel.getInstance().setName(x.getValue().toString());
                }

                if (x.getKey().toString().equals(SUFFIX_PARAM)) {
                    ArtifactModel.getInstance().setSuffix(x.getValue().toString());
                }

                if (x.getKey().toString().equals(EXTENSION_PARAM)) {
                    ArtifactModel.getInstance().setSuffix(x.getValue().toString());
                }
            });
        }
    }

    @Override
    public void codeGenerated(PackageID packageID, Path binaryPath) {
        File buildlife = Paths.get("target", "buildlife").toFile();
        Path artifact = Paths.get("target", "buildlife", ArtifactModel.getInstance().getFileName());

        if (buildlife.exists()) {
            if (buildlife.delete()) {
                try {
                    Files.copy(binaryPath, artifact);
                } catch (IOException e) {
                    dlog.logDiagnostic(Diagnostic.Kind.ERROR, null, e.getMessage());
                }
            }
        }
    }
}