$(function(){
    // 자기소개 글자수 카운팅
    $("#introduce").keyup(function(){
        var count = $(this).val().length;
        $("#introCount").html(count+"/100");
    });
});

// signUp폼에서 넘어온 값을 ajax를 이용해서 graphql 쿼리 질의하기
function signUpAjax(){
    var email = $("#email").val();
    var password = $("#password").val();
    var nickname = $("#nickname").val();
    var name = $("#name").val();
    var birthday = $("#birth").val();
    var phone = $("#hp").val();
    var address = $("#address").val();
    var introduce = $("#introduce").val();
    var queryA = 'mutation{signUpMember(request:{'
    +'introduce: "'+introduce+'", address: "'+address+'", birthday: "'+birthday+'", name: "'+name+'", phone: "'+phone+'", nickname: "'+nickname+'",'
    +'email: "'+email+'", password: "'+password+'" }){result, message}}';

    // 이메일 유효성 검사 함수 실행
    if(checkEmail() === false || email === null || email === ""){
        alert("이메일을 다시 확인해주세요.");
        $("#email").focus();
        return;
    }

    // 비밀번호 유효성 검사 함수 실행
    if(!checkPassword() || password === null || password === ""){
        alert("비밀번호를 다시 확인해주세요.");
        $("#password").focus();
        return;
    }

  // 닉네임 중복체크 함수 실행
    if(duplicateNicknameCheck === false|| nickname === null || nickname === ""){
        alert("닉네임을 다시 확인해주세요.");
        $("#nickname").focus();
        return;
    } else if (nickname.search(/\s/) != -1) {
        alert("닉네임에 빈칸은 사용할 수 없습니다.");
        $("nickname").focus();
        return;
    }



  $.post({
    url: "http://localhost:8080/graphql",
    contentType:"application/json",
    data: JSON.stringify({
        query: queryA
     })
     }).done(function(response){
        var result = response.data.signUpMember.result;
        if(result === "success"){
            alert("회원가입되었습니다.");
            location.href="/index";
        } else if(result === "fail"){
            alert("회원가입에 실패하였습니다.");
        }
     });
}

// 비밀번호 유효성 검사
function checkPassword(){
    var password = $("#password").val();
    if(!/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/.test(password)){
        $("#pwdinfo").html("<br><p style='font-size: 6pt;'>숫자+영문+특수문자 조합으로 8자리 이상 사용해야 합니다.</p>");
        //console.log("숫자+영문+특수문자 조합으로 8자리 이상 사용해야 합니다.")
        return false;
    } else {
        $("#pwdinfo").html("");
        return true;
    }
}

// 이메일 유효성 검사와 중복체크
function checkEmail(){
    var email = $("#email").val();
    var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    if(!exptext.test(email)){
        $("#emailinfo").html("<br><p style='font-size: 6pt;'>이메일 형식이 맞지 않습니다.</p>");
        // 사용불가 이미지 추가

        return false;
    } else {
        $("#emailinfo").html("");
        duplicateEmailCheck(email);
    }
}

// 이메일 중복체크 함수
function duplicateEmailCheck(email){
    var emailCheck = 'query{isValidEmail(email:"'+email+'")}';
    $.post({
        url: "http://localhost:8080/graphql",
        contentType:"application/json",
        data: JSON.stringify({
            query: emailCheck
         })
         }).done(function(response){
            // 중복된 아이디가 있으면 false 반환
            //console.log(response.data.isValidEmail);
            if(response.data.isValidEmail){
                // OK 이미지 추가

                console.log("사용가능한 이메일입니다.");
                return true;
            } else {
                // 사용불가 이미지 추가

                console.log("이미 존재하는 이메일입니다.");
                return false;
            }
         });
}

// 닉네임 중복체크 함수
function duplicateNicknameCheck(){
    var nickname = $("#nickname").val();
    var nicknameCheck = 'query{isValidNickname(nickname:"'+nickname+'")}';
    var exptext = /^[A-Za-z0-9가-힣]+$/;

    if(!exptext.test(nickname)){
        $("#nicknameinfo").html("<br><p style='font-size: 6pt;'>2~10자를 입력해주세요.(특수문자는 사용할 수 없습니다.)</p>");
        console.log("숫자영문한글만 가능");
        return false;
    } else {
        $("#nicknameinfo").html("");
    }

    $.post({
            url: "http://localhost:8080/graphql",
            contentType:"application/json",
            data: JSON.stringify({
                query: nicknameCheck
             })
             }).done(function(response){
                // 중복된 아이디가 있으면 false를 반환
                console.log(response);
                console.log(response.data.isValidNickname);
                if(response.data.isValidNickname){
                    // OK 이미지 추가

                    console.log("사용가능한 닉네임입니다.");
                    return true;
                } else {
                    // 사용불가 이미지 추가

                    console.log("이미 존재하는 닉네임입니다.");
                    return false;
                }
             });
}






