package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
public class phoneVerification extends AppCompatActivity {

    EditText phoneNumber,otp;
    Button getOtp,verifyOtp;
    FirebaseAuth mAuth;
    String codeSent;
    ProgressBar otpProgress,verifyProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_verification);

        phoneNumber=findViewById(R.id.phone2verify);
        otp= findViewById(R.id.otp);
        getOtp=findViewById(R.id.getOtpBtn);
        verifyOtp=findViewById(R.id.otpVerifyBtn);
        otpProgress=findViewById(R.id.getOtpProgress);
        verifyProgress=findViewById(R.id.otpVerifyProgress);
        mAuth = FirebaseAuth.getInstance();

        otpProgress.setVisibility(View.GONE);
        verifyProgress.setVisibility(View.GONE);
        getOtp.setOnClickListener(v -> getVerificationCode());

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyProgress.setVisibility(View.VISIBLE);
                String otpEntered=otp.getText().toString();
                if(otpEntered.isEmpty())
                {
                    verifyProgress.setVisibility(View.GONE);
                    otp.setError("Enter phone number");
                    otp.requestFocus();
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent,otpEntered);
                signInWithPhoneAuthCredential(credential);
            }
        });

    }
    //
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final SharedPreferences pre = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
                        final int flag=pre.getInt("FLAG",0);
                        if (task.isSuccessful()) {
                            verifyProgress.setVisibility(View.GONE);
                            Toast.makeText(phoneVerification.this,"Successful",Toast.LENGTH_SHORT).show();
                            if (flag == 0) {
                                Intent intent = new Intent(phoneVerification.this, usersingup.class);
                                intent.putExtra("verifiedPhoneNumber", phoneNumber.getText().toString());
                                startActivity(intent);
                            } else if (flag == 1) {
                                Intent intent = new Intent(phoneVerification.this, workersignup.class);
                                intent.putExtra("verifiedPhoneNumber", phoneNumber.getText().toString());
                                startActivity(intent);
                            }
                        }
                        else {
                            verifyProgress.setVisibility(View.GONE);
                            Toast.makeText(phoneVerification.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(phoneVerification.this,"Invalid code",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    //
    private void getVerificationCode() {
        otpProgress.setVisibility(View.VISIBLE);
        String phoneNum=phoneNumber.getText().toString();

        if(phoneNum.isEmpty())
        {
            phoneNumber.setError("Enter phone number");
            phoneNumber.requestFocus();
            return;
        }
        if(phoneNum.length()<10){
            phoneNumber.setError("Enter a valid phone number");
            phoneNumber.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNum,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            otpProgress.setVisibility(View.GONE);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            otpProgress.setVisibility(View.GONE);
            Toast.makeText(phoneVerification.this,e.getMessage(), LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
        }
    };
}
