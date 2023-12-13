$(".go-back").on("click", function(e){
    window.location.href = '/login'
});

window.addEventListener("load", function(event) {
    event.preventDefault();
    const form = document.getElementById("initialPassword-form");

    form.addEventListener("submit", function(event) {
        if(form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        }else{
            event.preventDefault();

            FormDataToObj.getParameter("initialPassword-form").then(obj => {
                const userName = $("#initialPasswordUserName").val()
                const userEmail = $("#initialPasswordUserEmail").val()

                Http.syncGet(`/front/api/initialPassword?userName=${userName}&userEmail=${userEmail}`).then((res) => {
                    Util.alert(res.message).then(() => {
                        if(res['httpStatus'] === 200) window.location.href = '/login'
                    })
                })
            });

        }
    });
});

window.addEventListener("load", function(event) {
    event.preventDefault();
    const form = document.getElementById("searchId-form");

    form.addEventListener("submit", function(event) {
        if(form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        }else{
            event.preventDefault();

            FormDataToObj.getParameter("searchId-form").then(obj => {
                const userName = $("#searchUserIdUserName").val()
                const userEmail = $("#searchUserIdUserEmail").val()

                Http.syncGet(`/front/api/findUserId?userName=${userName}&userEmail=${userEmail}`).then((res) => {
                    Util.alert(res.message).then(() => {
                        if(res['httpStatus'] === 200) window.location.href = '/login'
                    })
                })
            });
        }
    });
});