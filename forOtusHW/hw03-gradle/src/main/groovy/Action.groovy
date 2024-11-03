import java.time.LocalDateTime

// Модель класса Action (Действие)
class Action {
    String description
    LocalDateTime time
    Action(String description, LocalDateTime time) {
        this.description = description
        this.time = time
    }

    @Override
    String toString() {
        return "$description at $time"
    }
}