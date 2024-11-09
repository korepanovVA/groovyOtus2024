/**
 * Пример конфигурационного файла для демонстрации работы DSL.
 * Содержит настройки окружения, свойств и mappings.
 */

import ConfigBuilder
import ConfigEnvironment

static void main(String[] args) {
// Создаем конфигурацию с использованием DSL
    def config = ConfigBuilder.configure {
        environment ConfigEnvironment.PROD         // Устанавливаем окружение
        configName "Production Config"             // Устанавливаем имя конфигурации

        // Добавляем свойства
        property "url", "https://prod.example.com"
        property "db_user", "prod_user"

        // Настраиваем mappings
        mappings {
            put "serviceA", "serviceAEndpoint"
            put "serviceB", "serviceBEndpoint"
        }
    }

    // Печатаем конфигурацию для проверки результата
    println config
}