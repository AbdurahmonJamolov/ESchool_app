package crux.russia.e_school.interfaces;


import java.util.List;

import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.UserAchievements;

public interface LoadData {

    void loadAllClassRoom(List<ClassRoom> list);

    void loadAllMaterials(List<Materials> list);

    void loadAllMaterialsContant(List<MaterialsContant> list);

    void loadAllExam(List<Exam> list);

    void loadAllQuestion(List<Question> list);

    void loadAllAchievements(List<UserAchievements> list);

}
