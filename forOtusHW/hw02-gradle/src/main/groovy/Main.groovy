static void main(String[] args) {
  // Пример использования
  def atm = new ATM()

// Депозит банкнот
  atm.deposit(new Banknote(100))
  atm.deposit(new Banknote(100))
  atm.deposit(new Banknote(50))
  atm.deposit(new Banknote(50))
  atm.deposit(new Banknote(500))
  atm.deposit(new Banknote(1000))

// Отображение состояния ATM
  atm.displayATMState()

// Попытка снять 750 (удачная)
  atm.withdraw(750)

// Попытка снять 1800 (ошибка)
  atm.withdraw(1800)

// Остаток средств
  println "Current balance: ${atm.getBalance()}"

// Отображение состояния ATM после операций
  atm.displayATMState()
}