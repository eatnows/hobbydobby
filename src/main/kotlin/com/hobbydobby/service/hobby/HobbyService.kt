package com.hobbydobby.service.hobby

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
    fun registerNewHobby(hobby: Hobby): HashMap<String, String> {
        try {
            val hobbySave = hobbyRepository.save(hobby)
            return HashMap<String, String>().also {
                it["result"] = "success"
                it["id"] = hobbySave.id.toString()
            }
        } catch (exception : Exception) {
            return HashMap<String, String>().also {
                it["result"] = "fail"
                it["message"] = exception.message?:""
            }
        }
    }
}