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

import crux.russia.e_school.adapters.ClassRoomAdapter;
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

public class AdminMaterialsActivity extends AppCompatActivity {

    ListView lv;
    MaterialsAdapter adapter;

    EditText name;
    Spinner classRomms;
    List<ClassRoom> classes;

    AppCompatButton save, delete, clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_materials);

        name = findViewById(R.id.ed_materials_name);
        classRomms = findViewById(R.id.sp_materials_classes);

        save = findViewById(R.id.btn_materials_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClassRoom catObject = (ClassRoom) classRomms.getSelectedItem();


                Materials object = new Materials();
                if (Memory.currentMaterials == null) {
                    object.setDocId(Util.generateRandomUUID());
                } else {
                    object.setDocId(Memory.currentMaterials.getDocId());
                }
                object.setClassDocId(catObject.getDocId());
                object.setClassName(catObject.getClassName());

                object.setMaterialName(name.getText().toString());

                Util.addOrUpdateObject(object, object.getDocId());

                clearData();

            }
        });

        delete = findViewById(R.id.btn_materials_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Memory.currentMaterials != null)
                    Util.deleteObject(Memory.currentMaterials, Memory.currentMaterials.getDocId());

                clearData();
            }
        });

        clear = findViewById(R.id.btn_materials_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

        Util.loadAllClassRoom(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {
                ArrayAdapter<ClassRoom> adapter = new ArrayAdapter<ClassRoom>(getBaseContext(),
                        android.R.layout.simple_spinner_item,
                        list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classRomms.setAdapter(adapter);
                classes = list;
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

            }

            @Override
            public void loadAllAchievements(List<UserAchievements> list) {

            }
        });

        lv = findViewById(R.id.lv_materials_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory.currentMaterials = (Materials) parent.getItemAtPosition(position);
                name.setText(Memory.currentMaterials.getMaterialName());
             //   classRomms.setSelection(Util.getItemIndexFromListOfClassRoom(Memory.currentClassRoom.getClassName(), classes));
            }
        });

        Util.loadAllMaterials(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {

            }

            @Override
            public void loadAllMaterials(List<Materials> list) {
                adapter = new MaterialsAdapter(AdminMaterialsActivity.this, list);
                lv.setAdapter(adapter);
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

        changeListen();
    }

    public void clearData() {
        name.setText("");
        classRomms.setSelection(0);
        Memory.currentClassRoom = null;

    }

    public void changeListen() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(Materials.class.getSimpleName());
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
                                adapter = new MaterialsAdapter(AdminMaterialsActivity.this, list);
                                lv.setAdapter(adapter);
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
                    }
                });
    }
}