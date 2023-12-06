$(document).ready(function(e) {
    chatContainer.setLoginId(sender.id)

    if(messageCanUser.length === 0) {
        Util.alert("톡톡을 할 수 있는 대상자가 없습니다. <br/> 잠시후에 다시 찾아주세요.", 'w', 'w').then(() => {
            window.location.href = '/'
        })
    }

    $(".receiver-link").on('click', function(e){
        receiverId = $(this).attr('data-num')                   // 현재 로그인한 사용자 입장에서 톡톡을 보낼 사용자Id

        Http.get(`/api/message/${receiverId}`).then((res) => {
            if(res['httpStatus'] === 200) {
                chatContainer.setDomId(`chat-container-div-${receiverId}`).setData(res.message).draw()
            }
        })
    })
})

$("#save-message-content").on('click', function(e) {
    if(receiverId === null) Util.alert("톡톡을 발송할 대상을 선택해주세요.", 'w', 'w')
    else {
        const messageContent = $("#message-content").val()
        const obj = {'senderId': sender.id, 'receiverId': receiverId, 'messageContent': messageContent}

        Http.post(`/api/message`, obj).then((res) => {
            if(res['httpStatus'] === 200) {
                chatContainer.setData(res.message).draw()
                $("#message-content").val('')
            }else {
                Util.alert("죄송합니다. <br/> 톡톡 전송에 실패했습니다.", 'w', 'w')
            }
        })
    }
})