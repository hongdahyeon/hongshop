package hongshop.hongshop.domain.postType.html;

public enum BbsType {

    NOTICE("notice"),
    FAQ("faq"),
    QNA("qna");

    private final String htmlPath;

    BbsType(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public static String getHtmlName(String value, String html){
        for(BbsType type : BbsType.values()){
            if(type.name().equals(value)){
                return type.htmlPath + "/" + html;
            }
        }
        throw new IllegalArgumentException("there is no html page");
    }
}
