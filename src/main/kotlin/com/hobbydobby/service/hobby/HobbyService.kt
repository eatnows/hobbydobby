package com.hobbydobby.service.hobby

import com.hobbydobby.domain.ResponseCode
import com.hobbydobby.domain.hobby.Hobby
import com.hobbydobby.repository.hobby.HobbyRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class HobbyService(
        private var hobbyRepository: HobbyRepository
) {
    /**
     * 신규 관심사 등록
     */
    fun registerNewHobby(hobby: Hobby): HashMap<String, Any> {
        val resultMap = HashMap<String, Any>()
        try {
            // 등록된 관심사 확인
            if(hobbyRepository.findByNameEquals(hobby.name) != null) {
                return resultMap.also {
                    it["result"] = "fail"
                    it["message"] = "Duplicate Hobby Request"
                    it["code"] = ResponseCode.HOBBY_NAME_DUPLICATE
                }
            }
            val hobbySave = hobbyRepository.save(hobby)
            return resultMap.also {
                it["result"] = "success"
                it["id"] = hobbySave.id.toString()
                it["code"] = ResponseCode.HOBBY_SUCCESS
            }
        } catch (exception : Exception) {
            return resultMap.also {
                it["result"] = "fail"
                it["message"] = exception.message?:""
                it["code"] = ResponseCode.HOBBY_EXCEPTION
            }
        }
    }
}