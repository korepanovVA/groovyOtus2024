import java.time.LocalDateTime

// Менеджер задач TodoList
class TodoList {
    List<Task> tasks = []

    // Добавление задачи с проверкой на пересечение времени
    void addTask(Task task) {
        if (isTimeSlotAvailable(task.startTime, task.endTime)) {
            tasks << task
        } else {
            println "Ошибка: Время занято для задачи $task.name."
        }
    }

    // Удаление задачи
    void removeTask(Task task) {
        tasks.remove(task)
    }

    // Проверка свободного времени
    boolean isTimeSlotAvailable(LocalDateTime startTime, LocalDateTime endTime) {
        !tasks.any { task ->
            (task.startTime.isBefore(endTime) && task.endTime.isAfter(startTime))
        }
    }

    // Отображение количества задач на конкретную дату
    int countTasksForDate(LocalDateTime date) {
        tasksForDate(date).size()
    }

    // Отображение busy-time на определенную дату
    List<LocalDateTime> busyTimesForDate(LocalDateTime date) {
        tasksForDate(date).collectMany { task ->
            task.actions.collect { it.time }
        }
    }

    // Получение задач на конкретную дату
    List<Task> tasksForDate(LocalDateTime date) {
        tasks.findAll { task ->
            task.startTime.toLocalDate() == date.toLocalDate()
        }
    }

    // Триггер событий в указанное время
    void checkAndTriggerEvents(LocalDateTime currentTime) {
        tasks.each { task ->
            task.actions.findAll { action ->
                action.time == currentTime
            }.each { action ->
                println Event.triggerEvent(action)
            }
        }
    }

    // Отображение списка задач на конкретную дату
    void displayTasksForDate(LocalDateTime date) {
        tasksForDate(date).each { task ->
            task.with {
                println "Задача: $name"
                actions.each { action ->
                    println "  Действие: $action"
                }
            }
        }
    }
}