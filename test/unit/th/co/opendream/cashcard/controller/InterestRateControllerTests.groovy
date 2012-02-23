package th.co.opendream.cashcard.controller

import th.co.opendream.cashcard.domain.InterestRate
import org.junit.*
import grails.test.mixin.*
import groovy.time.*
import static java.util.Calendar.*

@TestFor(InterestRateController)
@Mock(InterestRate)
class InterestRateControllerTests {
    def today
    @Before
    void setUp() {
        today = Calendar.instance
        today.set 2012, JANUARY, 1
        today = today.time
    }

    def populateValidParams(params) {
        assert params != null
        params["startDate"] = today
        params["rate"] = 7
    }

    void testIndex() {
        controller.index()
        assert "/interestRate/list" == response.redirectedUrl
    }

    void testList() {
        def model

        model = controller.list()

        assert model.interestRateInstanceList.size() == 0
        assert model.interestRateInstanceTotal == 0

        populateValidParams(params)
        mockDomain(InterestRate, [params])

        model = controller.list()

        assert model.interestRateInstanceList.size() == 1
        assert model.interestRateInstanceTotal == 1
    }

    void testCreate() {
       def model = controller.create()

       assert model.interestRateInstance != null
    }

    void testSaveEmptyParam() {
        controller.save()

        assert model.interestRateInstance != null
        assert view == '/interestRate/create'
    }

    void testSaveValid() {

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/interestRate/list'
        assert controller.flash.message != null
        assert InterestRate.count() == 1
    }

    void testSaveWithEmptyStartDate() {
        populateValidParams(params)
        params.startDate = ""

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('startDate')
    }


    void testSaveWithNullStartDate() {
        populateValidParams(params)
        params.startDate = null

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('startDate')
    }


    void testSaveWithNotUniqueStartDate() {
        populateValidParams(params)
        mockDomain(InterestRate, [params])

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('startDate')
    }


    void testSaveWithUniqueStartDate() {
        populateValidParams(params)
        mockDomain(InterestRate, [params])

        params.startDate = params.startDate.plus(8)

        controller.save()
        assert flash.message != null
    }

    void testSaveWithEmptyRate() {
        populateValidParams(params)
        params.rate = ""

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('rate')
    }

    void testSaveWithNullRate() {
        populateValidParams(params)
        params.rate = null

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('rate')
    }

    void testSaveWithMinusRate() {
        populateValidParams(params)
        params.rate = -1

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('rate')
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/interestRate/list'


        populateValidParams(params)
        def interestRate = new InterestRate(params)

        assert interestRate.save() != null

        params.id = interestRate.id

        def model = controller.edit()

        assert model.interestRateInstance == interestRate
    }

    void testUpdateWithInvalidId() {
        populateValidParams(params)
        mockDomain(InterestRate)
        assert InterestRate.count() == 0
        params.id = 1
        controller.update()
        assert flash.message != null
        response.redirectedUrl == '/interestRate/list'
    }

    void testUpdateWithOutdatedVersionNumber() {
        populateValidParams(params)
        mockDomain(InterestRate, [params])
        assert InterestRate.count() == 1

        params.version = -2
        params.id = 1

        controller.update()

        assert view == '/interestRate/edit'
        assert model.interestRateInstance != null
        assert model.interestRateInstance.errors.getFieldError('version')
    }

    void testUpdateWithInvalidParams() {
        populateValidParams(params)
        mockDomain(InterestRate)
        def interestRate = new InterestRate(params)

        assert interestRate.save() != null

        // test invalid parameters in update
        params.id = interestRate.id
        params.startDate = null
        controller.update()

        assert view == "/interestRate/edit"
        assert model.interestRateInstance != null
    }

    void testUpdateWithEmptyParams() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/interestRate/list'
    }

    void testValidUpdate() {
        mockDomain(InterestRate)

        populateValidParams(params)
        def interestRate = new InterestRate(params)

        assert interestRate.save() != null

        params.id = interestRate.id
        controller.update()

        assert response.redirectedUrl == "/interestRate/list"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/interestRate/list'

        response.reset()

        populateValidParams(params)
        def interestRate = new InterestRate(params)

        assert interestRate.save() != null
        assert InterestRate.count() == 1

        params.id = interestRate.id

        controller.delete()

        assert InterestRate.count() == 0
        assert InterestRate.get(interestRate.id) == null
        assert response.redirectedUrl == '/interestRate/list'
    }
}