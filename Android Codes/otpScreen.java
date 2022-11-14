package com.devonative.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class otpScreen extends AppCompatActivity {

    private EditText otpEnter;
    private Button otpLogin;
    private Button otpResend;
    FirebaseDatabase rootNode;
    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_otp_screen);
    }
    // For send Otp Function
    public void resendOtp(View v) {
        rootNode = FirebaseDatabase.getInstance();
        reff = rootNode.getReference("/fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw/otp");
        reff.setValue(true);
        otpResend = findViewById(R.id.otpResend);

        otpResend.setEnabled(false);
        otpResend.setText("Wait for 30 seconds");
        otpResend.setTextColor(Color.RED);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                otpResend.setEnabled(true);
                otpResend.setText("Resend");
                int purple = Color.parseColor("#8a2be2");
                otpResend.setTextColor(purple);

            }

        }, 30000);






    }
    // delay method


    // Towards MainPanel for control activity
    public void toMainPanel(View v){
        otpEnter = findViewById(R.id.otpEnter);
        String otpText = otpEnter.getText().toString();
//        Toast.makeText(getApplicationContext(),
//                otpText,
//                Toast.LENGTH_LONG)
//                .show();
        rootNode = FirebaseDatabase.getInstance();
        reff = rootNode.getReference("/fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw/otp");

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            String otpDB;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otpDB = snapshot.getValue().toString();
                if(otpText.equals(otpDB)){
                    Intent intent = new Intent(getApplicationContext(), controlPanel.class);
                    reff.setValue(false);
                    startActivity(intent);
                }
                else{ Toast.makeText(getApplicationContext(),
                "Incorrect OTP!!",
                Toast.LENGTH_LONG)
                .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}