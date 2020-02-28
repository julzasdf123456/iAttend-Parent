package com.lopez.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Monitor extends AppCompatActivity {

    public Bundle bundle;
    public String subject, childId;

    public CalendarView calendarView;
    public DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        // PRE - INITS
        bundle = getIntent().getExtras();
        subject = bundle.getString("SUBJECT");
        childId = bundle.getString("CHILDID");

        // INITS
        getSupportActionBar().setTitle(subject);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        dr = FirebaseDatabase.getInstance().getReference().child("Attendance").child(childId).child(subject);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<EventDay> events = new ArrayList<>();
                    for (DataSnapshot attendance : dataSnapshot.getChildren()) {
                        AttendanceData data = attendance.getValue(AttendanceData.class);
                        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
                        Calendar calendar = Calendar.getInstance();
                        try {
                            calendar.setTime(sdf.parse(data.getDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        events.add(new EventDay(calendar, R.drawable.ic_check_circle_black_24dp));
                    }
                    calendarView.setEvents(events);

                } else {
                    Snackbar.make(calendarView, "No attendance recorded on your child.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
