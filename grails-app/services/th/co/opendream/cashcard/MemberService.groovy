package th.co.opendream.cashcard

class MemberService {

    def update(Member member) {
    	def properties = [
			identificationNumber: member.identificationNumber,
			firstname: member.firstname,
			lastname: member.lastname,
			dateCreated: member.dateCreated,
			lastUpdated: member.lastUpdated,
			gender: member.gender,
			telNo: member.telNo,
			address: member.address,
			balance: member.balance,
			member: member.save(),
			status: member.status,
    	]

    	new MemberHistory(properties).save()
    }
}
