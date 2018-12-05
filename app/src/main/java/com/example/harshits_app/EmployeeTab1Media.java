package com.example.harshits_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class EmployeeTab1Media extends Fragment{

    public final static int REQUEST_CODE_IMAGE = 1;
    public final static int REQUEST_CODE_DOCS = 2;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.employee_tab1media, container, false);

       Button images=(Button) view.findViewById(R.id.images);
       Button docs=(Button) view.findViewById(R.id.docs);

       images.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getActivity(),UploadFile.class);
               intent.putExtra("type","image/*");
               startActivityForResult(intent,REQUEST_CODE_IMAGE);
           }
       });

       docs.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getActivity(),UploadFile.class);
               intent.putExtra("type","application/*");
               startActivityForResult(intent,REQUEST_CODE_DOCS);
           }
       });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(UploadFile.UPLOAD_RESULT) {
                UploadFile.UPLOAD_RESULT=false;
                switch (requestCode) {
                    case REQUEST_CODE_IMAGE:
                        Toast.makeText(getActivity(), "Images Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        break;
                    case REQUEST_CODE_DOCS:
                        Toast.makeText(getActivity(), "Documents Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            else {
                Toast.makeText(getActivity(), "Upload failed", Toast.LENGTH_SHORT).show();
            }
    }
}
