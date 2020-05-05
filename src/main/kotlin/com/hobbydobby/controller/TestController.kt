package com.hobbydobby.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController {
    @GetMapping("/index")
    fun goIndex(): String {
        return "index"
    }
}