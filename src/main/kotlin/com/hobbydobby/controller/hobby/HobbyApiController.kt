package com.hobbydobby.controller.hobby

import com.hobbydobby.domain.ResponseCode
import com.hobbydobby.domain.hobby.Hobby
import com.hobbydobby.domain.hobby.RegisterNewHobbyResponse
import com.hobbydobby.service.hobby.HobbyService
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Controller

/**
 * 관심사 관련 Api 컨트롤러
 */
@GraphQLApi
@Controller
class HobbyApiController(
        var hobbyService: HobbyService
) {
    @GraphQLMutation(name = "registerNewHobby", description = "신규 취미 등록")
    fun registerNewHobby(hobbyName : String): RegisterNewHobbyResponse {
        val resultMap = hobbyService.registerNewHobby(Hobby(name = hobbyName))
        return RegisterNewHobbyResponse(
                result = (resultMap["result"] as String),
                message = (resultMap["message"] as String?)?:"",
                id = (resultMap["id"] as String?)?.toIntOrNull(),
                code = (resultMap["code"] as ResponseCode).code)
    }
}