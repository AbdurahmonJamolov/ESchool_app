package crux.russia.e_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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

public class UserHomePageActivity extends AppCompatActivity {

    ListView lv;
    Spinner materials;
    ContentAdapter adapter;

    Button openExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        lv = findViewById(R.id.lv_user_materials_list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory.currentContent = (MaterialsContant) parent.getItemAtPosition(position);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Memory.currentContent.getLink()));
                startActivity(browserIntent);
            }
        });


        materials = findViewById(R.id.sp_user_materials);


        Util.loadAllMaterialsByClassRoom(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {

            }

            @Override
            public void loadAllMaterials(List<Materials> list) {
                ArrayAdapter<Materials> adapter = new ArrayAdapter<Materials>(getBaseContext(),
                        android.R.layout.simple_spinner_item,
                        list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                materials.setAdapter(adapter);
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
        }, Memory.currentUser.getClassRoomId());

        materials.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Materials matObject = (Materials) materials.getSelectedItem();

                Memory.currentMaterials = matObject;
                Util.loadAllMaterialsContantByMaterials(new LoadData() {
                    @Override
                    public void loadAllClassRoom(List<ClassRoom> list) {

                    }

                    @Override
                    public void loadAllMaterials(List<Materials> list) {

                    }

                    @Override
                    public void loadAllMaterialsContant(List<MaterialsContant> list) {
                        adapter = new ContentAdapter(UserHomePageActivity.this, list);
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
                }, matObject.getDocId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        openExam = findViewById(R.id.btn_user_request_exam);
        openExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserHomePageActivity.this, ActivityUserExam.class);
                startActivity(i);
            }
        });

    }
}