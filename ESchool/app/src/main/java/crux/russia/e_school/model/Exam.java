package crux.russia.e_school.model;

public class Exam {
    private String docId;
    private String materialsId;
    private String materialsName;
    private String examTitl;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(String materialsId) {
        this.materialsId = materialsId;
    }

    public String getMaterialsName() {
        return materialsName;
    }

    public void setMaterialsName(String materialsName) {
        this.materialsName = materialsName;
    }

    public String getExamTitl() {
        return examTitl;
    }

    public void setExamTitl(String examTitl) {
        this.examTitl = examTitl;
    }

    @Override
    public String toString() {
        return examTitl;
    }
}
