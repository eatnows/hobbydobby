var emailOk;
var nicknameOk;
var host = location.protocol+"//"+location.host;
var pwdCheck;
$(function(){
    // 자기소개 글자수 카운팅
    $("#introduce").keyup(function(){
        var count = $(this).val().length;
        $("#introCount").html(count+"/100");
    });


    // 비밀번호 확인과 비밀번호 같은 값인지 검사
    $("#password2").keyup(function(){
        var password = $("#password").val();
        var password2 = $(this).val();
        if(password === password2 || password2 === ""){
            // OK 이미지 추가

            $("#pwdinfo2").html("");
            pwdCheck = password === password2;
        } else {
            // X자 이미지 추가

            $("#pwdinfo2").html("<br><p style='font-size: 6pt; color: red;'>비밀번호가 맞지 않습니다.</p>");
            pwdCheck = false;
        }
    });
});

// signUp폼에서 넘어온 값을 ajax를 이용해서 graphql 쿼리 질의하기
function signUpAjax(){
    var email = $("#email").val();
    var password = $("#password").val();
    var nickname = $("#nickname").val();
    var name = $("#name").val();
    var birthday = $("#birth").val();
    var phone = $("#hp1").val()+$("#hp2").val()+$("#hp3").val();
    var address = $("#address").val();
    var introduce = $("#introduce").val();
    var queryA = 'mutation{signUpMember(request:{'
    +'introduce: "'+introduce+'", address: "'+address+'", birthday: "'+birthday+'", name: "'+name+'", phone: "'+phone+'", nickname: "'+nickname+'",'
    +'email: "'+email+'", password: "'+password+'" }){result, message}}';

    // 이메일 유효성 검사 함수 실행
    if(!emailOk){
        alert("이메일을 다시 확인해주세요.");
        $("#email").focus();
        return;
    }

    // 비밀번호 유효성 검사 함수 실행
    if(!checkPassword()){
        alert("비밀번호를 다시 확인해주세요.");
        $("#password").focus();
        return;
    }

    // 비빌번호 확인 값 비교
    if(!pwdCheck){
        alert("비밀번호가 틀립니다.");
        $("#password2").focus();
        return;
    }

  // 닉네임 중복체크 함수 실행
    if(!nicknameOk){
        alert("닉네임을 다시 확인해주세요.");
        $("#nickname").focus();
        return;
    } else if (nickname.search(/\s/) !== -1) {
        alert("닉네임에 빈칸은 사용할 수 없습니다.");
        $("#nickname").focus();
        return;
    }

    // 이름 유효성검사 실행
    if(!nameCheck()) {
        alert("이름을 확인해주세요.");
        $("#name").focus();
        return;
    }

    // 생년월일 유효성검사 실행
    if(!birthCheck()) {
        alert("생년월일을 확인해주세요.");
        $("#birth").focus();
        return;
    }


  // graphql 쿼리 질의하기
  $.post({
    url: host+"/graphql",
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
    if(password === "" || password === null){
        $("#pwdinfo").html("");
        return false;
        // 세가지 패턴을 가지는 8~25자 문자
    } else if(!/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/.test(password)){
        $("#pwdinfo").html("<br><p style='font-size: 6pt; color: red;'>숫자+영문+특수문자 조합으로 8자리 이상 사용해야 합니다.</p>");
        return false;
    }
    $("#pwdinfo").html("");
    return true;
}


// 이메일 유효성 검사와 중복체크
function checkEmail(){
    var email = $("#email").val();
    // (영문과숫자_.-)@(영문과 숫자).(영문과숫자)
    var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    if(email === ""){
        $("#emailinfo").html("");
        return false;
    } else if(!exptext.test(email)){
        $("#emailinfo").html("<br><p style='font-size: 6pt; color: red;'>이메일 형식이 맞지 않습니다.</p>");
        // 사용불가 이미지 추가
        return false;
    }
    duplicateEmailCheck(email);
}

// 이메일 중복체크 함수
function duplicateEmailCheck(email){
    var emailCheck = 'query{isValidEmail(email:"'+email+'")}';
    $.post({
        url: host+"/graphql",
        contentType:"application/json",
        data: JSON.stringify({
            query: emailCheck
         })
         }).done(function(response){
            // 중복된 아이디가 있으면 false 반환
            if(response.data.isValidEmail){
                // OK 이미지 추가
                // 임시 문구 대체
                $("#emailinfo").html("<br><p style='font-size: 6pt; color: green;'>사용가능한 이메일입니다.</p>");
                emailOk = true;
            } else if(email === null || email === "") {
                emailOk = false;
            } else {
                // 사용불가 이미지 추가
                // 임시 문구로 대체
                $("#emailinfo").html("<br><p style='font-size: 6pt; color: red;'>중복된 이메일입니다.</p>");
                emailOk = false;
            }
         });
}

// 닉네임 중복체크와 유효성 검사 함수
function duplicateNicknameCheck(){
    var nickname = $("#nickname").val();
    var nicknameCheck = 'query{isValidNickname(nickname:"'+nickname+'")}';
    // 영문,숫자,한글로 구성된 2~10 글자
    var exptext = /^[A-Za-z0-9가-힣]{2,10}$/;

    if(nickname === "" || nickname === null){
        $("#nicknameinfo").html("");
        return false;
    } else if(!exptext.test(nickname)){
        $("#nicknameinfo").html("<br><p style='font-size: 6pt; color: red;'>2~10자를 입력해주세요.(특수문자는 사용할 수 없습니다.)</p>");
        return false;
    }

    $.post({
        url: host+"/graphql",
        contentType:"application/json",
        data: JSON.stringify({
            query: nicknameCheck
         })
         }).done(function(response){
            // 중복된 아이디가 있으면 false를 반환
            if(response.data.isValidNickname){
                // OK 이미지 추가
                // 임시 문구 대체
                $("#nicknameinfo").html("<br><p style='font-size: 6pt; color: green;'>사용할 수 있는 닉네임입니다.</p>");
                nicknameOk = true;
            } else {
                // 사용불가 이미지 추가
                // 임시 문구 대체
                $("#nicknameinfo").html("<br><p style='font-size: 6pt; color: red;'>사용할 수 없는 닉네임입니다.</p>");
                nicknameOk = false;
            }
         });
}

// 생년월일 유효성 검사
function birthCheck(){
    var birth = $("#birth").val();
    // 숫자로 구성된 8자리 문자
    var exptext = /^[0-9]{8}$/;
    if(birth === "" || birth === null){
        $("#birthinfo").html("");
        return false;
    } else if(!exptext.test(birth)){
        $("#birthinfo").html("<br><p style='font-size: 6pt; color: red;'>생년월일 숫자 8자를 입력해주세요.</p>");
        return false;
    }
    $("#birthinfo").html("");
    return true;
}

// 이름 유효성 검사
function nameCheck(){
    var name = $("#name").val();
    // 영문자와 한글로 구성된 2글자 이상
    var exptext = /[A-Za-z가-힣]{2,}$/;

    if(name === "" || name === null ){
        $("#nameinfo").html("");
        return false;
    } else if(!exptext.test(name)){
        $("#nameinfo").html("<br><p style='font-size: 6pt; color: red;'>영문과 한글로 구성된 2글자 이상만 가능합니다.</p>");
        return false;
    } else {
        $("#nameinfo").html("");
    }
}









