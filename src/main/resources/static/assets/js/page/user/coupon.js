$(document).ready(function() {

    couponTable
        .get('/api/coupon-has-chk')
        .add(new Column("index").title("#").width("10%").center())
        .add(new Column("hongCouponVO").title("쿠폰이름").width("20%").left().formatter(function(cell) { return `${cell.getValue()['couponName']}` }))
        .add(new Column("hongCouponVO").title("할인가격(원)").width("20%").left().formatter(function(cell){ return `${Util.priceString(cell.getValue()['couponRate'])}` }))
        .add((new Column("useAt").title("사용여부").width("10%").left().formatter(function(cell){ return `${Util.strYN(cell.getValue(), "사용", "미사용")}` })))
        .add(new Column("hongCouponVO").title("사용 가능 기간").width("20%").left().formatter(function(cell) {
            const rowData = cell.getValue();
            return `${rowData['startDate']} ~ ${rowData['endDate']}`
        }))
        .add(new Column("hongCouponHasId").title("삭제").width("20%").center().formatter(function(cell) {
            const rowData = cell.getData()
            return ` <button type="button" class="btn btn-sm btn-outline-danger coupon-delete-btn" data-delete="${rowData.useAt}" data-num="${rowData.hongCouponHasId}" onclick="deleteCouonHas(this)">삭제</button>`
        }))
        .init()

    requestUserTable
        .get('/api/coupons-for-request')
        .selectable()
        .add(new Column("index").title("#").width("10%").center())
        .add(new Column("couponName").title("쿠폰이름").width("30%").left())
        .add(new Column("couponRate").title("할인 가격").width("30%").left().formatter(function(cell) { return `${Util.priceString(cell.getValue())} 원` }))
        .add(new Column("startDate").title("사용 가능 기간").width("30%").left().formatter(function(cell){
            const rowData = cell.getData();
            return `${rowData['startDate']} ~ ${rowData['endDate']}`
        }))
        .init()

    getRequestCouponTable
        .get(`/api/coupon-request-user`)
        .selectable()
        .add(new Column("index").title("#").width("10%").center())
        .add(new Column("couponName").title("쿠폰이름").width("30%").left())
        .add(new Column("couponRate").title("할인 가격").width("30%").left().formatter(function(cell) { return `${Util.priceString(cell.getValue())} 원` }))
        .add(new Column("startDate").title("사용 가능 기간").width("30%").left().formatter(function(cell){
            const rowData = cell.getData();
            return `${rowData['startDate']} ~ ${rowData['endDate']}`
        }))
        .init()
})

/* 자신이 요청한 쿠폰 리스트 조회 */
$("#request-coupon-lst").on('click', function(e){
    $("#get-request-coupon-modal").modal('show')
})

/* 쿠폰 요청하기 버튼 클릭 이벤트 */
$("#request-coupon").on('click', function(e) {
    $("#choose-coupon-modal").modal('show')
})

/* 쿠폰 요청하기 (쿠폰 요청 모달에서) 버튼 클릭 이벤트 */
$("#choose-coupon-btn").on('click', function(e) {
    const selectedRows = requestUserTable.getSelectedRows()
    const idList = selectedRows.map(item => item.couponId)

    if(idList.length === 0) Util.alert('요청할 쿠폰을 선택해주세요.', 'w', 'w')
    else {
        const obj = {'couponId': idList}

        Http.post(`/api/coupon-request-all`, obj).then((res) => {
            if (res['httpStatus'] === 200) {
                Util.alert(`총 ${res.message}개의 쿠폰이 요청되었습니다.`).then(() => {
                    window.location.href = '/user/coupon'
                })
            } else {
                Util.alert("쿠폰 요청에 실패했습니다.", 'w', 'w')
            }
        })
    }
})

/* 내가 요청한 쿠폰 리스트 모달 테이블 -> 클릭된 row들 삭제 : 내가 요청한 쿠폰들이 승인되기 전에 삭제하는 부분 */
$("#delete-request-coupon-btn").on('click', function(e) {
    const selectedRows = getRequestCouponTable.getSelectedRows()
    const idList = selectedRows.map(item => item['hongCouponRequestId'])

    if(idList.length === 0) Util.alert('삭제할 요청을 선택해주세요.', 'w', 'w')
    else {
        const queryString = '?ids=' + idList.join('&ids=');

        Http.delete(`/api/coupon-delete-several${queryString}`).then((res) => {
            if (res['httpStatus'] === 200) {
                Util.alert(`총 ${res.message}개를 삭제했습니다.`).then(() => {
                    window.location.href = `/user/coupon`
                })
            } else {
                Util.alert("쿠폰 요청 삭제에 실패했습니다.", 'w', 'w')
            }

        })
    }
})

/* 쿠폰 삭제 : 내가 요청한 쿠폰들이 승인이 된 뒤에 삭제하는 부분 */
function deleteCouonHas(This) {
    const couponHasId = This.getAttribute("data-num")
    const useAt = This.getAttribute("data-delete")

    const msg = (useAt === 'N') ? '해당 쿠폰은 아직 사용 전입니다. <br/> 해당 쿠폰을 삭제하시겠습니까?' : '해당 쿠폰을 삭제하시겠습니까?'
    Util.confirm(`${msg}`).then((isOk) => {
        if(isOk) {
            Http.delete(`/api/coupon-has/${couponHasId}`).then((res) => {
                if(res['httpStatus'] === 200) {
                    Util.alert(`${res.message}`).then(() => {
                        window.location.href = `/user/coupon`
                    })
                }else {
                    Util.alert("해당 쿠폰 삭제에 실패했습니다.", 'w', 'w')
                }
            })
        }
    })
}