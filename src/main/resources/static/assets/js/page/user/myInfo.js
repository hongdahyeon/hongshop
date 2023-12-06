$(document).ready(function() {
    if(user.city.length == 0 || user.street.length == 0 || user.zipcode.length == 0) Util.alert("주소 정보를 입력해주세요.", 'w', 'w')

    newPassword.on('input', chckValidate)
    newPasswordChk.on('input', chckValidate)
})

function chckValidate(){
    const passwordVal = newPassword.val()
    const passwordChkVal = newPasswordChk.val()

    if(passwordVal && passwordChkVal) {
        if(passwordVal === passwordChkVal) newPasswordChk.removeClass('is-invalid')
        else newPasswordChk.addClass('is-invalid')
    }
}

/* 이메일 중복 확인 */
$("#check-Email").on("click", function(e){
    e.preventDefault();

    if(!changeEmail) {

        changeEmail = true
        emailChk = false
        $("#userEmail").prop('disabled', false)
        $("#check-Email").text("중복확인")

    }else if(changeEmail) {
        const userEmail = $("#userEmail").val()
        const originUserEmail = $("#originUserEmail").val()

        if(userEmail === originUserEmail) {
            Util.confirm('기존 이메일과 동일합니다. 그대로 사용하시겠습니까?').then((isOk) => {
                if (isOk)  {
                    $("#EmailChkMessage").html('')
                    $("#userEmail").prop('disabled', true)
                    $("#check-Email").prop('disabled', true)
                    emailChk = true
                }
            })
        }else {
            // check duplicate email

            Http.get(`/front/api/checkEmail?userEmail=${userEmail}`).then((res) => {
                if (!res.message) $("#EmailChkMessage").css('color', 'red').html("중복되는 이메일입니다.")
                else {
                    $("#EmailChkMessage").css('color', 'green').html("사용 가능한 이메일입니다.")
                    $("#userEmail").prop('disabled', true)
                    $("#check-Email").prop('disabled', true)
                    emailChk = true
                }
            })
        }
    }
})

$("#change-pwd").on("click", function(e) {
    e.preventDefault();
    changePwd = true
    $("#newPassword").prop("disabled", false);
    $("#newPasswordChk").prop("disabled", false);
})

window.addEventListener("load", function(event) {
    event.preventDefault();
    const form = document.getElementById("update-form");

    form.addEventListener("submit", function(event) {
        if(form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        }else{
            event.preventDefault();
            const pwd = newPassword.val()
            const pwdChk = newPasswordChk.val()

            if(changePwd &&(pwd.length === 0 || pwd !== pwdChk)) Util.alert("입력하신 비밀번호가 다릅니다.", 'w', 'w')
            else if(changeEmail && !emailChk) Util.alert("이메일 중복을 확인해주세요.", 'w', 'w')
            else {
                FormDataToObj.getParameter("update-form").then(obj => {
                    if(changeEmail) obj['userEmail'] = $("#userEmail").val()

                    Http.put(`/api/user`, obj).then((res) => {
                        if(res['httpStatus'] === 200) {
                            Util.alert("회원정보를 수정하였습니다.").then(() => {
                                window.location.href='/'
                            })
                        }else {
                            Util.alert("회원정보 수정을 실패했습니다.", 'w', 'w')
                        }
                    })
                })
            }
        }
    });
});