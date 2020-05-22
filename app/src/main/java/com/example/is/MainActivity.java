package com.example.is;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public EditText emailId, passwd;
    Button btnSignUp;
    Button signIn;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailId = findViewById(R.id.username);
        passwd = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.button2);
        signIn = findViewById(R.id.button);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailID = emailId.getText().toString();
                String paswd = passwd.getText().toString();
                if (emailID.isEmpty())
                {
                    emailId.setError("Provide your Email first!");
                    emailId.requestFocus();
                }else if (paswd.isEmpty()){
                    passwd.setError("Enter your password");
                    emailId.requestFocus();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(emailID,paswd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
                            }else{
//                                startActivity(new Intent(MainActivity.this,OTP.class));
                                Intent i=new Intent(MainActivity.this,OTP.class);
                                i.putExtra("d",1);
                                MainActivity.this.startActivity(i);

                            }
                        }
                    });

                }



            }
        });


        }
    }
