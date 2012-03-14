package th.co.opendream.cashcard



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(CompanyTagLib)
class CompanyTagLibTests {

    void testCompanyTagLibRender() {
        CompanyTagLib.metaClass.springSecurityService = [
            principal: [
                companyName: "Opendream"
            ]
        ]

        assertEquals "Opendream", new CompanyTagLib().name().toString()
    }
}
