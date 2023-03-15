package crux.russia.e_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import crux.russia.e_school.model.Question;

public class AdminHomeActivity extends AppCompatActivity {

    Button classRoom,eduMaterial,materialContent,exam,question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        classRoom = findViewById(R.id.btn_admin_class_room);
        classRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this,AdminClassRoomActivity.class);
                startActivity(i);
            }
        });
        eduMaterial = findViewById(R.id.btn_admin_edu_materials);
        eduMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this,AdminMaterialsActivity.class);
                startActivity(i);
            }
        });
        materialContent = findViewById(R.id.btn_admin_materials_content);
        materialContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this,AdminContentActivity.class);
                startActivity(i);
            }
        });
        exam = findViewById(R.id.btn_admin_exam);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this,AdminExamActivity.class);
                startActivity(i);
            }
        });

        question = findViewById(R.id.btn_admin_question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this, AdminQuestionActivity.class);
                startActivity(i);
            }
        });
    }
}