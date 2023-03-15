package crux.russia.e_school.model;

public class Question {
    private String docId;
    private String examDocId;
    private String examTitle;
    private String questionBody;
    private boolean correctAnswer; // true or false


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getExamDocId() {
        return examDocId;
    }

    public void setExamDocId(String examDocId) {
        this.examDocId = examDocId;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }
}
