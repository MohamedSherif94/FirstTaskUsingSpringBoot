package com.example.mohamedsherif.firsttaskusingspringboot;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;

    public EmployeesAdapter(List<Employee> employeeList){
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_single_layout, parent, false);

        EmployeeViewHolder vh = new EmployeeViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);

        holder.mNameTextView.setText(employee.getName() + " ("+ employee.getId() + ")");
        holder.mJobIdTextView.setText(employee.getJob_id());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mNameTextView;
        public TextView mJobIdTextView;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.employee_single_layout_name);
            mJobIdTextView = itemView.findViewById(R.id.employee_single_layout_job_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
