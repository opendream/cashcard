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
      params["endDate"] = today.plus(5)
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

        params.endDate = params.endDate.plus(10)

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('startDate')
    }


    void testSaveWithUniqueStartDate() {

        populateValidParams(params)
        mockDomain(InterestRate, [params])

        params.startDate = params.startDate.plus(8)
        params.endDate = params.endDate.plus(10)

        controller.save()
        assert flash.message != null
    }

    void testSaveWithEmptyEndDate() {
        populateValidParams(params)
        params.endDate = ""

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('endDate')
    }


    void testSaveWithNullEndDate() {
        populateValidParams(params)
        params.endDate = null

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('endDate')
    }


    void testSaveWithNotUniqueEndDate() {

        populateValidParams(params)
        mockDomain(InterestRate, [params])

        params.startDate = params.startDate.minus(1)

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('endDate')
    }

    void testSaveWithUniqueEndDate() {

        populateValidParams(params)
        mockDomain(InterestRate, [params])

        params.startDate = params.startDate.minus(1)
        params.endDate = params.endDate.plus(10)

        controller.save()
        assert flash.message != null
    }

    void testSaveWithInvalidDateRange() {
        populateValidParams(params)
        params.endDate = params.endDate.minus(10)

        controller.save()
        assert model.interestRateInstance.errors.getFieldError('endDate')
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

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/interestRate/list'

        response.reset()


        populateValidParams(params)
        def interestRate = new InterestRate(params)

        assert interestRate.save() != null

        // test invalid parameters in update
        params.id = interestRate.id
        params.startDate = today
        params.endDate = today

        controller.update()

        assert view == "/interestRate/edit"
        assert model.interestRateInstance != null

        interestRate.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/interestRate/list"
        assert flash.message != null

        //test outdated version number
        response.reset()
        interestRate.clearErrors()

        populateValidParams(params)
        params.id = interestRate.id
        params.version = -1
        controller.update()

        assert view == "/interestRate/edit"
        assert model.interestRateInstance != null
        assert model.interestRateInstance.errors.getFieldError('version')
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
