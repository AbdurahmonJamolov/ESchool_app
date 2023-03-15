package crux.russia.e_school.model;

public class Materials {
    private String docId;
    private String classDocId;
    private String className;
    private String materialName;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getClassDocId() {
        return classDocId;
    }

    public void setClassDocId(String classDocId) {
        this.classDocId = classDocId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public String toString() {
        return materialName;
    }
}
