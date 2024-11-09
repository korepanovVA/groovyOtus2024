import java.time.LocalDateTime

// Модель класса Event (Событие)
class Event {
    String message
    LocalDateTime eventTime

    Event(String message, LocalDateTime eventTime) {
        this.message = message
        this.eventTime = eventTime
    }

    static Event triggerEvent(Action action) {
        new Event("Событие: ${action.description} в ${action.time}", action.time)
    }

    @Override
    String toString() {
        return message
    }
}