<div class="subnav">
    <ul class="nav nav-pills">
		<li><a href="${createLink(controller:'member', action:'payment', params:[id:memberInstance.id])}">Pay</a></li>
		<li><a href="${createLink(controller:'member', action:'withdraw', params:[id:memberInstance.id])}">Withdraw</a></li>
		<li><a href="${createLink(controller:'member', action:'transaction', params:[id:memberInstance.id])}">View Transaction</a></li>
	</ul>
</div>
