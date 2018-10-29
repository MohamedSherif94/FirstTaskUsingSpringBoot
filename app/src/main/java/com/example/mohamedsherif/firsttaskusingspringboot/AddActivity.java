package com.example.mohamedsherif.firsttaskusingspringboot;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class AddActivity extends AppCompatActivity {

    private static String ADD_EMPLOYEE = "https://springboot-my-task.herokuapp.com/api/employees";
    private ProgressDialog mProgressDialog;
    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mJobIdTextInputLayout;
    private Button mAddBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setTitle(R.string.add_employee);

        initializeComponents();
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameTextInputLayout.getEditText().getText().toString().trim();
                String job_id = mJobIdTextInputLayout.getEditText().getText().toString().trim();
                if( !TextUtils.isEmpty(name) && !TextUtils.isEmpty(job_id)){
                    mProgressDialog.setTitle(R.string.add_employee);
                    mProgressDialog.setMessage("Please wait until we get the data...");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    add(name, job_id);
                }
                else {
                    Toast.makeText(AddActivity.this, "Please enter the name and job_id of the employee !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeComponents() {
        mProgressDialog = new ProgressDialog(this);
        mNameTextInputLayout = findViewById(R.id.add_name_text);
        mJobIdTextInputLayout = findViewById(R.id.add_job_id_text);
        mAddBtn = findViewById(R.id.add_btn);
    }

    private void add(String name, String job_id) {
        JSONObject jsonObjectBody = new JSONObject();
        try {
            jsonObjectBody.put("name", name);
            jsonObjectBody.put("job_id", job_id);
        } catch (JSONException e) {
            Toast.makeText(AddActivity.this, "There is an error in creating json object body", Toast.LENGTH_SHORT).show();
        }
        AndroidNetworking.post(ADD_EMPLOYEE)
                .addJSONObjectBody(jsonObjectBody)
                .build()
                .getAsObject(Employee.class, new ParsedRequestListener<Employee>() {
                    @Override
                    public void onResponse(Employee employee) {
                        mProgressDialog.dismiss();
                        mNameTextInputLayout.getEditText().setText("");
                        mJobIdTextInputLayout.getEditText().setText("");
                        Toast.makeText(AddActivity.this, "The employee is added with id " + employee.getId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgressDialog.dismiss();
                        Toast.makeText(AddActivity.this, "This employee isn't added. There is an error on server.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
