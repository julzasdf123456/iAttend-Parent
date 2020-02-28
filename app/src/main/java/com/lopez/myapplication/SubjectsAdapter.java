package com.lopez.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubjectsAdapter  extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    public List<Subjects> subjectsList;
    public Context mContext;
    public String childId;

    public SubjectsAdapter(List<Subjects> subjectsList, Context mContext, String childId) {
        this.subjectsList = subjectsList;
        this.mContext = mContext;
        this.childId = childId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View subjectsView = inflater.inflate(R.layout.recyclerview_subjects, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(subjectsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subjects subject = subjectsList.get(position);

        holder.subject.setText(subject.getSubject());
        holder.description.setText(subject.getDescription());
        holder.schoolyear.setText(subject.getSchoolyear());
    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject, description, schoolyear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.subject);
            description = (TextView) itemView.findViewById(R.id.description);
            schoolyear = (TextView) itemView.findViewById(R.id.schoolYear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos =getAdapterPosition();
                    Intent intent = new Intent(mContext, Monitor.class);
                    intent.putExtra("SUBJECT", subjectsList.get(pos).getSubject());
                    intent.putExtra("CHILDID", childId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
