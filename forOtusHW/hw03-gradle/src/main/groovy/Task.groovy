import java.time.LocalDateTime

// Модель класса Task (Задача)
class Task {
    String name
    LocalDateTime startTime
    LocalDateTime endTime
    List<Action> actions = []

    Task(String name, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name
        this.startTime = startTime
        this.endTime = endTime
    }

    // Добавление действия с проверкой времени
    void addAction(Action action) {
        if (action.time.isBefore(startTime) || action.time.isAfter(endTime)) {
            println "Ошибка: Действие выходит за рамки времени выполнения задачи $name."
        } else {
            actions << action
        }
    }

    // Удаление действия
    void removeAction(Action action) {
        actions.remove(action)
    }

    // Редактирование действий
    void editActions(List<Action> newActions) {
        actions = newActions.findAll { action ->
            action.time.isAfter(startTime) && action.time.isBefore(endTime)
        }
    }

    @Override
    String toString() {
        return "Задача: $name с $startTime по $endTime\nДействия:\n" + actions.collect { it.toString() }.join("\n")
    }
}