/**
 * Класс-строитель для создания и настройки объекта ConfigDsl с использованием DSL.
 */
class ConfigBuilder {
    // Метод configure создает объект ConfigDsl и применяет к нему настройки из замыкания (Closure)
    static ConfigDsl configure(Closure closure) {
        ConfigDsl config = new ConfigDsl()
        closure.delegate = config
        closure()
        return config
    }
}