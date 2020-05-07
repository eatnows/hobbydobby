package com.hobbydobby.domain.member

/**
 * SignUp 요청에 대한 Response
 */
class MemberSingUpResponse(
        var result : String,
        var message : String = ""
)