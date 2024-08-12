package com.example.recyclerwithdatabaseexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyActivity extends Activity {
    Button btnNew;
    RecyclerView myRecycler;
    DataLayer dbLayer=new DataLayer(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        btnNew=findViewById(R.id.btnNew);
        myRecycler=findViewById(R.id.myRecycler);
        LoadData();
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
    }
    public void LoadData(){
        ArrayList<Student>list=dbLayer.GetData();;
        MyAdapter adapter;
        RecyclerView.LayoutManager manager;
        manager=new LinearLayoutManager(this);
        myRecycler.setLayoutManager(manager);
        adapter=new MyAdapter(this,list);
        myRecycler.setAdapter(adapter);
    }
    public void ShowDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Student Record!");
        builder.setCancelable(false);
        LayoutInflater inflater= getLayoutInflater();
        View v=inflater.inflate(R.layout.dialog_layout,null,false);
        builder.setView(v);
        AlertDialog dlg=builder.create();
        dlg.show();
        Button btnSubmit,btnCancel;
        EditText editName,editAddress;
        btnSubmit=v.findViewById(R.id.btnSubmit);
        btnCancel=v.findViewById(R.id.btnCancel);
        editName=v.findViewById(R.id.editName);
        editAddress=v.findViewById(R.id.editAddress);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.cancel();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student s=new Student();
                s.Name=editName.getText().toString();
                s.Address=editAddress.getText().toString();
                s.Id=0;//id is zero for new student
                if(dbLayer.InsertData(s)) {
                    Toast.makeText( MyActivity.this,"Student registered successfully.",Toast.LENGTH_LONG).show();
                    LoadData();
                }
                else
                {
                    Toast.makeText(MyActivity.this,"Student registration failed.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
