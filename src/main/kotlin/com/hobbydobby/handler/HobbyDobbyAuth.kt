package com.hobbydobby.handler

import com.hobbydobby.domain.Role
import com.hobbydobby.domain.member.Member
import com.hobbydobby.service.member.MemberService
import com.hobbydobby.util.EncryptUtil
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HobbyDobbyAuthProvider(
        var memberService: MemberService
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication? {
        if(authentication == null) {
            throw Exception("auth fail")
        }
        val email = authentication.principal as String
        val member = memberService.getMemberByEmail(email) ?: throw UsernameNotFoundException("Not Exist User : $email")
        var password = authentication.credentials as String
        password = EncryptUtil.encryptSHA512(password + member.salt)
        if(member.lockYn) {
            throw LockedException("Lock User id : ${member.id}")
        }
        if(member.password != password) {
            throw BadCredentialsException("Password incorrect id : ${member.id}")
        }

        val grantedAuthorityList = ArrayList<GrantedAuthority>()

        grantedAuthorityList.add(SimpleGrantedAuthority(Role.MEMBER.role))

        return HobbyDobbyAuthentication(email, member.password, grantedAuthorityList, member)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.name == "org.springframework.security.authentication.UsernamePasswordAuthenticationToken"
    }
}

/**
 * login 성공시 실행하는 Handler
 */
@Component
class AuthSuccessHandler(
        var memberService: MemberService
) : SimpleUrlAuthenticationSuccessHandler() {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        response!!.status = HttpServletResponse.SC_OK
        val member = (authentication as HobbyDobbyAuthentication).member
        logger.info("Login Success id : ${member.id}")
        memberService.loginSuccess(member)
        redirectStrategy.sendRedirect(request, response, "/main")
    }
}

/**
 * login 실패시 실행하는 Handler
 */
@Component
class AuthFailureHandler(
        var memberService: MemberService
) : SimpleUrlAuthenticationFailureHandler() {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        response!!.status = HttpServletResponse.SC_UNAUTHORIZED
        logger.info("Login Fail" + exception?.message)
        val email = request?.getParameter("email")
        if(email != null) {
            memberService.loginFail(email)
        }
        request?.getRequestDispatcher("/login")?.forward(request,response)
    }
}

class HobbyDobbyAuthentication(principal: String, credentials: String, authorities: ArrayList<GrantedAuthority>, var member: Member) : UsernamePasswordAuthenticationToken(principal, credentials, authorities)