import groovy.json.JsonSlurper
import spock.lang.Specification
import spock.lang.Subject

class EmployeeProcessorSpec extends Specification {

    @Subject
    EmployeeProcessor processor = new EmployeeProcessor()

    def "должен скачать JSON из URL"() {
        given: "Действительный URL, который возвращает JSON"
        String url = "https://api.example.com/employees"

        and: "Имитация ответа URL"
        def jsonResponse = '[{"name": "John Doe", "age": 30, "secretIdentity": "Superman", "powers": ["flight", "strength"]}]'
        def httpClient = Mock(HttpClient)
        httpClient.get(url) >> jsonResponse

        when: "JSON загружается"
        String jsonContent = processor.downloadJsonOrFallback(url, "local_employees.json")

        then: "Содержимое должно соответствовать ожидаемому JSON"
        jsonContent == jsonResponse
    }

    def "должен читать локальный JSON файл, если URL недоступен"() {
        given: "Неверный URL"
        String url = "https://invalid-url.com/employees"
        String localJson = '{"name": "Jane Doe", "age": 25, "secretIdentity": "Wonder Woman", "powers": ["strength", "wisdom"]}'

        and: "Создание локального файла для резервного варианта"
        def localFile = new File("local_employees.json")
        localFile.text = localJson

        when: "JSON загружается"
        String jsonContent = processor.downloadJsonOrFallback(url, localFile.path)

        then: "Содержимое должно соответствовать локальному JSON"
        jsonContent == localJson

        cleanup:
        localFile.delete() // Удаляем файл после теста
    }

    def "должен корректно преобразовать JSON в HTML"() {
        given: "Пример содержимого JSON"
        String jsonContent = '[{"name": "John Doe", "age": 30, "secretIdentity": "Superman", "powers": ["flight", "strength"]}]'

        when: "Парсинг JSON в HTML"
        String htmlContent = processor.parseJsonToHtml(jsonContent)

        then: "Сгенерированный HTML должен содержать правильные значения"
        htmlContent.contains('<p>John Doe</p>')
        htmlContent.contains('<p>30</p>')
        htmlContent.contains('<p>Superman</p>')
        htmlContent.contains('<li>flight</li>')
        htmlContent.contains('<li>strength</li>')
    }

    def "должен корректно преобразовать JSON в XML"() {
        given: "Пример содержимого JSON"
        String jsonContent = '[{"name": "Jane Doe", "age": 25, "secretIdentity": "Wonder Woman", "powers": ["strength", "wisdom"]}]'

        when: "Парсинг JSON в XML"
        String xmlContent = processor.parseJsonToXml(jsonContent)

        then: "Сгенерированный XML должен содержать правильные значения"
        xmlContent.contains('<name>Jane Doe</name>')
        xmlContent.contains('<age>25</age>')
        xmlContent.contains('<secretIdentity>Wonder Woman</secretIdentity>')
        xmlContent.contains('<powerNode>strength</powerNode>')
        xmlContent.contains('<powerNode>wisdom</powerNode>')
    }
}
