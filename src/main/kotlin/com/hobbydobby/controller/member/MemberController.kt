package com.hobbydobby.controller.member

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MemberController {
    @GetMapping("/member/signup")
    fun signUpPage(): String {
        return "member/signup"
    }

    // 이메일 중복체크

}