package codegen

import org.gradle.api.Plugin
import org.gradle.api.Project

class CodegenPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // Создание расширения
        def extension = project.extensions.create("codegen", CodegenPluginExtension, project.objects)

        // Регистрация задачи
        project.tasks.register("generateCode", CodegenTask) { task ->
            task.fieldValue = extension.fieldValue
            task.className = extension.className
            task.outputDir = extension.outputDir
            task.packageName = extension.packageName
        }

        // Установка зависимости задачи от компиляции
        project.afterEvaluate {
            project.sourceSets.main.java.srcDir(extension.outputDir)
            project.tasks.named("compileJava").configure {
                dependsOn "generateCode"
            }
        }
    }
}
