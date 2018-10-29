package com.example.mohamedsherif.firsttaskusingspringboot;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class SearchActivity extends AppCompatActivity {

    private static String SEARCH_FOR_EMPLOYEE = "https://springboot-my-task.herokuapp.com/api/employees/{id}";

    //Progress Dialog
    private ProgressDialog mProgressDialog;
    private TextInputLayout mIDTextInputLayout;
    private Button mSearchBtn;
    private LinearLayout mEmpLinearLayout;
    private TextView mNameTextView;
    private TextView mJobIDTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle(R.string.search_for_employee);

        initializeComponents();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mIDTextInputLayout.getEditText().getText().toString().trim();
                if( !TextUtils.isEmpty(id)){
                    mProgressDialog.setTitle(R.string.search_for_employee);
                    mProgressDialog.setMessage("Please wait until we get the data...");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    search(id);
                }
                else {
                    Toast.makeText(SearchActivity.this, "Please enter the id of the employee !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initializeComponents() {
        mProgressDialog = new ProgressDialog(this);
        mIDTextInputLayout = findViewById(R.id.search_id_text);
        mSearchBtn = findViewById(R.id.search_btn);
        mEmpLinearLayout = findViewById(R.id.search_employee_linear_layout);
        mNameTextView = findViewById(R.id.search_name_text_view);
        mJobIDTextView = findViewById(R.id.search_job_id_text_view);
    }

    private void search(String id) {
        AndroidNetworking.get(SEARCH_FOR_EMPLOYEE)
                .addPathParameter("id", id)
                .build()
                .getAsObject(Employee.class, new ParsedRequestListener<Employee>() {
                    @Override
                    public void onResponse(Employee employee) {
                        mProgressDialog.dismiss();
                        mEmpLinearLayout.setVisibility(View.VISIBLE);
                        mNameTextView.setText(employee.getName());
                        mJobIDTextView.setText(employee.getJob_id());
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgressDialog.dismiss();
                        mEmpLinearLayout.setVisibility(View.GONE);
                        Toast.makeText(SearchActivity.this, "This employee doesn't exist.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
