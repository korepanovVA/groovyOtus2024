/**
 * Класс для хранения и управления секцией `mappings`.
 * Представляет собой структуру данных, содержащую соответствие ключ-значение.
 */
class Mapping {
    Map<String, String> mappings = [:]

    // Метод для добавления пары ключ-значение в mappings
    void put(String key, String value) {
        mappings[key] = value
    }

    // Метод для отображения mappings в виде строки
    @Override
    String toString() {
        mappings.toString()
    }
}