package crux.russia.e_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import crux.russia.e_school.model.User;
import crux.russia.e_school.utility.Memory;

public class UserLoginActivity extends AppCompatActivity {
    TextInputEditText phone, password;
    AppCompatButton login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        phone = findViewById(R.id.ed_login_user_phone);
        password = findViewById(R.id.ed_login_user_password);

        login = findViewById(R.id.btn_user_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(password.getText().toString(), phone.getText().toString());
            }
        });

        register = findViewById(R.id.btn_user_reg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(i);
            }
        });


    }

    public void loginUser(String password, String phone) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(User.class.getSimpleName()).whereEqualTo("password", password)
                .whereEqualTo("phone", phone)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            User object = new User();
                            int counter = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                object.setDocId(document.getString("docId"));
                                object.setName(document.getString("name"));
                                object.setPassword(document.getString("password"));
                                object.setPhone(document.getString("phone"));
                                object.setClassRoomName(document.getString("classRoomName"));
                                object.setClassRoomId(document.getString("classRoomId"));
                                counter++;
                            }

                            if (counter > 0) {
                                Memory.currentUser = object;
                                Intent i = new Intent(UserLoginActivity.this, UserHomePageActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getBaseContext(), "Check username and password ..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Tracking", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}