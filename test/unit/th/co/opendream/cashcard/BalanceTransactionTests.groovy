package th.co.opendream.cashcard
    
import grails.test.mixin.*
import org.junit.*

@TestFor(BalanceTransaction)
class BalanceTransactionTests {
    
    void testNullContraints() {
        mockForConstraintsTests(BalanceTransaction, [])
            
        def balance = new BalanceTransaction()
        assertFalse balance.validate()
        
        assert "nullable" == balance.errors["amount"]
        assert "nullable" == balance.errors["date"]
        assert "nullable" == balance.errors["txType"]
        assert "nullable" == balance.errors["member"]
        assert "nullable" == balance.errors["activity"]
        assert "nullable" == balance.errors["net"]
        assert "nullable" == balance.errors["remainder"]
    }

    void testDefaultCodeInitValue() {
        def balance = new BalanceTransaction()
        balance.activity = ActivityType.PAYMENT
        balance.validate()
        assert balance.code == "PAY"

        balance = new BalanceTransaction()
        balance.activity = ActivityType.WITHDRAW
        balance.validate()
        assert balance.code == "WDR"
    }

    void testAmountSholdNotBeZero() {
        mockForConstraintsTests(BalanceTransaction, [])
            
        def balance = new BalanceTransaction(
            amount: 0.00,
            date: new Date(),
            txType: TransactionType.DEBIT,
            member: new Member(),
            activity: ActivityType.PAYMENT,
            net: 0.00,
            remainder: 0.00
        )
        
        assertFalse balance.validate()
        assert "notValidAmount" == balance.errors["amount"]
        
        balance.amount = -10.00
        assertFalse balance.validate()
        assert "notValidAmount" == balance.errors["amount"]
    }

    void testAmountConstraint() {
        mockForConstraintsTests(BalanceTransaction, [])
            
        def balance = new BalanceTransaction(
            amount: 1000.75,
            date: new Date(),
            txType: TransactionType.DEBIT,
            member: new Member(),
            activity: ActivityType.PAYMENT,
            net: 1000.73,
            remainder: 0.03
        )
        
        assertFalse balance.validate()
        assert "RemainderAmountNotEqual" == balance.errors["remainder"]

        balance = new BalanceTransaction(
            amount: 1000.75,
            date: new Date(),
            txType: TransactionType.DEBIT,
            member: new Member(),
            activity: ActivityType.PAYMENT,
            net: 1000.73,
            remainder: 0.02
        )
        
        assertTrue balance.validate()
        assertNull balance.errors["remainder"]
    }
    
}