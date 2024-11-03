package codegen

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

// Задача, которая генерирует Java-класс с заданными параметрами
abstract class CodegenTask extends DefaultTask {

    @Input
    abstract Property<String> getFieldValue()

    @Input
    abstract Property<String> getClassName()

    @OutputDirectory
    abstract DirectoryProperty getOutputDir()

    @Input
    abstract Property<String> getPackageName()

    @TaskAction
    void generateCode() {
        // Создаем директорию пакета
        def packageDir = new File(outputDir.get().asFile, packageName.get().replace('.', '/'))
        packageDir.mkdirs()

        // Создаем файл для сгенерированного класса
        def generatedClass = new File(packageDir, "${className.get()}.java")
        generatedClass.text = """
            package ${packageName.get()};

            public class ${className.get()} {
                public static final String VALUE = "${fieldValue.get()}";
            }
        """.stripIndent()  // Генерация Java-класса
        // Выводим сообщение для отладки
        println "Generated class at: ${generatedClass.absolutePath}"
    }
}
