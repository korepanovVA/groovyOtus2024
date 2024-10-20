import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder
import groovy.xml.XmlUtil

class EmployeeProcessor {

    // Метод для скачивания JSON или использования локального файла
    String downloadJsonOrFallback(String url, String localFilePath) {
        try {
            println "Пытаюсь скачать файл по URL: $url"
            def jsonText = new URL(url).text
            println "Файл успешно скачан"
            return jsonText
        } catch (Exception e) {
            println "Не удалось скачать файл по URL: $url. Использую локальный файл $localFilePath"
            return new File(localFilePath).text
        }
    }

    // Метод для парсинга JSON и генерации HTML
    String parseJsonToHtml(String jsonContent) {
        def json = new JsonSlurper().parseText(jsonContent)
        def writer = new StringWriter()
        def html = new MarkupBuilder(writer)

        html.div {
            json.each { employee ->
                div(id: 'employee') {
                    p(employee.name)
                    p(employee.age)
                    p(employee.secretIdentity)
                    ul(id: 'powers') {
                        employee.powers.each { power ->
                            li(power)
                        }
                    }
                }
            }
        }
        return writer.toString()
    }

    // Метод для преобразования JSON в XML
    String parseJsonToXml(String jsonContent) {
        def json = new JsonSlurper().parseText(jsonContent)
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        xml.employees {
            json.each { employee ->
                employeeNode {
                    name(employee.name)
                    age(employee.age)
                    secretIdentity(employee.secretIdentity)
                    powers {
                        employee.powers.each { power ->
                            powerNode(power)
                        }
                    }
                }
            }
        }
        return XmlUtil.serialize(writer.toString())
    }

    // Метод для сохранения результата в файл
    void saveToFile(String content, String fileName) {
        // Создаем родительскую директорию, если она не существует
        File file = new File(fileName)
        file.parentFile.mkdirs() // Создает директории, если их нет
        file.text = content
        println "Результат сохранен в файл: $fileName"
    }
}