class faqAcc {

    constructor(id, role) {
        this._id = id;
        this._data = []
        this._userRole = role
    }

    setData(data) {
        this._data = data
        return this
    }

    draw(){
        const dom = $(`#${this._id}`)
        dom.empty()

        this._data.forEach((data, i) => {

            const delAndEdit = (this._userRole === 'ROLE_USER') ? '' :
                           `<hr class="hr" />
                            <div class="d-flex justify-content-end">
                                <a href="#" onclick="deleteBbsctt(${data['id']})" style="margin-right: 10px;">
                                    <i data-feather="trash" class="front-medium-2"></i>
                                </a>
                                <a href="#" onclick="editBbsctt(${data['id']})">
                                    <i data-feather="edit" class="front-medium-2"></i>
                                </a>
                            </div>`

            const body = `
                <div class="accordion-item">
                    <h2 class="accordion-header" id="${data['id']}">
                      <button class="accordion-button collapsed" data-bs-toggle="collapse" role="button" data-bs-target="#faq${i}" aria-expanded="false" aria-controls="faq${i}">
                           <b> ${data['title']} </b>
                      </button>
                    </h2>
                    <div id="faq${i}" class="accordion-collapse collapse" aria-labelledby="${data['id']}" data-bs-parent="#faq-accordion">
                        <div class="accordion-body">
                            <div class="mb-3 mt-3" style="margin-bottom: 10px;">
                                <div id="content" name="content" style="min-height: 200px">
                                    ${data['content']}
                                </div>
                            </div>

                            <hr class="hr" />

                            <div class="mb-3 mt-3">
                                <label for="file-container">파일첨부</label>
                                <div class="file-container" id="file-container">
                                    <ul id="download-container-${data['id']}"></ul>
                                </div>
                            </div>
                           
                            ${delAndEdit}
                        </div>
                    </div>
                </div>
            `

            dom.append(body)
            if(data['file']) this._drawFiles(data['id'], data['file']['fileList'])
        })
    }

    _drawFiles(id, file) {
        const dom = $(`#download-container-${id}`)
        dom.empty()

        file.forEach((value, i) => {
            const body =
                `
                    <li style="margin-top: 10px;">
                        <a href="#" onclick="Http.fileDownload(${value.id})">
                            <i data-feather="download" class="front-medium-2"></i>
                        </a>
                        ${value['originalFileName']}
                    </li>
                    `
            dom.append(body)
        });
        feather.replace()
    }
}