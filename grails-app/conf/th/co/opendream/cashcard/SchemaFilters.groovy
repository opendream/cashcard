package th.co.opendream.cashcard

class SchemaFilters {
    def springSecurityService
    def schemaService

    def filters = {
        switchSchema(controller:'*', action:'*') {
            before = {
                def companyId = springSecurityService?.principal?.companyId

                if (companyId) {
                    schemaService.with(schema)
                }
            }
        }
    }
}
