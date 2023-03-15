package crux.russia.e_school;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import crux.russia.e_school.adapters.ExamAdapter;
import crux.russia.e_school.adapters.MaterialsAdapter;
import crux.russia.e_school.interfaces.LoadData;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.UserAchievements;
import crux.russia.e_school.utility.Memory;
import crux.russia.e_school.utility.Util;

public class AdminExamActivity extends AppCompatActivity {

    ListView lv;
    ExamAdapter adapter;

    EditText title;
    Spinner material;
    List<Materials> materials;

    AppCompatButton save, delete, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exam);

        title = findViewById(R.id.ed_exam_title);
        material = findViewById(R.id.sp_exam_material);

        save = findViewById(R.id.btn_exam_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Materials catObject = (Materials) material.getSelectedItem();


                Exam object = new Exam();
                if (Memory.currentExam == null) {
                    object.setDocId(Util.generateRandomUUID());
                } else {
                    object.setDocId(Memory.currentExam.getDocId());
                }
                object.setMaterialsId(catObject.getDocId());
                object.setMaterialsName(catObject.getMaterialName());

                object.setExamTitl(title.getText().toString());

                Util.addOrUpdateObject(object, object.getDocId());

                clearData();

            }
        });


        delete = findViewById(R.id.btn_exam_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Memory.currentExam != null)
                    Util.deleteObject(Memory.currentContent, Memory.currentContent.getDocId());

                clearData();
            }
        });


        clear = findViewById(R.id.btn_exam_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });


        Util.loadAllMaterials(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {

            }

            @Override
            public void loadAllMaterials(List<Materials> list) {
                ArrayAdapter<Materials> adapter = new ArrayAdapter<Materials>(getBaseContext(),
                        android.R.layout.simple_spinner_item,
                        list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                material.setAdapter(adapter);
                materials = list;
            }

            @Override
            public void loadAllMaterialsContant(List<MaterialsContant> list) {

            }

            @Override
            public void loadAllExam(List<Exam> list) {

            }

            @Override
            public void loadAllQuestion(List<Question> list) {

            }

            @Override
            public void loadAllAchievements(List<UserAchievements> list) {

            }
        });

        lv = findViewById(R.id.lv_exam_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory.currentExam = (Exam) parent.getItemAtPosition(position);

                title.setText(Memory.currentMaterials.getMaterialName());
                material.setSelection(Util.getItemIndexFromListOfMaterials(Memory.currentExam.getMaterialsName(), materials));
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
                adapter = new ExamAdapter(AdminExamActivity.this, list);
                lv.setAdapter(adapter);
            }

            @Override
            public void loadAllQuestion(List<Question> list) {

            }

            @Override
            public void loadAllAchievements(List<UserAchievements> list) {

            }
        });

        changeListen();
    }


    public void clearData() {
        title.setText("");
        material.setSelection(0);
        Memory.currentExam = null;
    }


    public void changeListen() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(Exam.class.getSimpleName());
        ListenerRegistration registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Util.loadAllMaterials(new LoadData() {
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
                                adapter = new ExamAdapter(AdminExamActivity.this, list);
                                lv.setAdapter(adapter);
                            }

                            @Override
                            public void loadAllQuestion(List<Question> list) {

                            }

                            @Override
                            public void loadAllAchievements(List<UserAchievements> list) {

                            }
                        });
                    }
                });
    }
}