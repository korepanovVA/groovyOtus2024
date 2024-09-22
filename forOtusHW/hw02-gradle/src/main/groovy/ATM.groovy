class ATM {
    // Ячейки для хранения банкнот по номиналу
    Map<Integer, List<Banknote>> banknoteCells = [:]

    // Метод для приема банкнот
    void deposit(Banknote banknote) {
        if (!banknoteCells.containsKey(banknote.denomination)) {
            banknoteCells[banknote.denomination] = []
        }
        banknoteCells[banknote.denomination] << banknote
        println "Deposited: $banknote"
    }

    // Метод для выдачи запрошенной суммы
    List<Banknote> withdraw(int amount) {
        List<Banknote> result = []
        Map<Integer, Integer> requiredBanknotes = [:]

        // Сортировка номиналов по убыванию
        def sortedDenominations = banknoteCells.keySet().sort { -it }

        for (denomination in sortedDenominations) {
            int count = Math.min((float)amount / denomination, (float)banknoteCells[denomination].size())
            if (count > 0) {
                requiredBanknotes[denomination] = count
                amount -= denomination * count
            }
        }

        if (amount > 0) {
            println "Error: Unable to dispense the requested amount."
            return []
        }

        requiredBanknotes.each { denomination, count ->
            result += banknoteCells[denomination].take(count)
            banknoteCells[denomination] = banknoteCells[denomination].drop(count)
        }

        println "Withdrawn: ${result.join(', ')}"
        return result
    }

    // Метод для отображения остатка денежных средств
    int getBalance() {
        banknoteCells.collect { denomination, banknotes ->
            denomination * banknotes.size()
        }.sum()
    }

    // Метод для отображения текущего состояния ячеек банкомата
    void displayATMState() {
        println "ATM state:"
        banknoteCells.each { denomination, banknotes ->
            println "$denomination: ${banknotes.size()} banknotes"
        }
        println "Total balance: ${getBalance()}"
    }
}

