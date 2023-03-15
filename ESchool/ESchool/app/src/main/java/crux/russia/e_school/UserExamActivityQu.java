package crux.russia.e_school;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import crux.russia.e_school.adapters.ExamAdapter;
import crux.russia.e_school.adapters.QuestionAdapter;
import crux.russia.e_school.adapters.QuestionUserAdapter;
import crux.russia.e_school.interfaces.LoadData;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.UserAchievements;
import crux.russia.e_school.utility.Memory;
import crux.russia.e_school.utility.Util;

public class UserExamActivityQu extends AppCompatActivity {

    ListView lv;
    QuestionUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exam_qu);





        lv = findViewById(R.id.lv_user_question_list);


        Util.loadAllQuestionByExam(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {

            }

            @Override
            public void loadAllMaterials(List<Materials> list) {

            }

            @Override
            public void loadAllMaterialsContant(List<MaterialsContant> list) {

            }

            @Override
            public void loadAllExam(List<Exam> list) {

            }

            @Override
            public void loadAllQuestion(List<Question> list) {
                adapter = new QuestionUserAdapter(UserExamActivityQu.this, list);
                lv.setAdapter(adapter);
            }

            @Override
            public void loadAllAchievements(List<UserAchievements> list) {

            }
        }, Memory.currentExam.getDocId());
    }
}