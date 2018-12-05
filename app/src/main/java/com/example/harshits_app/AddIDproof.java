package com.example.harshits_app;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddIDproof extends AppCompatActivity {

    Uri imageuri1,imageuri2;
    public static final int PICK_IMAGE_REQ=1;
    private ImageView frontImage,backImage;
    private int f;
    private StorageReference ref;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_idproof);

        frontImage=(ImageView) findViewById(R.id.front);
        backImage=(ImageView) findViewById(R.id.back);
        Button chooseimage1=(Button) findViewById(R.id.chooseImage1);
        Button chooseimage2=(Button) findViewById(R.id.chooseImage2);
        Button Upload=(Button) findViewById(R.id.upload);
        Intent intent=getIntent();
        String userid=intent.getStringExtra("userid");
        ref=FirebaseStorage.getInstance().getReference("users/employee/"+userid+"/ID proof");
        bar=(ProgressBar) findViewById(R.id.uploadprogress);

        chooseimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=1;
                fileChooser();
            }
        });

        chooseimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=2;
                fileChooser();
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageuri1!=null&&imageuri2!=null){
                    bar.setVisibility(View.VISIBLE);
                    //add first image
                    ref.child("front side."+getFileExtension(imageuri1)).putFile(imageuri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //upload second image
                            ref.child("back side."+getFileExtension(imageuri2)).putFile(imageuri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(AddIDproof.this,"Upload successful",Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.GONE);
                                    finish();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddIDproof.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddIDproof.this,"Uploading file",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }


    private void fileChooser(){
        Intent intent=new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQ && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            if(f==1) {
                imageuri1=data.getData();
                frontImage.setImageURI(imageuri1);
            }
            else {
                imageuri2=data.getData();
                backImage.setImageURI(imageuri2);
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver resolver=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }
}
