package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminLoginPage extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private static final String LOGIN_URL = "http://10.0.0.78:5100/api/login";
    // http://10.2.106.113:5100 SCHOOL
    // http://10.0.0.78:5100 HOME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AdminLoginPage.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    JSONObject loginData = new JSONObject();
                    loginData.put("username", username);
                    loginData.put("password", password);

                    JsonObjectRequest loginRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            LOGIN_URL,
                            loginData,
                            response -> {
                                try {
                                    boolean success = response.getBoolean("success");
                                    if (success) {
                                        Toast.makeText(AdminLoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AdminLoginPage.this, AdminPanelActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AdminLoginPage.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(AdminLoginPage.this, "Login error", Toast.LENGTH_SHORT).show();
                                }
                            },
                            error -> {
                                error.printStackTrace();
                                Toast.makeText(AdminLoginPage.this, "Network error", Toast.LENGTH_SHORT).show();
                            }
                    );

                    Volley.newRequestQueue(AdminLoginPage.this).add(loginRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AdminLoginPage.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}