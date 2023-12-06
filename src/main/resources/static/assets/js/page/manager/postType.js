/* 게시판 보러가기 버튼 클릭 이벤트 */
$(".post-page-btn").on("click", function(e){
    const postTypeId = $(this).val()
    const postUseAt = $(this).attr("data-useAt")
    if(postUseAt === 'N') Util.alert(`해당 게시판은 사용여부가 N이기 때문에 게시판URL이 생성되지 않았습니다. <br/> 사용여부를 Y로 변경해주세요.`, 'w', 'w')
    else {
        window.location.href = `/bbs/${postTypeId}`
    }
})

/* 게시판 삭제 버튼 클릭 이벤트 */
$(".post-delete-btn").on("click", function(e){
    const postTypeId = $(this).val()
    const postLen = $(this).attr("data-num")

    if(postLen > 0) Util.alert("해당 게시판은 하위에 게시글이 있기 때문에 삭제가 불가능합니다.", 'w', 'w')
    else {
        Util.confirm("해당 게시판을 삭제하시겠습니까?").then((isOk) => {
            if (isOk) {
                Http.delete(`/api/type/${postTypeId}`).then((res) => {
                    if (res['httpStatus'] === 200) {
                        Util.alert(`${res.message}`).then(() => {
                            window.location.href = '/manager/postType'
                        })
                    }
                })
            }
        })
    }
})

/* 게시판 수정 버튼 클릭 이벤트 */
$(".post-update-btn").on("click", function(e){
    const postTypeId = $(this).val()
    Http.get(`/api/type/${postTypeId}`).then((res) => {
        if(res['httpStatus'] === 200) {
            setPostUpdateModal(res.message)
        }
    })
})

/* 게시판 수정 버튼 클릭 시, 해당 게시판 정보로 모달 채우고 모달 띄우기 */
function setPostUpdateModal(data) {
    postTypeId = data['postTypeId']
    postType = data['postType']
    $("#update-postName").val(data['postName'])
    $("#update-orderNum").val(data['orderNum'])
    $(`#update-${data['postType']}`).prop('checked', true)
    $(`#update-useAt-${data['useAt']}`).prop('checked', true)
    $("#update-postType-modal").modal('show')
}

/* 게시판 생성 버튼 클릭 이벤트 */
$("#new-postType").on("click", function(e){
    $("#new-postType-modal").modal('show')
})

/* 게시판 생성 모달 -> 취소, x 버튼 클릭 이벤트 */
$("#cancel-new-postType-btn, #close-new-postType-modal-btn").on("click", function(e){
    Util.confirm('작성중인 내용은 저장되지 않습니다. <br/> 작성을 취소하시겠습니까?').then((isOk) => {
        if(isOk) $("#new-postType-modal").modal('hide')
    })
})

/* 게시판 수정 모달 -> 취소, x 버튼 클릭 이벤트 */
$("#cancel-update-postType-btn, #close-update-postType-modal-btn").on("click", function(e){
    Util.confirm('작성중인 내용은 저장되지 않습니다. <br/> 작성을 취소하시겠습니까?').then((isOk) => {
        if(isOk) $("#update-postType-modal").modal('hide')
    })
})

/* 게시판 유형 추가 */
window.addEventListener("load", function() {
    const form = document.getElementById("new-postType-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("new-postType-form").then((obj) => {
                Http.post(`/api/type`, obj).then((res) => {
                    if(res['httpStatus'] === 200) {
                        Util.alert("게시판 유형이 추가되었습니다.").then(() => {
                            window.location.href='/manager/postType'
                        })
                    }else {
                        Util.alert("해당 게시판 유형 추가에 실패하였습니다.", 'w', 'w')
                    }
                })
            })
        }
    })
})

/* 게시판 유형 수정 */
window.addEventListener("load", function() {
    const form = document.getElementById("update-postType-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("update-postType-form").then((obj) => {

                if(postType === 'QNA' && (postType !== obj['postType'])){
                    Util.confirm(`만약 QNA 게시판에서 ${obj['postType']} 게시판 유형으로 변경하시면 <br/> 해당 게시글에 달린 댓글을 볼수 없습니다. <br/> 그래도 변경하시겠습니까?`).then((isOk) => {
                        if(isOk) {

                            Http.put(`/api/type/${postTypeId}`, obj).then((res) => {
                                if (res['httpStatus'] === 200) {
                                    Util.alert(res.message).then(() => {
                                        window.location.href = '/manager/postType'
                                    })
                                } else {
                                    Util.alert("해당 게시판 유형 수정에 실패하였습니다.", 'w', 'w')
                                }
                            })
                        }
                    })
                }else {

                    Http.put(`/api/type/${postTypeId}`, obj).then((res) => {
                        if (res['httpStatus'] === 200) {
                            Util.alert(res.message).then(() => {
                                window.location.href = '/manager/postType'
                            })
                        } else {
                            Util.alert("해당 게시판 유형 수정에 실패하였습니다.", 'w', 'w')
                        }
                    })
                }
            })
        }
    })
})