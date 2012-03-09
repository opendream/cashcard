package th.co.opendream.cashcard

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser

import org.springframework.security.core.GrantedAuthority 
import org.springframework.security.core.userdetails.User

class CompanyUserDetails extends GrailsUser {

    final String companyName
    final Long companyId

    CompanyUserDetails(String username, 
        String password, 
        boolean enabled, 
        boolean accountNonExpired, 
        boolean credentialsNonExpired, 
        boolean accountNonLocked, 
        Collection<GrantedAuthority> authorities, 
        long id, String companyName, Long companyId) { 

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, id)

        this.companyName = companyName 
        this.companyId = companyId 
    } 
}