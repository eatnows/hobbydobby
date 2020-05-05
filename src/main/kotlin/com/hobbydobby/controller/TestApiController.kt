package com.hobbydobby.controller

import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Controller

@GraphQLApi
@Controller
class TestApiController {

    @GraphQLQuery(name = "test")
    fun testApi(): String {
        return "dddd"
    }
}