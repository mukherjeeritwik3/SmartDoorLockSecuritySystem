package com.devonative.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;
    FirebaseDatabase rootNode;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



    }
    public void toControlPanel(View v){

        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        String uName = userName.getText().toString();
        String pass = passWord.getText().toString();

        boolean flag;

        rootNode = FirebaseDatabase.getInstance();
        reff = rootNode.getReference("/fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw/userName");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            String uNameDB;
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uNameDB = snapshot.getValue().toString();

                if (uNameDB.equals(uName)){

                    DatabaseReference reffPass = rootNode.getReference("/fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw/passWord");
                    reffPass.addListenerForSingleValueEvent(new ValueEventListener() {
                        String passDB;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            passDB = snapshot.getValue().toString();
                            if(passDB.equals(pass)){
                                DatabaseReference reffOtp = rootNode.getReference("/fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw/otp");
                                reffOtp.setValue(true);
                                Intent intent = new Intent(getApplicationContext(), otpScreen.class);
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),
                    "Wrong Credentials!",
                    Toast.LENGTH_SHORT)
                    .show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Wrong credentials!",
                            Toast.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

}