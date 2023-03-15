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

import crux.russia.e_school.adapters.ContentAdapter;
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

public class AdminContentActivity extends AppCompatActivity {

    ListView lv;
    ContentAdapter adapter;

    EditText title, descrption, link;
    Spinner spMaterials;
    List<Materials> materials;

    AppCompatButton save, delete, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_content);

        spMaterials = findViewById(R.id.sp_content_materials);
        lv = findViewById(R.id.lv_content_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory.currentContent = (MaterialsContant) parent.getItemAtPosition(position);
                title.setText(Memory.currentContent.getTitle());
                descrption.setText(Memory.currentContent.getBody());
                link.setText(Memory.currentContent.getLink());

                spMaterials.setSelection(Util.getItemIndexFromListOfMaterials(Memory.currentContent.getMaterialName(), materials));
            }
        });

        title = findViewById(R.id.ed_content_title);
        descrption = findViewById(R.id.ed_content_description);
        link = findViewById(R.id.ed_content_link);

        save = findViewById(R.id.btn_content_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Materials matObject = (Materials) spMaterials.getSelectedItem();


                MaterialsContant object = new MaterialsContant();
                if (Memory.currentContent == null) {
                    object.setDocId(Util.generateRandomUUID());
                } else {
                    object.setDocId(Memory.currentContent.getDocId());
                }
                object.setMaterialDocId(matObject.getDocId());
                object.setMaterialName(matObject.getMaterialName());

                object.setTitle(title.getText().toString());
                object.setBody(descrption.getText().toString());
                object.setLink(link.getText().toString());

                Util.addOrUpdateObject(object, object.getDocId());

                clearData();
            }
        });

        delete = findViewById(R.id.btn_content_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Memory.currentContent != null)
                    Util.deleteObject(Memory.currentContent, Memory.currentContent.getDocId());

                clearData();
            }
        });

        clear = findViewById(R.id.btn_content_clear);
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
                spMaterials.setAdapter(adapter);
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

        Util.loadAllMaterialsContent(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {

            }

            @Override
            public void loadAllMaterials(List<Materials> list) {

            }

            @Override
            public void loadAllMaterialsContant(List<MaterialsContant> list) {
                adapter = new ContentAdapter(AdminContentActivity.this, list);
                lv.setAdapter(adapter);
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
        title.setText("");
        descrption.setText("");
        link.setText("");
        spMaterials.setSelection(0);
        Memory.currentContent = null;

    }

    public void changeListen() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(MaterialsContant.class.getSimpleName());
        ListenerRegistration registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Util.loadAllMaterialsContent(new LoadData() {
                            @Override
                            public void loadAllClassRoom(List<ClassRoom> list) {

                            }

                            @Override
                            public void loadAllMaterials(List<Materials> list) {

                            }

                            @Override
                            public void loadAllMaterialsContant(List<MaterialsContant> list) {
                                adapter = new ContentAdapter(AdminContentActivity.this, list);
                                lv.setAdapter(adapter);
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