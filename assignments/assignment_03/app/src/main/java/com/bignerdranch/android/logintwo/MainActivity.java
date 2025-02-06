package com.bignerdranch.android.logintwo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

// String url = "http://10.0.0.182:5000/login";

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    TextView usernameField;
    TextView passwordField;
    Button loginButton;

    // MY IP
    //String url = "http://10.1.121.55:5000/login";
    String url = "http://10.1.121.55:5000/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.login_button);
        usernameField = findViewById(R.id.user_input);
        passwordField = findViewById(R.id.password_input);

        queue = Volley.newRequestQueue(this);
        queue.start();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                String data = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username, password);

                JsonRequest<String> jsonRequest =
                        new JsonRequest<String>(Request.Method.POST,
                                url, data,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT)
                                                .show();
                                        Log.d("Login", "responded " + response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Login Error", "Error:" + error);
                                    }
                                }) {
                            @Override
                            protected Response parseNetworkResponse(NetworkResponse response) {
                                String data = new String(response.data);
                                Response<String> res = Response.success(data, null);
                                Log.d("Login", "parseNetworkResponse called");
                                return res;
                            }
                        };
                queue.add(jsonRequest);
            }
        });

    }
}