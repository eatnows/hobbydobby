package com.hobbydobby.handler

import com.hobbydobby.domain.Role
import com.hobbydobby.domain.member.Member
import com.hobbydobby.service.member.MemberService
import com.hobbydobby.util.EncryptUtil
import com.hobbydobby.util.StringUtil
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class HobbyDobbyAuthProvider(
        var memberService: MemberService
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication? {
        if(authentication == null) {
            throw Exception("auth fail")
        }
        val email = authentication.principal as String
        if(!StringUtil.isEmail(email)) {
            return null
        }
        val member = memberService.getMemberByEmail(email) ?: return null
        var password = authentication.credentials as String
        password = EncryptUtil.encryptSHA512(password + member.salt)
        if(member.password != password) {
            return null
        }

        val grantedAuthorityList = ArrayList<GrantedAuthority>()

        grantedAuthorityList.add(SimpleGrantedAuthority(Role.MEMBER.role))

        return HobbyDobbyAuthentication(email, member.password, grantedAuthorityList, member)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.name == "org.springframework.security.authentication.UsernamePasswordAuthenticationToken"
    }
}

class HobbyDobbyAuthentication(principal: String, credentials: String, authorities: ArrayList<GrantedAuthority>, var member: Member) : UsernamePasswordAuthenticationToken(principal, credentials, authorities)