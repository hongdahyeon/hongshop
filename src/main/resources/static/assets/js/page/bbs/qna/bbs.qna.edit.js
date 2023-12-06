if(post['file']) uploadFile.setFileGroupId(post['file'].id)

$(document).ready(function(){
    if(post['file'])  uploadFile.setData(post['file']['fileList']).setFiles()

    $("#list-btn").on("click", function(e){
        Util.confirm(`수정 정보가 저장되지 않을수도 있습니다. <br /> 목록으로 돌아가시겠습니까?`).then((isOk) => {
            if(isOk) window.location.href = `/bbs/${post['typeId']}`
        })
    })

    window.addEventListener("load", function() {
        const form = document.getElementById("qna-form")
        form.addEventListener("submit", function(event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                form.classList.add("was-validated")
            } else {
                event.preventDefault();
                if(ckEditor.getData().length === 0) Util.alert("내용을 입력해주세요.", 'w', 'w')
                else {
                    const obj = {
                        'title' : $("#title").val(),
                        'content': ckEditor.getData(),
                        'hongPostTypeId' : post['typeId'],
                        'deleteFile': uploadFile.getDeleteFile()
                    }

                    if(uploadFile.getFileGroupId()) obj['fileGroupId'] = uploadFile.getFileGroupId()

                    Http.put(`/api/post/${post['id']}`, obj).then((res) => {
                        if(res['httpStatus'] === 200){
                            Util.alert("수정되었습니다.").then(() => {
                                window.location.href = `/bbs/${post['typeId']}`
                            })
                        }
                    })
                }
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
})


ClassicEditor
    .create( document.querySelector( '#content' ), {
        extraPlugins: [MyCustomUploadAdapterPlugin]
    } )
    .then( editor => {
        ckEditor = editor;
        ckEditor.setData(post['content'])
    } )
    .catch( error => {
    } );

function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        return new MyUploadAdapter(loader)
    }
}