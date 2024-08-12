package com.example.recyclerwithdatabaseexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
    Context context;
    ArrayList<Student> list;

     public MyAdapter(Context context, ArrayList<Student> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater=LayoutInflater.from(context);
        v=inflater.inflate(R.layout.recycler_item_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student s=list.get(position);
        holder.txtId.setText(String.valueOf(s.Id));
        holder.txtName.setText(s.Name);
        holder.txtAddress.setText(s.Address);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //write code for edit
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //write code for delete
                DataLayer dbLayer=new DataLayer(context);
                int id=list.get(holder.getAdapterPosition()).Id;
                dbLayer.DeleteData(id);
                MyActivity activity=(MyActivity)context;
                activity.LoadData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
