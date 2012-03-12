package th.co.opendream.cashcard

import th.co.opendream.cashcard.Member.Gender
import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Member)
@Mock ([Member, TransactionService])
class MemberTests {

    def transactionService
    def txServiceControl

    @Before
    void setUp() {
        mockDomain(Member, [
            [id: 1, identificationNumber: "1111111111111", firstname: "Nat", lastname: "Weerawan", telNo: "0891278552", gender: "MALE", address: "11223445"],
            [id: 2, identificationNumber: "2222222222222", firstname: "Noomz", lastname: "Siriwat", telNo: "0811111111", gender: "MALE", address: "2222222"]
        ])

        transactionService = new TransactionService()
        txServiceControl = mockFor(TransactionService)
    }

    def generateFindBy(flag) {
        return { key ->
            if (key == Policy.KEY_CREDIT_LINE) {
                return [value: "2000.00"]
            }
            else {
                return [value: flag]
            }
        }
    }

    void testProperties() {
        def defaultProps = ["validationSkipMap", "gormPersistentEntity", "properties","id",
                            "gormDynamicFinders", "all", "attached", "class", "constraints", "version",
                            "validationErrorsMap", "errors", "mapping", "metaClass", "count"]
        def memberProps = ['identificationNumber', 'firstname', 'lastname', 'dateCreated', 'lastUpdated', 'gender', 'telNo', 'address', 'balance']

        def instanceProperties = Member.metaClass.properties*.name

        instanceProperties -= defaultProps

        assert 0 == (memberProps - instanceProperties).size()
    }

    void testValidateIdentificationNumber() {
        def field = 'identificationNumber'
        def existingMember = new Member(identificationNumber:'1234567890123',
                                        firstname:'firstname',
                                        lastname:'lastname',
                                        gender:Gender.MALE)
        mockForConstraintsTests(Member, [existingMember])

        def member = new Member()
        assert field == member.hasProperty(field)?.name

        assertFalse member.validate([field])
        assert "nullable" == member.errors[field]

        member.identificationNumber = '1234567890123'
        assertFalse member.validate([field])
        assert "unique" == member.errors[field]

        member.identificationNumber = '12345ก67890'
        assertFalse member.validate([field])
        assert "matches" == member.errors[field]

        member.identificationNumber = '1234556789012'
        assertTrue member.validate([field])
    }

    void testValidateFirstname() {
        def field = 'firstname'
        def member = new Member()
        mockForConstraintsTests(Member, [member])

        assert field == member.hasProperty(field)?.name
          assertFalse member.validate([field])
          member.firstname = 'firstname'
          assertTrue member.validate([field])
    }

    void testValidateLastname() {
        def field = 'lastname'
        def member = new Member()
        mockForConstraintsTests(Member, [member])

        assert field == member.hasProperty(field)?.name
        assertFalse member.validate([field])
        member.lastname = 'lastname'
        assertTrue member.validate([field])
    }


    void testValidateGender() {
        def field = 'gender'
        def member = new Member()
        mockForConstraintsTests(Member, [member])

        assert field == member.hasProperty(field)?.name
          assertFalse member.validate([field])
          member.gender = Member.Gender.MALE
          assertTrue member.validate([field])
    }

    void testValidateAddress() {
        def field = 'address'
        def member = new Member()
        mockForConstraintsTests(Member, [member])

        assert field == member.hasProperty(field)?.name
        assertFalse member.validate([field])
        member.address = 'address'
        assertTrue member.validate([field])
    }

    void testValidateTelNo() {
        def field = 'telNo'
        def member = new Member()
        mockForConstraintsTests(Member, [member])

        assert field == member.hasProperty(field)?.name
        assertTrue member.validate([field])

        member.telNo = '12345ก67890'
        assertFalse member.validate([field])
        assert "matches" == member.errors[field]

        member.telNo = '1234567890'
        assertTrue member.validate([field])
    }

    void testInitialBalance() {
        def field = 'balance'
        def member = new Member()
        mockForConstraintsTests(Member, [member])

        assert field == member.hasProperty(field)?.name
        assert 0.00 == member.balance
        assert BigDecimal == member.balance.class
        assertTrue member.validate([field])
    }


   void testCallWithdraw() {
        txServiceControl.demand.withdraw(1..1) { member, amount -> true }

        def m1 = Member.get(1)
        m1.transactionService = txServiceControl.createMock()
        m1.withdraw(100.00)

        txServiceControl.verify()
    }

