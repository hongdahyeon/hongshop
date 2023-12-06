$(document).ready(function(e) {
    feather.replace()
})

/* 리뷰 수정 버튼 이벤트 */
function editReview(id) {

    Http.get(`/api/review/${id}`).then((res) => {
        if(res['httpStatus'] === 200) {
            setReviewModal(res.message)
        }
    })
}

/* 리뷰 삭제 버튼 이벤트 */
function trashReview(id){
    Util.confirm("해당 리뷰를 삭제하시겠습니까?").then((isOk) => {
        if(isOk) {
            Http.delete(`/api/review/${id}`).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(`${res.message}`).then(() => {
                        window.location.href = `/user/review`
                    })
                }else {
                    Util.alert("해당 리뷰 삭제에 실패했습니다.", 'w', 'w')
                }
            })
        }
    })
}

/* 리뷰수정 버튼 이벤트 클릭 -> 리뷰 수정 모달 데이터로 채우기 */
function setReviewModal(data){
    reviewId = data['reviewId']                                             // 리뷰 id

    if(data['file']) {                                                      // file-container
        uploadFile.setFileGroupId(data['file'].id)
        uploadFile.setData(data['file']['fileList']).setFiles()
    }

    $("#productName").text(data['hongOrderDetailVO']['productName'])        // 주문 상품 이름
    $("#update-reviewContent").val(data['reviewContet'])                    // 리뷰 내용

    // review-rate-div
    const dom = $("#review-rate-div")
    dom.empty()

    let body = '';
    for(let i=5; i>=1; i--) {
        const checkedOrNot = (data['reviewStar'] == i) ? 'checked' : ''
        body += `<input type="radio" id="reviewStar${i}" name="reviewStar" value="${i}" ${checkedOrNot}/>
                     <label for="reviewStar${i}" title="text">${i} stars</label>`
    }

    dom.append(body)
    $("#review-update-modal").modal('show')
}

/* 리뷰 수정 모달 닫기 이벤트 -> 폼 validation 제거 , 파일 컨테이너 초기화 , 리뷰 내용 초기화 */
$("#close-review-update-modal-btn, #close-reviewUpdateModal").on('click', function(e){
    Util.confirm("작성중인 리뷰는 저장되지 않습니다. <br/> 리뷰작성을 취소하시겠습니까?").then((isOk) => {
        if(isOk) {
            uploadFile.domEmptyAndFileGroupIdNull();
            $("#update-reviewContent").val('')

            const form = document.getElementById("review-update-form")
            form.classList.remove("was-validated")      // validation 지우기

            $("#review-update-modal").modal('hide')
        }
    })
})

/* 리뷰 수정하기 form */
window.addEventListener("load", function() {
    const form = document.getElementById("review-update-form")
    form.addEventListener("submit", function(event) {
        if (form.checkValidity() === false) {
            event.preventDefault();
            form.classList.add("was-validated")
        } else {
            event.preventDefault();

            FormDataToObj.getParameter("review-update-form").then((obj) => {
                obj['deleteFile'] = uploadFile.getDeleteFile();
                if(uploadFile.getFileGroupId()) obj['fileGroupId'] = uploadFile.getFileGroupId()

                Http.put(`/api/review/${reviewId}`, obj).then((res) => {
                    if (res['httpStatus'] === 200) {
                        Util.alert(`${res.message}`).then(() => {
                            window.location.href = '/user/review'
                        })
                    } else {
                        Util.alert("해당 리뷰 수정에 실패했습니다.", 'w', 'w')
                    }
                })
            })
        }
    })
})

$('#btn-upload').on("click", function (e) {
    e.preventDefault();
    $('#fileUpload').click();
});

$("#fileUpload").on("change", function (e) {
    const selectedFile = e.target.files[0];
    const fileName = selectedFile.name

    const formData = new FormData();
    formData.append("file", selectedFile);
    if(uploadFile.getFileGroupId()) formData.append("fileGroupId", uploadFile.getFileGroupId());

    Http.filePost("/api/uploadFile", formData).then((res) => {
        if(res['httpStatus'] === 200) {
            uploadFile.setFileGroupId(res.message.fileGroupId)
            uploadFile.addFileList({'originalFileName': fileName, 'id': res.message.id})
        }
    })
});
