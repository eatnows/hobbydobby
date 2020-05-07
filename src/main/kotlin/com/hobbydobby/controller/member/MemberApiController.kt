package com.hobbydobby.controller.member

import com.hobbydobby.domain.member.MemberSignUpRequest
import com.hobbydobby.domain.member.MemberSignUpResponse
import com.hobbydobby.service.member.MemberService
import io.leangen.graphql.annotations.GraphQLMutation
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
}