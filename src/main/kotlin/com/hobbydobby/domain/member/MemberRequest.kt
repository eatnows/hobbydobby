package com.hobbydobby.domain.member

import com.hobbydobby.repository.member.Member

/**
 * 회원가입에 대한 Request
 */
class MemberSingUpRequest(
        var password : String = "",
        var userId : String  = ""
) {
    // MemberSingUpRequest -> Member
    fun toEntity() = Member(password = this.password, userId = this.userId)
}