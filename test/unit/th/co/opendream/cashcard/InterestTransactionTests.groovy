package th.co.opendream.cashcard
    
import grails.test.mixin.*
import org.junit.*

@TestFor(InterestTransaction)
class InterestTransactionTests {
    
    void testNullContraints() {
        mockForConstraintsTests(InterestTransaction, [])
            
        def interest = new InterestTransaction()
        assertFalse interest.validate()
        
        assert "nullable" == interest.errors["amount"]
        assert "nullable" == interest.errors["date"]
        assert "nullable" == interest.errors["txType"]
        assert "nullable" == interest.errors["member"]
        assert "nullable" == interest.errors["fee"]
        assert "nullable" == interest.errors["interest"]
    }
    
    void testDefaultCodeInitValue() {
        def interest = new InterestTransaction()
        
        interest.validate()
        assert interest.code == "INT"
    }
    
    void testBalanceConstraint() {
        mockForConstraintsTests(InterestTransaction, [])
            
        def interest = new InterestTransaction(
            amount: 1000.00,
            date: new Date(),
            txType: TransactionType.CREDIT,
            member: new Member(),
            fee: 200.00,
            interest: 801.00
        )
        
        assertFalse interest.validate()
        assert "BalanceNotEqual" == interest.errors["fee"]
    }
    
    void testAmountSholdNotBeZero() {
        mockForConstraintsTests(InterestTransaction, [])
            
        def interest = new InterestTransaction(
            amount: 0.00,
            date: new Date(),
            txType: TransactionType.CREDIT,
            member: new Member(),
            fee: 0.00,
            interest: 0.00
        )
        
        assertFalse interest.validate()
        assert "notValidAmount" == interest.errors["amount"]
        
        interest.amount = -10.00
        assertFalse interest.validate()
        assert "notValidAmount" == interest.errors["amount"]
    }
    
}