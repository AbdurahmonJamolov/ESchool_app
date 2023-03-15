package crux.russia.e_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import crux.russia.e_school.model.Admin;
import crux.russia.e_school.utility.Memory;

public class AdminLoginActivity extends AppCompatActivity {

    Button login;

    EditText phone, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        phone = findViewById(R.id.ed_admin_phone);
        password = findViewById(R.id.ed_admin_password);


        login = findViewById(R.id.btn_admin_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAdmin(password.getText().toString(), phone.getText().toString(), AdminLoginActivity.this);
            }
        });
    }


    public void loginAdmin(String password, String phone, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Admin.class.getSimpleName()).whereEqualTo("password", password)
                .whereEqualTo("phone", phone)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Admin admin = new Admin();
                            int counter = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                admin.setDocId(document.getString("docId"));
                                admin.setName(document.getString("name"));
                                admin.setPassword(document.getString("password"));
                                admin.setPhone(document.getString("phone"));

                                counter++;
                            }

                            if (counter > 0) {
                                Memory.currentAdmin = admin;
                                Intent i = new Intent(context, AdminHomeActivity.class);
                                context.startActivity(i);
                            } else {
                                Toast.makeText(context, "Check username and password ..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Tracking", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}