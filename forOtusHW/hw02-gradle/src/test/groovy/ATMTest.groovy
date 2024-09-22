import spock.lang.Specification;

public class ATMTest extends Specification {
    def "test deposit functionality"() {
        given: "an empty ATM"
        def atm = new ATM()

        when: "we deposit banknotes of different denominations"
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(50))
        atm.deposit(new Banknote(500))

        then: "the ATM should store the correct number of banknotes"
        atm.banknoteCells[100].size() == 1
        atm.banknoteCells[50].size() == 1
        atm.banknoteCells[500].size() == 1
        atm.getBalance() == 650
    }

    def "test withdraw functionality with exact amount"() {
        given: "an ATM with various banknotes"
        def atm = new ATM()
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(50))
        atm.deposit(new Banknote(500))

        when: "we withdraw an amount that can be exactly matched"
        def withdrawnBanknotes = atm.withdraw(750)

        then: "ATM should return correct banknotes and update balance"
        withdrawnBanknotes.size() == 4
        withdrawnBanknotes*.denomination.containsAll([500, 100, 100, 50])
        atm.getBalance() == 0
    }

    def "test withdraw functionality when amount cannot be matched"() {
        given: "an ATM with limited banknotes"
        def atm = new ATM()
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(50))

        when: "we try to withdraw an amount that cannot be matched"
        def withdrawnBanknotes = atm.withdraw(250)

        then: "ATM should not dispense any banknotes"
        withdrawnBanknotes.isEmpty()
        atm.getBalance() == 250
    }

    def "test withdraw functionality when ATM has insufficient funds"() {
        given: "an ATM with fewer funds than the requested amount"
        def atm = new ATM()
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(50))

        when: "we try to withdraw more than available funds"
        def withdrawnBanknotes = atm.withdraw(300)

        then: "ATM should not dispense any banknotes"
        withdrawnBanknotes.isEmpty()
        atm.getBalance() == 150
    }

    def "test get balance after multiple deposits"() {
        given: "an empty ATM"
        def atm = new ATM()

        when: "multiple deposits are made"
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(50))
        atm.deposit(new Banknote(500))
        atm.deposit(new Banknote(1000))

        then: "the balance should reflect the total of all deposits"
        atm.getBalance() == 1650
    }

    def "test display ATM state"() {
        given: "an ATM with a few banknotes"
        def atm = new ATM()
        atm.deposit(new Banknote(100))
        atm.deposit(new Banknote(50))

        when: "we display the ATM state"
        atm.displayATMState()

        then: "the state should show correct quantities and total balance"
        atm.banknoteCells[100].size() == 1
        atm.banknoteCells[50].size() == 1
        atm.getBalance() == 150
    }
}
