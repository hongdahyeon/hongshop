class FormDataToObj {
    static async getParameter(formId) {
        const form = document.getElementById(formId);
        const formData = new FormData(form);
        const data = Array.from(formData.entries()).reduce((perv, [key, value]) => {
            if (perv[key]) {
                Array.isArray(perv[key]) ? perv[key].push(value) : (perv[key] = [perv[key], value]);
            } else {
                perv[key] = value;
            }
            return perv;
        }, {});
        return data;
    }
}
