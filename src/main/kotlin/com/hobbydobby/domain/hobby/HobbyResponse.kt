package com.hobbydobby.domain.hobby

class RegisterNewHobbyResponse(
    var result : String,
    var id : Int?,
    var message : String,
    var code : String
)

class GetHobbyListByNameResponse(
    var code : String,
    var list : List<Hobby>,
    var result : String,
    var message : String = ""
)

class ConfirmHobbyBoardResponse(
    var code : String,
    var result : String,
    var message : String = ""
)