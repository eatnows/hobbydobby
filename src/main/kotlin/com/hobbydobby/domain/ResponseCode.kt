package com.hobbydobby.domain

/**
 * 결과코드
 */
enum class ResponseCode(var code : String, var apiName : ServiceName) {
    HOBBY_SUCCESS(code = "0", apiName = ServiceName.HOBBY),
    HOBBY_NAME_DUPLICATE(code = "1", apiName = ServiceName.HOBBY),
    HOBBY_INVALID_ID(code = "2", apiName = ServiceName.HOBBY),
    HOBBY_BOARD_ALREADY_EXIST(code = "3", apiName = ServiceName.HOBBY),
    HOBBY_EXCEPTION(code = "4", apiName = ServiceName.HOBBY)
}

/**
 * 서비스명
 */
enum class ServiceName {
    HOBBY
}