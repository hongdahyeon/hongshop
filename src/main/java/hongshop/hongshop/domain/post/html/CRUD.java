package hongshop.hongshop.domain.post.html;

public enum CRUD {

    INDEX("index"),
    VIEW("view"),
    EDIT("edit"),
    SAVE("save");

    private final String crud;

    CRUD(String crud){
        this.crud = crud;
    }

    public String html(){
        return crud;
    }
}
