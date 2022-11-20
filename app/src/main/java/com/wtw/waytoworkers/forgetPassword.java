package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {

    ProgressBar progressBar;
    EditText email;
    Button button;
    FirebaseAuth mAuth;
    TextView goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        progressBar=findViewById(R.id.progressBarFP);
        email=findViewById(R.id.emailFP);
        button=findViewById(R.id.sendLinkToEmail);
        mAuth= FirebaseAuth.getInstance();
        goToLogin=findViewById(R.id.textGoBack);

        progressBar.setVisibility(View.GONE);
        goToLogin.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String targetEmail;
                targetEmail=email.getText().toString();
                if(targetEmail.isEmpty())
                {
                    email.setError("Enter email");
                    email.requestFocus();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(targetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if ((task.isSuccessful())) {
                                goToLogin.setVisibility(View.VISIBLE);
                                Toast.makeText(forgetPassword.this, "CHECK EMAIL", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(forgetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}
