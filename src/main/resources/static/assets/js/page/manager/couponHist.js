userListLogTable
    .add(new Column("index").title("#").center().width("10%"))
    .add(new Column("userId").title("사용자").left().width("20%"))
    .add(new Column("useDate").title("사용날짜").left().width("20%").formatter(function(cell){ return `${cell.getValue().toString().substring(0,10)}` }))

$(document).ready(function(e) {
    table
        .get('/api/coupon-hist-user')
        .add(new Column("index").title("#").width("5%").center())
        .add(new Column("couponName").title("쿠폰 이름").width("20%").left())
        .add(new Column("couponRate").title("쿠폰 가격").width("20%").left().formatter(function(cell) { return `${Util.priceString(cell.getValue())} 원`}))
        .add(new Column("histUserVOList").title("사용 사용자").width("20%").left().formatter(function(cell) { return `${cell.getValue().length} 명` }))
        .init()

    table.rowClick((data, row) => {
        userListLogTable
            .setData(data['histUserVOList'])
            .init()

        $("#user-couponUseHist-log-modal").modal('show')
    })
})