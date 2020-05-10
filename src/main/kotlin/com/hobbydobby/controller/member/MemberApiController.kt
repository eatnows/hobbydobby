package com.hobbydobby.controller.member

import com.hobbydobby.domain.member.MemberSignUpRequest
import com.hobbydobby.domain.member.MemberSignUpResponse
import com.hobbydobby.service.member.MemberService
import com.hobbydobby.util.StringUtil
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Controller

/**
 * 회원관련된 api
 */
@GraphQLApi
@Controller
class MemberApiController(
        var memberService: MemberService
) {

    /**
     * 기초적인 회원가입 api
     */
    @GraphQLMutation(name = "signUpMember", description = "회원가입시 사용")
    fun singUp(request : MemberSignUpRequest): MemberSignUpResponse {
        val result = memberService.signUp(request.toEntity())
        return MemberSignUpResponse(result = result["result"]?:"fail", message = result["message"]?:"")
    }

    /**
     * Email 중복체크 true일 경우 사용가능
     */
    @GraphQLQuery(name = "isValidEmail", description = "이메일 중복체크")
    fun isValidEmail(email : String) = if(StringUtil.isEmail(email)) {
        memberService.isValidEmail(email)
    } else {
        false
    }

    /**
     * 닉네임 중복체크 true일 경우 사용가능
     */
    @GraphQLQuery(name = "isValidNickname", description = "닉네임 중복체크")
    fun isValidNickname(nickname : String) = if(nickname.length < 4 || nickname.length > 20) {
        false
    } else {
        memberService.isValidNickname(nickname)
    }
}