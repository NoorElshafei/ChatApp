package com.example.chatapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
  private MaterialEditText email, password;
  private FirebaseAuth auth;
  private Button loginButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Login");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    email = findViewById(R.id.email);
    password = findViewById(R.id.password);
    loginButton = findViewById(R.id.btn_login);

    auth = FirebaseAuth.getInstance();

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();

        if (TextUtils.isEmpty(emailString) || TextUtils.isEmpty(passwordString)) {
          Toast.makeText(LoginActivity.this, "Login Field!", Toast.LENGTH_SHORT).show();

        } else {

          auth.signInWithEmailAndPassword(emailString, passwordString)
                  .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                      }
                    }
                  });
        }
      }
    });


  }
}
