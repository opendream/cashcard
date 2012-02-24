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
    
    void testUniqueKey() {
        def existingPolicy = new Policy(key: "creditline", value: '1000.00')
        mockForConstraintsTests(Policy, [existingPolicy])
        
        def policy = new Policy(key: "creditline", value: '200.00')
        
        assertFalse policy.validate()
        assert "unique" == policy.errors["key"]
    }
    
    void testFindPolicyValueByKey() {
        mockDomain(Policy, [
            [ 
                key: Policy.KEY_INTEREST_METHOD, 
                value: Policy.VALUE_COMPOUND
            ]
        ])
        
        assert Policy.VALUE_COMPOUND == Policy.valueOf(Policy.KEY_INTEREST_METHOD)
    }
}