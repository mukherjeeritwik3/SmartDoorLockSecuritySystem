package com.devonative.myapplication;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class controlPanel extends AppCompatActivity {
    private Button button;
    private Button buttonOff;
    private ToggleButton toggleButton;
    private TextView textview1;
    FirebaseDatabase rootNode;
    DatabaseReference reff;


    // Download files setup
    // Create a storage reference from our app
    private Button download_btn;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;

    private Button clear_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_control_panel);
        Intent intent = getIntent();
        button = findViewById(R.id.button3);
        buttonOff = findViewById(R.id.button4);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                rootNode = FirebaseDatabase.getInstance();
                reff = rootNode.getReference("/fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw/toggle");
                reff.setValue(true);






            }
        });
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reff = rootNode.getReference("/fir-python-e6b67-default-rtdb/SecuritySystem/-Mn9auOPpOiMosS31Wpw/toggle");
                reff.setValue(false);
            }
        });

        toggleButton = findViewById(R.id.SwitchOnOFF);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    rootNode = FirebaseDatabase.getInstance();
                    reff = rootNode.getReference("/fir-python-e6b67-default-rtdb/DeviceList/-MmhGCofbF3tz9HBQgP_/device_1");
                    reff.setValue(true);
                }
                else{
                    rootNode = FirebaseDatabase.getInstance();
                    reff = rootNode.getReference("/fir-python-e6b67-default-rtdb/DeviceList/-MmhGCofbF3tz9HBQgP_/device_1");
                    reff.setValue(false);
                }
            }
        });



        // download method
        download_btn = findViewById(R.id.download_button);

        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });

        //clear method

        clear_btn = findViewById(R.id.clear);

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDB();
            }
        });


    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(controlPanel.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }

    public void download(){
        storageReference = firebaseStorage.getInstance().getReference();
//        ref=storageReference.child("snapshot_secure/img_107.png");
        ref=storageReference.child("snapshot_secure/");

        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for(StorageReference item : listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            downloadFiles(controlPanel.this,"Mobile",".jpg", Environment.DIRECTORY_DOWNLOADS,url);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }
            }
        });

//        SystemClock.sleep(20000);

//        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                for(StorageReference item : listResult.getItems()){
//                    item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//
//                        }
//                    });
//                }
//            }
//        });


    }

    // Delete files in there


    public void clearDB(){
        storageReference = firebaseStorage.getInstance().getReference();
        ref=storageReference.child("snapshot_secure/");
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for(StorageReference item : listResult.getItems()){
                    item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                }
            }
        });

    }

    public void downloadFiles(Context context,String fileName, String fileExtension,String destinationDirectory,String url){

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(destinationDirectory, fileName+fileExtension);
        downloadManager.enqueue(request);
    }









}