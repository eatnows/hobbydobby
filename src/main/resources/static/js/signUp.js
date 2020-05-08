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
  $.post({
    url: "http://localhost:8080/graphql",
    contentType:"application/json",
    data: JSON.stringify({
        query: queryA
     })
     }).done(function(response){
/*        console.log(response);
        console.log(response.data.signUpMember.message);
        console.log(response.data.signUpMember.result);*/
     });

}