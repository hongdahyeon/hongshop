$(document).ready(function(e) {
    Http.get(`/api/postsWithFileAnswerByPostType/${id}`).then((res) => {
        if(res['httpStatus'] === 200) {
            faq.setData(res.message).draw()
        }
    })

    $("#new-bbsctt").on("click", function(e) {
        window.location.href = `/bbs/save/${type['postTypeId']}`
    })
})

/* 게시글 삭제 버튼 클릭 이벤트 */
function deleteBbsctt(id){
    Util.confirm("삭제 하시겠습니까?").then((isOk) => {
        if(isOk) {
            Http.delete(`/api/post/${id}`).then((res) => {
                if (res['httpStatus'] === 200) {
                    Util.alert("게시글이 삭제되었습니다.").then(() => {
                        window.location.href = `/bbs/${postId}`
                    })
                }
            })
        }
    })
}

/* 게시글 수정 버튼 클릭 이벤트 */
function editBbsctt(id){
    window.location.href = `/bbs/edit/${id}`
}