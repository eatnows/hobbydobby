package com.hobbydobby.controller.member

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/member")
class MemberController {
    @GetMapping("/signup")
    fun signUpPage(): String {
        return "member/signup"
    }
}