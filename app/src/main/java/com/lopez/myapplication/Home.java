package com.lopez.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    public RecyclerView subjectsRecyclerView;
    public SubjectsAdapter subjectsAdapter;
    public List<Subjects> subjectsList;
    public RecyclerView.LayoutManager layoutManager;
    public Bundle bundle;
    public String childId;

    public DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // INITS
        bundle = getIntent().getExtras();
        childId = bundle.getString("CHILDID");
        subjectsRecyclerView = (RecyclerView) findViewById(R.id.subjectsRecyclerview);
        subjectsList = new ArrayList<>();
        subjectsAdapter = new SubjectsAdapter(subjectsList, this, childId);
        layoutManager = new GridLayoutManager(this, 2);
        dr = FirebaseDatabase.getInstance().getReference().child("StudentSubjects").child(childId);

        subjectsRecyclerView.setLayoutManager(layoutManager);
        subjectsRecyclerView.setAdapter(subjectsAdapter);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    subjectsList.clear();
                    for (DataSnapshot subject : dataSnapshot.getChildren()) {
                        Subjects subjects = subject.getValue(Subjects.class);
                        subjectsList.add(subjects);
                    }
                    subjectsAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(subjectsRecyclerView, "No student data found!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