    void testCanWithdraw() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def m1 = Member.get(1)
        assert m1.canWithdraw(100.00) == true
    }

    void testCanWithdrawWithExceedBalance() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def m1 = Member.get(1)
        assert m1.canWithdraw(3000.00) == false
    }

    void testCanWithdrawWithNegativeAmount() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)
        def m1 = Member.get(1)
        assert m1.canWithdraw(-100.00) == false
    }

    void testRemainingCreditAmountWithNonCompoundInterest() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def m1 = Member.get(1)

        m1.balance = 100.00
        m1.interest = 10.00

        assert m1.getRemainingFinancialAmount() == 1890.00

        m1.balance = 200.00
        m1.interest = 20.00

        assert m1.getRemainingFinancialAmount() == 1780.00
    }

    void testRemainingCreditAmountWithCompoundInterest() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        def m1 = Member.get(1)

        m1.balance = 110.00
        m1.interest = 10.00

        assert m1.getRemainingFinancialAmount() == 1890.00

        m1.balance = 220.00
        m1.interest = 20.00

        assert m1.getRemainingFinancialAmount() == 1780.00
    }

    void testGetInterest() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)
        def m1 = Member.get(1)

        m1.interest = 10.00

        assert m1.getInterest() == 10.00
    }

    void testGetTotalDebtWithCompoundInterest() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        def m1 = Member.get(1)

        m1.balance = 110.00
        m1.interest = 10.00

        assert m1.getTotalDebt() == 110.00
    }

    void testGetTotalDebtWithNonCompoundInterest() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def m1 = Member.get(1)

        m1.balance = 100.00
        m1.interest = 10.00

        assert m1.getTotalDebt() == 110.00
    }

    void testPayWithOverDebt() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def m1 = Member.get(1)
        m1.balance = 100.00
        m1.interest = 10.00
        def change = m1.pay(200.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert change == 90.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        m1.balance = 110.00
        m1.interest = 10.00
        change = m1.pay(200.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert change == 90.00

        /* Balance 200.00 */

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 200.00
        m1.interest = 23.00
        change = m1.pay(500.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert change == 277.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        m1.balance = 223.00
        m1.interest = 23.00
        change = m1.pay(500.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
        assert change == 277.00
    }


    void testPayWithAllDebt() {
        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        def m1 = Member.get(1)
        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(110.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(110.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00

        /* Balance 200.00 */

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(223.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(223.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
    }

    void testPayWithPartialDebt() {
        def m1 = Member.get(1)

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(90.00)

        assert m1.balance == 20.00
        assert m1.interest == 0.00


        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(90.00)

        assert m1.balance == 20.00
        assert m1.interest == 0.00

        /* Balance 200.00 */

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(123.00)

        assert m1.balance == 100.00
        assert m1.interest == 0.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(223.00)

        assert m1.balance == 0.00
        assert m1.interest == 0.00
    }

    void testPayWithFullInterest() {
        def m1 = Member.get(1)

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(10.00)

        assert m1.balance == 100.00
        assert m1.interest == 0.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)
        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(10.00)

        assert m1.balance == 100.00
        assert m1.interest == 0.00

        /* Balance 200.00 */

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(23.00)

        assert m1.balance == 200.00
        assert m1.interest == 0.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)
        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(23.00)

        assert m1.balance == 200.00
        assert m1.interest == 0.00

    }

    void testPayWithPartialInterest() {
        def m1 = Member.get(1)

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 100.00
        m1.interest = 10.00
        m1.pay(3.00)

        assert m1.balance == 100.00
        assert m1.interest == 7.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)
        m1.balance = 110.00
        m1.interest = 10.00
        m1.pay(3.00)

        assert m1.balance == 107.00
        assert m1.interest == 7.00

        /* Balance 200.00 */

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_NON_COMPOUND)

        m1.balance = 200.00
        m1.interest = 23.00
        m1.pay(3.00)

        assert m1.balance == 200.00
        assert m1.interest == 20.00

        Policy.metaClass.static.findByKey = generateFindBy(Policy.VALUE_COMPOUND)

        m1.balance = 223.00
        m1.interest = 23.00
        m1.pay(3.00)

        assert m1.balance == 220.00
        assert m1.interest == 20.00
    }
}