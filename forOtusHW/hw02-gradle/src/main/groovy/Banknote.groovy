class Banknote {
    Integer denomination // Номинал банкноты

    Banknote(int denomination) {
        this.denomination = denomination
    }

    // Переопределение оператора сравнения
    boolean equals(Banknote other) {
        return this.denomination == other.denomination
    }

    // Переопределение оператора сложения
    Banknote plus(Banknote other) {
        return new Banknote(this.denomination + other.denomination)
    }

    String toString() {
        return "$denomination"
    }
}
