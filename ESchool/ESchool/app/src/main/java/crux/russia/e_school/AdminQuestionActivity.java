package crux.russia.e_school;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.List;

import crux.russia.e_school.adapters.QuestionAdapter;
import crux.russia.e_school.interfaces.LoadData;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.UserAchievements;
import crux.russia.e_school.utility.Memory;
import crux.russia.e_school.utility.Util;

public class AdminQuestionActivity extends AppCompatActivity {

    Spinner searchExam, spExam;
    EditText qBody;
    RadioGroup rg;
    RadioButton rbTrue, rbFalse;

    ListView lv;
    QuestionAdapter adapter;
    AppCompatButton save, delete, clear;

    List<Exam> exams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_question);

        save = findViewById(R.id.btn_question_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Exam examObject = (Exam) spExam.getSelectedItem();


                Question object = new Question();
                if (Memory.currentQuestion == null) {
                    object.setDocId(Util.generateRandomUUID());
                } else {
                    object.setDocId(Memory.currentQuestion.getDocId());
                }
                object.setExamDocId(examObject.getDocId());
                object.setExamTitle(examObject.getExamTitl());

                object.setQuestionBody(qBody.getText().toString());
                if (rbTrue.isChecked()) {
                    object.setCorrectAnswer(true);
                } else if (rbFalse.isChecked()) {
                    object.setCorrectAnswer(false);
                }

                Util.addOrUpdateObject(object, object.getDocId());
                clearData();
            }
        });
        delete = findViewById(R.id.btn_question_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Memory.currentQuestion != null)
                    Util.deleteObject(Memory.currentQuestion, Memory.currentQuestion.getDocId());
            }
        });

        clear = findViewById(R.id.btn_question_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                ;
            }
        });
        spExam = findViewById(R.id.sp_question_exams);
        lv = findViewById(R.id.lv_question_list);
        qBody = findViewById(R.id.ed_question_body);
        rg = findViewById(R.id.rg_question_answers1);
        rbTrue = findViewById(R.id.rb_question_true1);
        rbFalse = findViewById(R.id.rb_question_false1);
        searchExam = findViewById(R.id.sp_question_exams_search);


        searchExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Exam object = (Exam) parent.getSelectedItem();


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
                        adapter = new QuestionAdapter(AdminQuestionActivity.this, list);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void loadAllAchievements(List<UserAchievements> list) {

                    }
                }, object.getDocId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Util.loadAllExam(new LoadData() {
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
                ArrayAdapter<Exam> adapter = new ArrayAdapter<Exam>(getBaseContext(),
                        android.R.layout.simple_spinner_item,
                        list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchExam.setAdapter(adapter);
                exams = list;
                spExam.setAdapter(adapter);
            }

            @Override
            public void loadAllQuestion(List<Question> list) {

            }

            @Override
            public void loadAllAchievements(List<UserAchievements> list) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory.currentQuestion = (Question) parent.getItemAtPosition(position);

                qBody.setText(Memory.currentQuestion.getQuestionBody());
                if (Memory.currentQuestion.isCorrectAnswer()) {
                    rbTrue.setChecked(true);
                } else {
                    rbFalse.setChecked(true);
                }
            }
        });


    }

    public void clearData() {
        qBody.setText("");
        Memory.currentQuestion = null;
    }
}