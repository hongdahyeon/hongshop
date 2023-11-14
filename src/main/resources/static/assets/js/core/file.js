class File{

    constructor(id) {
        this._drawId = id;
        this._fileGroupId = null;
        this._deleteFile = []
        this._fileData = []
    }

    setData(data = []){
        this._fileData = data
        return this
    }

    setFileGroupId(fileGroupId){
        this._fileGroupId = fileGroupId;
        return this
    }

    getDeleteFile(){
        return this._deleteFile
    }

    getFileGroupId(){
        return this._fileGroupId
    }

    setFiles(){
        const dom = $(`#${this._drawId}`)
        dom.empty()

        this._fileData.forEach((value, i) => {
            const listItem = document.createElement("li");
            listItem.style.marginTop = "10px";
            listItem.setAttribute("data-file-id", value.id);

            const link = document.createElement("a");
            link.href = "#";
            link.onclick = () => this.trashFile(value.id, listItem);

            const icon = document.createElement("i");
            icon.setAttribute("data-feather", "trash");
            icon.className = "front-medium-2";

            link.appendChild(icon);
            listItem.appendChild(link);
            listItem.appendChild(document.createTextNode(value['originalFileName']));

            dom.append(listItem);
        });
        feather.replace()
    }

    domEmptyAndFileGroupIdNull(){
        const dom = $(`#${this._drawId}`)
        dom.empty()
        this._fileGroupId = null
        this._deleteFile = []
        return this
    }
    trashFile(id, element) {
        this._deleteFile.push(id);
        const listItem = $(element).closest("li");
        listItem.remove();
    }

    addFileList = (file = []) => {
        const dom = $(`#${this._drawId}`)

        const listItem = document.createElement("li");
        listItem.style.marginTop = "10px";
        listItem.setAttribute("data-file-id", file.id);

        const link = document.createElement("a");
        link.href = "#";
        link.onclick = () => this.trashFile(file.id, listItem);

        const icon = document.createElement("i");
        icon.setAttribute("data-feather", "trash");
        icon.className = "front-medium-2";

        link.appendChild(icon);
        listItem.appendChild(link);
        listItem.appendChild(document.createTextNode(`[임시저장] ${file['originalFileName']}`));

        dom.append(listItem);

        feather.replace()
    }
}
