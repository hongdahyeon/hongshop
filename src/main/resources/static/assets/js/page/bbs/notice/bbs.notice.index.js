$(document).ready(function() {
    table
        .get(`/api/postsWithFileAnswerByPostType/${id}`)
        .add(new Column("index").title("#").width("15%").center())
        .add(new Column("title").title("제목").width("30%").left())
        .add(new Column("readCnt").title("조회수").width("20%").center())
        .add(new Column("file").title("첨부파일").width("20%").center().formatter(function(cell) {
            const cellData = cell.getValue()
            return (cellData === null) ? '0 개' : `${cellData['fileList'].length} 개`
        }))
        .init()

    table.rowClick((data, row) => {
        window.location.href = `/bbs/view/${data['id']}`
    })

    $("#new-bbsctt").on("click", function(e) {
        window.location.href = `/bbs/save/${type['postTypeId']}`
    })
})