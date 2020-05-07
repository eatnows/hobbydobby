package com.hobbydobby.controller.member

import com.hobbydobby.domain.member.MemberSingUpRequest
import com.hobbydobby.domain.member.MemberSingUpResponse
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
    @GraphQLMutation(name = "singUpMember", description = "회원가입시 사용")
    fun singUp(request : MemberSingUpRequest): MemberSingUpResponse {
        val result = memberService.signUp(request.toEntity())
        return MemberSingUpResponse(result = result["result"]?:"fail", message = result["message"]?:"")
    }
}