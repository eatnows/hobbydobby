package com.hobbydobby.domain

/**
 * 결과코드
 */
enum class ResponseCode(var code : String, var apiName : ServiceName) {
    HOBBY_SUCCESS(code = "0", apiName = ServiceName.HOBBY),
    HOBBY_NAME_DUPLICATE(code = "1", apiName = ServiceName.HOBBY),
    HOBBY_EXCEPTION(code = "2", apiName = ServiceName.HOBBY)
}

/**
 * 서비스명
 */
enum class ServiceName {
    HOBBY
}