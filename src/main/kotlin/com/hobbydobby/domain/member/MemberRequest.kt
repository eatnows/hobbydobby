package com.hobbydobby.domain.member

/**
 * 회원가입에 대한 Request
 */
class MemberSignUpRequest(
        var email : String  = "",
        var password : String = "",
        var name : String = "",
        var nickname : String = "",
        var birthday : String = "",
        var phone : String = "",
        var address : String = "",
        var introduce : String = ""
) {
    // MemberSingUpRequest -> Member
    fun toEntity() = Member(
            email = this.email,
            password = this.password,
            name  = this.name,
            nickname = this.nickname,
            birthday = this.birthday,
            phone = this.phone,
            address = this.address,
            introduce = this.introduce
    )
}