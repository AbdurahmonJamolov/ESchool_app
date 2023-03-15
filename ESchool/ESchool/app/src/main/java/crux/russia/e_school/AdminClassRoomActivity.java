package crux.russia.e_school;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import crux.russia.e_school.adapters.ClassRoomAdapter;
import crux.russia.e_school.interfaces.LoadData;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.UserAchievements;
import crux.russia.e_school.utility.Memory;
import crux.russia.e_school.utility.Util;

public class AdminClassRoomActivity extends AppCompatActivity {

    AppCompatButton save, clear, delete;
    EditText name;
    ListView lv;
    ClassRoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_class_room);

        changeListen();
        lv = findViewById(R.id.lv_class_room_list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory.currentClassRoom = (ClassRoom) parent.getItemAtPosition(position);
                name.setText(Memory.currentClassRoom.getClassName());
            }
        });

        Util.loadAllClassRoom(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {
                adapter = new ClassRoomAdapter(AdminClassRoomActivity.this, list);
                lv.setAdapter(adapter);
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

        name = findViewById(R.id.ed_class_name);

        save = findViewById(R.id.btn_class_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassRoom object = new ClassRoom();
                if (Memory.currentClassRoom == null) {
                    object.setDocId(Util.generateRandomUUID());
                } else {
                    object.setDocId(Memory.currentClassRoom.getDocId());
                }

                object.setClassName(name.getText().toString());

                Util.addOrUpdateObject(object, object.getDocId());
                clear();
            }
        });


        clear = findViewById(R.id.btn_class_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        delete = findViewById(R.id.btn_class_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Memory.currentClassRoom != null)
                    Util.deleteObject(Memory.currentClassRoom, Memory.currentClassRoom.getDocId());

                clear();
            }
        });

    }

    public void clear() {
        name.setText("");
        Memory.currentClassRoom = null;
    }

    public void changeListen() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(ClassRoom.class.getSimpleName());
        ListenerRegistration registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Util.loadAllClassRoom(new LoadData() {
                            @Override
                            public void loadAllClassRoom(List<ClassRoom> list) {
                                adapter = new ClassRoomAdapter(AdminClassRoomActivity.this, list);
                                lv.setAdapter(adapter);
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

                    }
                });
    }
}