class Chat {
    constructor() {
        this._id = null;
        this._data = []
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

        const body = `   <div class="message receiver-message">
                                    <span>이름</span>
                                    <br>
                                    <span>Id</span>
                                </div>

                                <!-- Sender's message -->
                                <div class="message sender-message">
                                    <span>이름</span>
                                    <br>
                                    <span>Id</span>
                                </div>`
        dom.append(body)
    }
}