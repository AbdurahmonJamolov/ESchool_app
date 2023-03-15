package crux.russia.e_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import crux.russia.e_school.adapters.ContentAdapter;
import crux.russia.e_school.adapters.ExamAdapter;
import crux.russia.e_school.interfaces.LoadData;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.UserAchievements;
import crux.russia.e_school.utility.Memory;
import crux.russia.e_school.utility.Util;

public class ActivityUserExam extends AppCompatActivity {

    ExamAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exam);


        lv = findViewById(R.id.lv_user_exam_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Exam exam = (Exam) parent.getItemAtPosition(position);

                Memory.currentExam = exam;

                Intent i = new Intent(ActivityUserExam.this, UserExamActivityQu.class);
                startActivity(i);
            }
        });

        Util.loadAllExamByMaterials(new LoadData() {
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
                adapter = new ExamAdapter(ActivityUserExam.this, list);
                lv.setAdapter(adapter);
            }

            @Override
            public void loadAllQuestion(List<Question> list) {

            }

            @Override
            public void loadAllAchievements(List<UserAchievements> list) {

            }
        }, Memory.currentMaterials.getDocId());
    }
}