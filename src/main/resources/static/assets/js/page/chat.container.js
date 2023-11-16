class Chat {
    constructor() {
        this._id = null;
        this._loginId = null;
        this._data = []
    }

    setLoginId(loginId) {
        this._loginId = loginId
        return this
    }

    setData(data = []) {
        this._data = data
        return this
    }

    setDomId(id){
        this._id = id;
        return this;
    }

    draw(){
        const dom = $(`#${this._id}`)
        dom.empty()

        if(this._data.length !== 0) {
            this._data.forEach((data, i) => {
                let body = ''
                if(data['senderId'] == this._loginId) {
                    body = `<div class="message sender-message">
                                <span >${data['messageContent']}</span>  
                            </div>`
                }else {
                    body = `<div class="message receiver-message">
                                <span>${data['messageContent']}</span>
                            </div>`
                }

                dom.append(body)
            })
        }
    }
}