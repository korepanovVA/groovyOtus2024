import java.time.LocalDateTime

static void main(String[] args) {
  // Пример использования
  def todoList = new TodoList()

// Создаем задачи
  def task1 = new Task("Задача 1", LocalDateTime.of(2024, 9, 22, 9, 0), LocalDateTime.of(2024, 9, 22, 12, 0))
  def task2 = new Task("Задача 2", LocalDateTime.of(2024, 9, 22, 13, 0), LocalDateTime.of(2024, 9, 22, 16, 0))

// Создаем действия для задач
  def action1 = new Action("Выполнить действие 1", LocalDateTime.of(2024, 9, 22, 10, 0))
  def action2 = new Action("Выполнить действие 2", LocalDateTime.of(2024, 9, 22, 11, 0))
  def action3 = new Action("Выполнить действие 3", LocalDateTime.of(2024, 9, 22, 14, 0))

// Добавляем действия в задачи
  task1.addAction(action1)
  task1.addAction(action2)
  task2.addAction(action3)

// Добавляем задачи в список дел
  todoList.addTask(task1)
  todoList.addTask(task2)

// Вывод информации
  println "Количество задач на 22 сентября 2024 года: ${todoList.countTasksForDate(LocalDateTime.of(2024, 9, 22, 0, 0))}"
  println "\nЗанятые времена на 22 сентября 2024 года:"
  println todoList.busyTimesForDate(LocalDateTime.of(2024, 9, 22, 0, 0))

  println "\nСписок задач на 22 сентября 2024 года:"
  todoList.displayTasksForDate(LocalDateTime.of(2024, 9, 22, 0, 0))

// Триггер событий в указанное время
  println "\nТриггер событий на 22 сентября 2024 года в 10:00"
  todoList.checkAndTriggerEvents(LocalDateTime.of(2024, 9, 22, 10, 0))
}