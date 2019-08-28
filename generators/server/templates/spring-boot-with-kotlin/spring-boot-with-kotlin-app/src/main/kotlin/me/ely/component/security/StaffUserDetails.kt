package me.ely.component.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class StaffUserDetails(
        val id: Int,
        val uname: String,
        val pwd: String,
        val enabled: Boolean,
        val accountNonExpired: Boolean,
        val accountNonLocked: Boolean,
        val credentialsNonExpired: Boolean,
        val authorities: MutableList<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        return authorities
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun getUsername(): String {
        return uname
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun getPassword(): String {
        return pwd
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

}

