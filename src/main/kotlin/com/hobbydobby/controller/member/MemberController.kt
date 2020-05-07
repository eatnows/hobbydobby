package com.hobbydobby.controller.member

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MemberController {
    @GetMapping("/member/signUp")
    fun signUpPage(): String {
        return "member/signup"
    }
}