import spock.lang.Specification

/**
 * Тест для проверки корректности работы DSL с использованием Spock.
 */
class ConfigDslTest extends Specification {

    def "DSL должна корректно настраивать конфигурацию для тестового окружения"() {
        given: "Создание тестовой конфигурации"
        def config = ConfigBuilder.configure {
            environment ConfigEnvironment.TEST     // Устанавливаем окружение TEST
            configName "Test Config"               // Устанавливаем имя конфигурации
            property "url", "https://test.example.com"  // Добавляем свойство
            mappings {
                put "serviceA", "testServiceAEndpoint"  // Добавляем элемент в mappings
            }
        }

        expect: "Конфигурация должна содержать указанные значения"
        config.environment == ConfigEnvironment.TEST
        config.configName == "Test Config"
        config.properties["url"] == "https://test.example.com"
        config.mapping.mappings["serviceA"] == "testServiceAEndpoint"
    }

    def "DSL должна поддерживать настройку окружения и секции mappings для окружения prod"() {
        given: "Создание конфигурации для окружения prod"
        def config = ConfigBuilder.configure {
            environment ConfigEnvironment.PROD          // Устанавливаем окружение PROD
            configName "Production Config"              // Устанавливаем имя конфигурации
            property "url", "https://prod.example.com"  // Добавляем свойство
            property "db_user", "prod_user"             // Добавляем другое свойство
            mappings {
                put "serviceA", "prodServiceAEndpoint"  // Добавляем элемент в mappings
                put "serviceB", "prodServiceBEndpoint"
            }
        }

        expect: "Конфигурация должна содержать значения для окружения PROD"
        config.environment == ConfigEnvironment.PROD
        config.configName == "Production Config"
        config.properties["url"] == "https://prod.example.com"
        config.properties["db_user"] == "prod_user"
        config.mapping.mappings["serviceA"] == "prodServiceAEndpoint"
        config.mapping.mappings["serviceB"] == "prodServiceBEndpoint"
    }
}