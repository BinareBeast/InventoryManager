package com.gknagro.inventorymanager.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gknagro.inventorymanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText editTextemail,editTextpassword;
    private Button login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");


        editTextemail = findViewById(R.id.email);
        editTextpassword = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    protected void login(){
        String mail,password;

        mail = editTextemail.getText().toString().trim();
        password = editTextpassword.getText().toString().trim();

        if(TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Enter Required Fields!",Toast.LENGTH_LONG).show();
            return;
        }
        if (mAuth == null) {
            Toast.makeText(LoginActivity.this, "Firebase Auth not initialized", Toast.LENGTH_SHORT).show();
            return;
        }
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    fetchUserRole(user.getUid());
                                } else {
                                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
    private void fetchUserRole(String uid) {
        mDatabase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    if (role != null) {
                        redirectUserBasedOnRole(role);
                    } else {
                        Toast.makeText(LoginActivity.this, "Role not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void redirectUserBasedOnRole(String role) {
        switch (role) {
            case "admin":
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                break;
            case "manager":
                startActivity(new Intent(LoginActivity.this, ManagerActivity.class));
                break;
            case "employee":
                startActivity(new Intent(LoginActivity.this, EmployeeActivity.class));
                break;
            default:
                Toast.makeText(LoginActivity.this, "Role not recognized", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}