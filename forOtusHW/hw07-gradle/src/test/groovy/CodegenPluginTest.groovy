import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

class CodegenPluginTest extends Specification {

    @TempDir
    File testProjectDir

    File buildFile

    def setup() {
        buildFile = new File(testProjectDir, "build.gradle")
        buildFile << """
            plugins {
                id 'codegen.codegen'
            }

            codegen {
                fieldValue = "Hello, World!"
                className = "GeneratedClass"
                outputDir = file("\$buildDir/generated/sources/codegen")
                packageName = "com.example.generated"
            }
        """
    }

    def "generateCode task should create a Java file with specified field and class"() {
        given:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments("generateCode", "--stacktrace")
                .withPluginClasspath()
                .build()

        expect:
        result.output.contains("BUILD SUCCESSFUL")

        and:
        File generatedFile = new File(testProjectDir, "build/generated/sources/codegen/com/example/generated/GeneratedClass.java")
        generatedFile.exists()
        generatedFile.text.contains("public class GeneratedClass")
        generatedFile.text.contains('public static final String VALUE = "Hello, World!";')
    }
}