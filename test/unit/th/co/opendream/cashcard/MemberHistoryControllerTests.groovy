package th.co.opendream.cashcard



import org.junit.*
import grails.test.mixin.*

@TestFor(MemberHistoryController)
@Mock(MemberHistory)
class MemberHistoryControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/memberHistory/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.memberHistoryInstanceList.size() == 0
        assert model.memberHistoryInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.memberHistoryInstance != null
    }

    void testSave() {
        controller.save()

        assert model.memberHistoryInstance != null
        assert view == '/memberHistory/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/memberHistory/show/1'
        assert controller.flash.message != null
        assert MemberHistory.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/memberHistory/list'


        populateValidParams(params)
        def memberHistory = new MemberHistory(params)

        assert memberHistory.save() != null

        params.id = memberHistory.id

        def model = controller.show()

        assert model.memberHistoryInstance == memberHistory
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/memberHistory/list'


        populateValidParams(params)
        def memberHistory = new MemberHistory(params)

        assert memberHistory.save() != null

        params.id = memberHistory.id

        def model = controller.edit()

        assert model.memberHistoryInstance == memberHistory
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/memberHistory/list'

        response.reset()


        populateValidParams(params)
        def memberHistory = new MemberHistory(params)

        assert memberHistory.save() != null

        // test invalid parameters in update
        params.id = memberHistory.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/memberHistory/edit"
        assert model.memberHistoryInstance != null

        memberHistory.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/memberHistory/show/$memberHistory.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        memberHistory.clearErrors()

        populateValidParams(params)
        params.id = memberHistory.id
        params.version = -1
        controller.update()

        assert view == "/memberHistory/edit"
        assert model.memberHistoryInstance != null
        assert model.memberHistoryInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/memberHistory/list'

        response.reset()

        populateValidParams(params)
        def memberHistory = new MemberHistory(params)

        assert memberHistory.save() != null
        assert MemberHistory.count() == 1

        params.id = memberHistory.id

        controller.delete()

        assert MemberHistory.count() == 0
        assert MemberHistory.get(memberHistory.id) == null
        assert response.redirectedUrl == '/memberHistory/list'
    }
}
