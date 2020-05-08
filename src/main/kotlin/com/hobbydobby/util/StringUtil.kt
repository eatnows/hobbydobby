package com.hobbydobby.util

import java.time.LocalDateTime

class StringUtil {
    companion object{
        /**
         * Email 형식 여부 체크
         */
        val isEmail : (String?) -> Boolean = {
            it?.matches(Regex("[^@]+@[^ \\t\\r\\n\\v\\f@]+[.][^ \\t\\r\\n\\v\\f@]+")) ?: false
        }
    }
}