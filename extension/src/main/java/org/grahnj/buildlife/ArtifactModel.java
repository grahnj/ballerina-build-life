package org.grahnj.buildlife;

public class ArtifactModel {

    private static ArtifactModel instance = new ArtifactModel();

    private String name;
    private String suffix;
    private String extension;

    private ArtifactModel() {

    }

    public static ArtifactModel getInstance() {
        return instance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFileName() {
        return this.name + "." + this.suffix + "." + this.extension;
    }
}