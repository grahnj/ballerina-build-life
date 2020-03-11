import ballerina/config;
# This record defines the fields of the @hello:Greeting annotation. 
#
# + name - The project name
# + suffix - The suffix to include after the name, e.g. 1.0.2-SNAPSHOT.9384773. Defaults to project version.
# + extension - The file extension, e.g. .jar. Defaults to .jar
public type ArtifactConfiguration record {|
    string name;
    string suffix = string`${config:getAsString("project.version")}`;
    string extension = ".jar";
|};

# Define an annotation named `Greeting`. Its type is `HelloConfiguration` and it can be
# attached to functions. 
public annotation ArtifactConfiguration ArtifactConfig on service, function;