package com.example.mohamedsherif.firsttaskusingspringboot;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;
import java.util.List;

public class AllEmployeesActivity extends AppCompatActivity {

    private String TAG = AllEmployeesActivity.class.getSimpleName();
    private static String GET_ALL_EMPLOYEES = "https://springboot-my-task.herokuapp.com/api/employees";

    private ProgressDialog mProgressDialog;

    private RecyclerView mEmployeesRecyclerView;
    private EmployeesAdapter mEmployeesAdapter;
    private List<Employee> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_employees);
        getSupportActionBar().setTitle(R.string.get_all_employees);

        initializeComponents();

        getAllEmployees();
    }

    private void initializeComponents() {
        mProgressDialog = new ProgressDialog(this);

        mEmployeesRecyclerView = findViewById(R.id.all_employees_recycler_view);
        mEmployeesAdapter = new EmployeesAdapter(employeeList);
        mEmployeesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEmployeesRecyclerView.setAdapter(mEmployeesAdapter);

    }

    private void getAllEmployees() {
        mProgressDialog.setTitle("Getting Data");
        mProgressDialog.setMessage("Please wait untill we get data...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        AndroidNetworking.get(GET_ALL_EMPLOYEES)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Employee.class, new ParsedRequestListener<List<Employee>>() {
                    @Override
                    public void onResponse(List<Employee> employees) {

                        for (Employee employee : employees) {
                            employeeList.add(employee);
                        }
                        mEmployeesAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgressDialog.dismiss();
                        Toast.makeText(AllEmployeesActivity.this, anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
