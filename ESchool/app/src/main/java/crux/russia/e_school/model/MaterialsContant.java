package crux.russia.e_school.model;

public class MaterialsContant {
    private String docId;
    private String materialDocId;
    private String materialName;
    private String title;
    private String body;
    private String link;


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getMaterialDocId() {
        return materialDocId;
    }

    public void setMaterialDocId(String materialDocId) {
        this.materialDocId = materialDocId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return title;
    }
}
