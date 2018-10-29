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

public class UpdateActivity extends AppCompatActivity {

    private static String Update_EMPLOYEE = "https://springboot-my-task.herokuapp.com/api/employees/{id}";
    private ProgressDialog mProgressDialog;
    private TextInputLayout mIDTextInputLayout;
    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mJobIdTextInputLayout;
    private Button mUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setTitle(R.string.update_employee);

        initializeComponents();

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mIDTextInputLayout.getEditText().getText().toString().trim();
                String name = mNameTextInputLayout.getEditText().getText().toString().trim();
                String job_id = mJobIdTextInputLayout.getEditText().getText().toString().trim();

                if( !TextUtils.isEmpty(id) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(job_id)){
                    mProgressDialog.setTitle(R.string.update_employee);
                    mProgressDialog.setMessage("Please wait until we update the data...");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    update(id, name, job_id);
                }
                else {
                    Toast.makeText(UpdateActivity.this, "Please enter the id, name and job_id of the employee !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeComponents() {
        mProgressDialog = new ProgressDialog(this);
        mIDTextInputLayout = findViewById(R.id.update_id_text);
        mNameTextInputLayout = findViewById(R.id.update_name_text);
        mJobIdTextInputLayout = findViewById(R.id.update_job_id_text);
        mUpdateBtn = findViewById(R.id.update_btn);
    }

    private void update(String id, String name, String job_id) {
        JSONObject jsonObjectBody = new JSONObject();
        try {
            jsonObjectBody.put("name", name);
            jsonObjectBody.put("job_id", job_id);
        } catch (JSONException e) {
            Toast.makeText(UpdateActivity.this, "There is an error in creating json object body", Toast.LENGTH_SHORT).show();
        }

        AndroidNetworking.put(Update_EMPLOYEE)
                .addPathParameter("id", id)
                .addJSONObjectBody(jsonObjectBody)
                .build()
                .getAsObject(Employee.class, new ParsedRequestListener<Employee>() {
                    @Override
                    public void onResponse(Employee employee) {
                        mProgressDialog.dismiss();
                        Toast.makeText(UpdateActivity.this, "The employee is updated.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgressDialog.dismiss();
                        Toast.makeText(UpdateActivity.this, "This employee isn't exist in the database.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
