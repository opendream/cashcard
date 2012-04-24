package th.co.opendream.cashcard

import grails.test.mixin.*
import org.junit.*


@TestFor(Policy)
@Mock(Company)
class PolicyTests {

    void testConstraints() {
        mockForConstraintsTests(Policy, [])

        // validation should fail if both properties are null
        def policy = new Policy()
        assert !policy.validate()
        assert "nullable" == policy.errors["key"]
        assert "nullable" == policy.errors["value"]
        assert "nullable" == policy.errors["company"]
    }
}