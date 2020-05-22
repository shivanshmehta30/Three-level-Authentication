package com.example.is;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import com.example.is.*;
public class OTP extends AppCompatActivity {

    FirebaseAuth auth;
    EditText et1,et2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    String verification_code;

    Button otp,verify;
    String GMail = "// your email"; //replace with you GMail
    String GMailPass = "// your password"; // replace with you GMail Password
    int o;
    int d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        et1 = (EditText)findViewById(R.id.etmob);
        et2 = (EditText)findViewById(R.id.etotpver);
//        auth = FirebaseAuth.getInstance();
        Intent i=getIntent();
         d=i.getIntExtra("d",0);
        otp=(Button)findViewById(R.id.otp);

        verify=(Button)findViewById(R.id.verify);
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_to=et1.getText().toString();
                String str_subject="Your OTP";
                Random r = new Random();
                 o= r.nextInt((9999 - 1000) + 1) + 1000;
                String str_message="Your otp is "+o;


               // Check if there are empty fields
                if (!str_to.equals("") &&
                        !str_message.equals("") &&
                        !str_subject.equals("")){


                    //Check if 'To:' field is a valid email
                    if (isValidEmail(str_to)){
                        et1.setError(null);


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getApplicationContext(), "Sending... Please wait", Toast.LENGTH_LONG).show();
                            }
                        });
                        sendEmail(str_to, str_subject, str_message);
                    }else{
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                et1.setError("Not a valid email");
                            }
                        });
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "There are empty fields.", Toast.LENGTH_LONG).show();
                }








            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check=et2.getText().toString();
                if(o==Integer.parseInt(check))
                {
//                    Toast.makeText(getApplicationContext(),"OTP Matched",Toast.LENGTH_LONG).show();
                    Intent u=new Intent(OTP.this,activity_rgb.class);
                    u.putExtra("d",d);
                    OTP.this.startActivity(u);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"OTP Not Matched",Toast.LENGTH_LONG).show();
                }
            }
        });


//        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//
//
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                verification_code = s;
//                Toast.makeText(getApplicationContext(),"Code sent to the number",Toast.LENGTH_SHORT).show();
//            }
//        };

    }

    private void sendEmail(final String to, final String subject, final String message) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(GMail,
                            GMailPass);
                    sender.sendMail(subject,
                            message,
                            GMail,
                            to);
                    Log.w("sendEmail","Email successfully sent!");

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Email successfully sent!", Toast.LENGTH_LONG).show();
                            et1.setText("");
//                            et_message.setText("");
//                            et_subject.setText("");
                        }
                    });

                } catch (final Exception e) {
                    Log.e("sendEmail", e.getMessage(), e);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Email not sent. \n\n Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }

        }).start();
    }

    public final static boolean isValidEmail(CharSequence emailAddress) {
        if (emailAddress == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
        }
    }
    public void send_sms(View v)
    {
        String number = et1.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,60, TimeUnit.SECONDS,this,mcallbacks
        );
    }

    public void signInWithPhone (PhoneAuthCredential credential)
    {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"User Sign In Successfully",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    public void verify (View v)
    {
        String input_code =et2.getText().toString();
        if (verification_code.equals(""))
        {
            verifyPhoneNumber (verification_code,input_code);
        }
    }

   public void verifyPhoneNumber(String verifyCode ,String input_code)
   {
       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode,input_code);
       signInWithPhone(credential);
   }

}

