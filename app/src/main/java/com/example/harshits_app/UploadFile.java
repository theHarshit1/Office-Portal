package com.example.harshits_app;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UploadFile extends AppCompatActivity {
    private static final int PICK_FILE_REQ=1;
    private Uri imageuri;
    public static boolean UPLOAD_RESULT;
    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();

        type=intent.getStringExtra("type");
        fileChooser(type);

    }

    private void fileChooser(String type){
        Intent intent=new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_FILE_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setContentView(R.layout.upload_file_progress);
        if(requestCode==PICK_FILE_REQ && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageuri=data.getData();

                String path;
                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                String uniqueName=UUID.randomUUID().toString();
                if(type.equals("images/*")){
                    path="users/"+uid+"/images/"+uniqueName+"."+getFileExtension(imageuri);
                }
                else{
                    path="users/"+uid+"/documents/"+uniqueName+"."+getFileExtension(imageuri);
                }
                uploadToFirebase(path);
        }
        else{
            finish();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver resolver=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

    private void uploadToFirebase(String path){
        UPLOAD_RESULT=false;
        StorageReference ref=FirebaseStorage.getInstance().getReference(path);
        ref.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    UPLOAD_RESULT=true;
                    finish();
                }
            }
        });
    }
}
