/**
 * Основной класс для настройки DSL-конфигурации.
 * Поддерживает секцию `mappings` и выбор окружения.
 */
class ConfigDsl {
    ConfigEnvironment environment      // Окружение конфигурации (TEST, DEV, PROD)
    Mapping mapping = new Mapping()    // Секция mappings, представляющая собой соответствие ключ-значение
    String configName                  // Имя конфигурации
    Map<String, String> properties = [:]  // Общие свойства конфигурации

    // Метод для установки окружения
    void environment(ConfigEnvironment env) {
        this.environment = env
    }

    // Метод для установки имени конфигурации
    void configName(String name) {
        this.configName = name
    }

    // Метод для добавления свойства
    void property(String key, String value) {
        properties[key] = value
    }

    // Метод для настройки секции mappings с использованием DSL
    void mappings(Closure closure) {
        closure.delegate = mapping
        closure()
    }

    // Метод для отображения объекта в виде строки
    @Override
    String toString() {
        "Environment: $environment, Config Name: $configName, Properties: $properties, Mappings: $mapping"
    }
}