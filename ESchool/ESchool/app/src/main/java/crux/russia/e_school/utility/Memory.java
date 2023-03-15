package crux.russia.e_school.utility;

import crux.russia.e_school.model.Admin;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.User;

public class Memory {

    public static User currentUser = null;
    public static Admin currentAdmin = null;
    public static ClassRoom currentClassRoom = null;
    public static Materials currentMaterials = null;
    public static MaterialsContant currentContent = null;

    public static Exam currentExam = null;
    public static Question currentQuestion = null;


}
