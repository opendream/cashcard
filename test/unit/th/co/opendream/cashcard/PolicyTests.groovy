package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*


@TestFor(Policy)
class PolicyTests {

    void testConstraints() {
        mockForConstraintsTests(Policy, [])

        // validation should fail if both properties are null
        def policy = new Policy()
        assert !policy.validate()
        assert "nullable" == policy.errors["key"]
        assert "nullable" == policy.errors["value"]
    }

    void testFindPolicyValueByKey() {
        mockDomain(Policy, [
            [ key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_COMPOUND ]
        ])

        assert Policy.VALUE_COMPOUND == Policy.valueOf(Policy.KEY_INTEREST_METHOD)
    }

    void testStaticCompoundMethod() {
        mockDomain(Policy, [
            [ key: Policy.KEY_INTEREST_METHOD, value: Policy.VALUE_COMPOUND ]
        ])

        assertTrue Policy.isCompoundMethod()
    }
}