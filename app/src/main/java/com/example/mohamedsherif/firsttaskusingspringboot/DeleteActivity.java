package com.example.mohamedsherif.firsttaskusingspringboot;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class DeleteActivity extends AppCompatActivity {

    private static String DELETE_EMPLOYEE = "https://springboot-my-task.herokuapp.com/api/employees/{id}";

    private ProgressDialog mProgressDialog;
    private TextInputLayout mIDTextInputLayout;
    private Button mDeleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        getSupportActionBar().setTitle(R.string.delete_employee);

        initializeComponents();

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mIDTextInputLayout.getEditText().getText().toString().trim();
                if( !TextUtils.isEmpty(id)){
                    mProgressDialog.setTitle(R.string.delete_employee);
                    mProgressDialog.setMessage("Please wait until we delete the data...");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    deleteEmployee(id);
                }
                else {
                    Toast.makeText(DeleteActivity.this, "Please enter the id of the employee !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeComponents() {
        mProgressDialog = new ProgressDialog(this);
        mIDTextInputLayout = findViewById(R.id.delete_id_text);
        mDeleteBtn = findViewById(R.id.delete_btn);
    }

    private void deleteEmployee(final String id) {
        AndroidNetworking.delete(DELETE_EMPLOYEE)
                .addPathParameter("id", id)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        mProgressDialog.dismiss();
                        if ( response.code() == 200){
                            Toast.makeText(DeleteActivity.this, "The Employee is deleted.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(DeleteActivity.this, "Employee not found with id : " + id, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mProgressDialog.dismiss();
                        Toast.makeText(DeleteActivity.this, "There is an error on the server.", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}


