import spock.lang.Specification
import java.time.LocalDateTime

class TodoListSpec extends Specification {

    //Добавление задачи без конфликтов по времени:
    //Проверяется, что задача корректно добавляется, если нет временных пересечений.
    def "should add task without time conflicts"() {
        given: "A todo list and a task"
        def todoList = new TodoList()
        def task = new Task("Task 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))

        when: "We add the task"
        todoList.addTask(task)

        then: "The task is added successfully"
        todoList.tasks.size() == 1
        todoList.tasks[0].name == "Task 1"
    }

    //Добавление задачи с конфликтом по времени:
    //Проверяется, что задача не добавляется, если она пересекается по времени с существующей задачей.
    def "should not add task with conflicting time"() {
        given: "A todo list with an existing task"
        def todoList = new TodoList()
        def task1 = new Task("Task 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))
        todoList.addTask(task1)

        and: "Another task with conflicting time"
        def task2 = new Task("Task 2", LocalDateTime.of(2024, 9, 22, 11, 0), LocalDateTime.of(2024, 9, 22, 14, 0))

        when: "We try to add the conflicting task"
        todoList.addTask(task2)

        then: "The task is not added"
        todoList.tasks.size() == 1
        todoList.tasks[0].name == "Task 1"
    }

    //Добавление действия в рамках времени задачи:
    //Проверяется, что действие добавляется корректно, если его время соответствует времени задачи.
    def "should add action within task time range"() {
        given: "A task with a valid time range"
        def task = new Task("Task 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))

        and: "An action within the task time"
        def action = new Action("Action 1", LocalDateTime.of(2024, 9, 22, 10, 0))

        when: "We add the action"
        task.addAction(action)

        then: "The action is added successfully"
        task.actions.size() == 1
        task.actions[0].description == "Action 1"
    }

    //Добавление действия вне временных рамок задачи:
    //Проверяется, что действие не добавляется, если оно выходит за рамки времени задачи.
    def "should not add action outside of task time range"() {
        given: "A task with a time range"
        def task = new Task("Task 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))

        and: "An action outside the task time range"
        def action = new Action("Action 1", LocalDateTime.of(2024, 9, 22, 13, 0))

        when: "We try to add the action"
        task.addAction(action)

        then: "The action is not added"
        task.actions.size() == 0
    }

    //Триггер события, когда наступает время действия:
    //Проверяется, что событие корректно отображается в консоли, когда наступает время действия.
    def "should trigger event when action time arrives"() {
        given: "A task with an action"
        def task = new Task("Task 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))
        def action = new Action("Action 1", LocalDateTime.of(2024, 9, 22, 10, 0))
        task.addAction(action)

        and: "A todo list with the task"
        def todoList = new TodoList()
        todoList.addTask(task)

        when: "We check for events at the action's time"
        todoList.checkAndTriggerEvents(LocalDateTime.of(2024, 9, 22, 10, 0))

        then: "An event is triggered"
        output ==~ /Событие: Action 1 в 2024-09-22T10:00/
    }

    //Подсчет количества задач на определенную дату:
    //Проверяется корректный подсчет задач на заданную дату.
    def "should count tasks for specific date"() {
        given: "A todo list with two tasks"
        def todoList = new TodoList()
        def task1 = new Task("Task 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))
        def task2 = new Task("Task 2", LocalDateTime.of(2024, 9, 23, 9, 0), LocalDateTime.of(2024, 9, 23, 12, 0))
        todoList.addTask(task1)
        todoList.addTask(task2)

        when: "We count tasks for 22nd September 2024"
        def count = todoList.countTasksForDate(LocalDateTime.of(2024, 9, 22, 0, 0))

        then: "Only one task is found"
        count == 1
    }

    //Отображение занятых времен для определенной даты:
    //Проверяется корректный вывод списка занятых времен для определенной даты.
    def "should display busy times for specific date"() {
        given: "A todo list with tasks and actions"
        def todoList = new TodoList()
        def task = new Task("Task 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))
        def action1 = new Action("Action 1", LocalDateTime.of(2024, 9, 22, 10, 0))
        def action2 = new Action("Action 2", LocalDateTime.of(2024, 9, 22, 11, 0))
        task.addAction(action1)
        task.addAction(action2)
        todoList.addTask(task)

        when: "We fetch busy times for 22nd September 2024"
        def busyTimes = todoList.busyTimesForDate(LocalDateTime.of(2024, 9, 22, 0, 0))

        then: "The busy times are returned"
        busyTimes == [LocalDateTime.of(2024, 9, 22, 10, 0), LocalDateTime.of(2024, 9, 22, 11, 0)]
    }
}
