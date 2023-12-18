$(document).ready(function(e) {
    table
        .get('/api/file-list')
        .usePagination()
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("originalFileName").title("파일 이름").width("20%").left())
        .add(new Column("fileSize").title("파일 크기").width("10%").left())
        .add(new Column("extension").title("확장자").width("10%").left())
        .add(new Column("filePath").title("파일 저장 경로").width("20%").left())
        .add(new Column("downCnt").title("다운로드 횟수").width("10%").center())
        .init()

    downloadLogTable
        .add(new Column("index").title("#").center().width("20%"))
        .add(new Column("userName").title("다운로드 사용자 이름").left().width("30%"))
        .add(new Column("userId").title("다운로드 사용자 이름").left().width("30%"))

    table.rowClick((data, row) => {
        const fileId = data.id
        downloadLogTable
            .get(`/api/log/${fileId}`)
            .init()
        $("#download-log-modal").modal('show')
    })
})