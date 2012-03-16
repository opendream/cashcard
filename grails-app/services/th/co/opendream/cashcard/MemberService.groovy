package th.co.opendream.cashcard

class MemberService {

    def update(Member member) {
    	def __member__ = Member.get(member.id)
    	def properties = [
			identificationNumber: __member__.identificationNumber,
			firstname: __member__.firstname,
			lastname: __member__.lastname,
			dateCreated: __member__.dateCreated,
			lastUpdated: __member__.lastUpdated,
			gender: __member__.gender,
			telNo: __member__.telNo,
			address: __member__.address,
			balance: __member__.balance,
			member: member.save(),
			status: __member__.status,
    	]

    	new MemberHistory(properties).save()
    }
}
