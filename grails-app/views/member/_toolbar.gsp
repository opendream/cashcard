<div class="subnav">
    <ul class="nav nav-pills">
        <li><a href="${createLink(controller:'member', action:'show', params:[id:memberInstance.id])}">ข้อมูล</a></li>
		<li><a href="${createLink(controller:'member', action:'payment', params:[id:memberInstance.id])}"><g:message code="member.toolbar.pay"></g:message></a></li>
		<li><a href="${createLink(controller:'member', action:'withdraw', params:[id:memberInstance.id])}"><g:message code="member.toolbar.withdraw"></g:message></a></li>
		<li><a href="${createLink(controller:'member', action:'transaction', params:[id:memberInstance.id])}"><g:message code="member.toolbar.viewTransaction"></g:message></a></li>
	</ul>
</div>
