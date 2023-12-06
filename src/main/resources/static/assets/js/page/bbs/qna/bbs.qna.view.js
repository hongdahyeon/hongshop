if(post['file']) setFileList(post['file']['fileList']);

$(document).ready(function(){
    feather.replace()

    /* 목록버튼 클릭 이벤트 */
    $("#list-btn").on("click", function(e){
        window.location.href = `/bbs/${post['typeId']}`
    })

    /* 수정버튼 클릭 이벤트 */
    $("#edit-btn").on("click", function(e){
        window.location.href = `/bbs/edit/${post['id']}`
    })

    /* 답변 추가 모달 -> 저장버튼 클릭 이벤트 */
    $("#save-answer-btn").on("click", function(e){
        const content = $("#save-answer").val()
        const hongPostId = post['id']
        const obj = { content, hongPostId }

        Http.post(`/api/answer`, obj).then((res) => {
            if(res['httpStatus'] === 200){
                Util.alert("답변이 등록되었습니다.").then(() => {
                    $("#saveAnswerModal").modal("hide")
                    window.location.href = `/bbs/view/${post['id']}`
                })
            }
        })
    })

    /* 답변 수정 모달 -> 저장버튼 클릭 이벤트 */
    $("#edit-answer-btn").on("click", function(e) {
        const content = $("#edit-answer").val()
        const hongPostId = post['id']
        const obj = { content, hongPostId }

        Http.put(`/api/answer/${answerId}`, obj).then((res) => {
            if(res['httpStatus'] === 200){
                Util.alert("답변이 수정되었습니다.").then(() => {
                    $("#editAnswerModal").modal("hide")
                    window.location.href = `/bbs/view/${post['id']}`
                })
            }
        })
    })

    /* 답변 작성하기 버튼 클릭 이벤트 */
    $("#show-modalAnswer").on("click", function(e) {
        $("#saveAnswerModal").modal("show")
    })


    /* 게시글 삭제버튼 클릭 이벤트 */
    $("#delete-btn").on("click", function(e) {
        Util.confirm("삭제 하시겠습니까?").then((isOk) => {
            if(isOk) {
                Http.delete(`/api/post/${post['id']}`).then((res) => {
                    if (res['httpStatus'] === 200) {
                        Util.alert("게시글이 삭제되었습니다.").then(() => {
                            window.location.href = `/bbs/${post['typeId']}`
                        })
                    }
                })
            }
        })
    })

})

function setFileList (file = []) {
    const dom = $("#download-container")
    dom.empty()

    file.forEach((value, i) => {
        const body =
            `
                    <li style="margin-top: 10px;">
                        <a href="#" onclick="Http.fileDownload(${value.id})">
                            <i data-feather="download" class="front-medium-2"></i>
                        </a>
                        ${value['originalFileName']}
                    </li>
                    `
        dom.append(body)
    });
    feather.replace()
}

/* 댓글 삭제버튼 클릭 이벤트 */
function deleteAnswer(id) {
    Util.confirm("삭제 하시겠습니까?").then((isOk) => {
        if(isOk) {
            Http.delete(`/api/answer/${id}`).then((res) => {
                if (res['httpStatus'] === 200) {
                    Util.alert("답변이 삭제되었습니다.").then(() => {
                        window.location.href = `/bbs/view/${post['id']}`
                    })
                }
            })
        }
    })
}

/* 댓글 수정버튼 클릭 이벤트 */
function editAnswer(id) {
    Http.get(`/api/answer/${id}`).then((res) => {
        if(res['httpStatus'] === 200){
            $("#edit-answer").val(res['message']['content'])
            answerId = res['message']['id']
            $("#editAnswerModal").modal("show")
        }
    })
}

ClassicEditor
    .create(document.querySelector('#content'), {
        readOnly: true,
    })
    .then(editor => {
        ckEditor = editor;
        ckEditor.enableReadOnlyMode( Symbol("#content") );
        ckEditor.setData(post['content'])

        const editableElement = ckEditor.ui.view.editable.element;
        editableElement.style.border = 'none';

        const toolbarElement = ckEditor.ui.view.toolbar.element;
        toolbarElement.style.display = 'none';
    })
    .catch(error => {
        // Handle any initialization error
    });
