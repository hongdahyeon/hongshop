if(post['file']) setFileList(post['file']['fileList']);

$(document).ready(function(){
    feather.replace()

    /* 삭제버튼 클릭 이벤트 */
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

    /* 목록버튼 클릭 이벤트 */
    $("#list-btn").on("click", function(e){
        window.location.href = `/bbs/${post['typeId']}`
    })

    /* 수정버튼 클릭 이벤트 */
    $("#edit-btn").on("click", function(e){
        window.location.href = `/bbs/edit/${post['id']}`
    })
})

function setFileList(file = []) {
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

ClassicEditor
    .create(document.querySelector('#content'), {
        readOnly: true,
    })
    .then(editor => {
        ckEditor = editor;
        ckEditor.enableReadOnlyMode(  Symbol("#content") );
        ckEditor.setData(post['content'])

        const editableElement = ckEditor.ui.view.editable.element;
        editableElement.style.border = 'none';

        const toolbarElement = ckEditor.ui.view.toolbar.element;
        toolbarElement.style.display = 'none';
    })
    .catch(error => {
        // Handle any initialization error
    });