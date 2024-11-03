package codegen

import org.gradle.api.Project

class CodegenPluginExtension {
    String fieldValue
    String className
    File outputDir
    String packageName

    CodegenPluginExtension(Project project) {
        // Задание значений по умолчанию
        fieldValue = "Default Value"
        className = "GeneratedClass"
        outputDir = project.file("${project.buildDir}/generated/sources")
        packageName = "com.example.generated"
    }
}
