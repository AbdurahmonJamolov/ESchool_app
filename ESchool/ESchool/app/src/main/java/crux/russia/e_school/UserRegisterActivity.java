package crux.russia.e_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import crux.russia.e_school.interfaces.LoadData;
import crux.russia.e_school.model.ClassRoom;
import crux.russia.e_school.model.Exam;
import crux.russia.e_school.model.Materials;
import crux.russia.e_school.model.MaterialsContant;
import crux.russia.e_school.model.Question;
import crux.russia.e_school.model.User;
import crux.russia.e_school.model.UserAchievements;
import crux.russia.e_school.utility.Util;

public class UserRegisterActivity extends AppCompatActivity {

    EditText name, phone, password;
    Button save;

    Spinner classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        name = findViewById(R.id.ed_user_reg_name);
        phone = findViewById(R.id.ed_user_reg_phone);
        password = findViewById(R.id.ed_user_reg_password);
        save = findViewById(R.id.btn_user_reg_save);
        classes = findViewById(R.id.sp_user_reg_classes);


        Util.loadAllClassRoom(new LoadData() {
            @Override
            public void loadAllClassRoom(List<ClassRoom> list) {
                ArrayAdapter<ClassRoom> adapter = new ArrayAdapter<ClassRoom>(getBaseContext(),
                        android.R.layout.simple_spinner_item,
                        list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classes.setAdapter(adapter);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClassRoom catObject = (ClassRoom) classes.getSelectedItem();


                User user = new User();
                user.setDocId(Util.generateRandomUUID());
                user.setName(name.getText().toString());
                user.setPassword(password.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setClassRoomId(catObject.getDocId());
                user.setClassRoomName(catObject.getClassName());

                Util.addOrUpdateObject(user, user.getDocId());

                Intent i = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}