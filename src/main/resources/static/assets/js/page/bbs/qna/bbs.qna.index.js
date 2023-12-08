$(document).ready(function() {
    table
        .get(`/api/postsWithFileAnswerByPostType/${id}`)
        .add(new Column("regId").title("").width("5%").center().formatter(function(cell) {
            const cellData = cell.getValue()
            const classNm = (cellData == user.id) ? 'front-medium-2' : 'front-medium-2 lock'
            const feathers = (cellData == user.id) ? 'unlock' : 'lock'
            return `<i class="${classNm}" data-feather=${feathers} />`
        }))
        .add(new Column("index").title("#").width("10%").center())
        .add(new Column("title").title("제목").width("30%").left())
        .add(new Column("readCnt").title("조회수").width("20%").center())
        .add(new Column("file").title("첨부파일").width("20%").center().formatter(function(cell) {
            const cellData = cell.getValue()
            return (cellData === null) ? '0 개' : `${cellData['fileList'].length} 개`
        }))
        .add(new Column("answerList").title("답변").width("10%").formatter(function(cell){ return `${cell.getValue().length} 개` }))
        .init()

    table.rowClick((data, row) => {
        if(user['role'] == 'ROLE_USER' && data['regId'] != user['id']) Util.alert("본인이 작성한 게시글에 대해서만 볼 수 있습니다.", 'w', 'w')
        else window.location.href = `/bbs/view/${data['id']}`
    })

    table.afterComplete(() => {
        feather.replace()
    })

    $("#new-bbsctt").on("click", function(e) {
        window.location.href = `/bbs/save/${type['postTypeId']}`
    })
})