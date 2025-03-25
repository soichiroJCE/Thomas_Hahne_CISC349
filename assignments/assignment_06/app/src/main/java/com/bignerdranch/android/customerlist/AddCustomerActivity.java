package com.bignerdranch.android.customerlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class AddCustomerActivity extends AppCompatActivity {
    protected static final String url = "http://10.0.0.182:5000/add";
    protected static RequestQueue queue;
    protected static Context context;

    public static Intent newIntent(Context context, RequestQueue queue)
    {
        Intent i = new Intent(context, AddCustomerActivity.class);
        AddCustomerActivity.queue = queue;
        AddCustomerActivity.context = context;

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_add_layout);

        EditText nameField = findViewById(R.id.name_input);
        EditText addressField = findViewById(R.id.address_input);
        EditText phoneField = findViewById(R.id.phone_input);

        Button saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String address = addressField.getText().toString();
                String phone = phoneField.getText().toString();

                String data = String.format("{ \"name\":\"%s\", \"address\":\"%s\", \"phone\":\"%s\" }", name, address, phone);
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(data);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JSONObject finalJobj = jobj;
                JsonRequest jsonRequest =
                        new JsonObjectRequest(Request.Method.POST,
                                url, finalJobj,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT)
                                                .show();
                                        finish();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Save Error", "Error:" + error);
                                Toast.makeText(context, "Save failed!", Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            }
                        }) {
                            @Override
                            protected Response parseNetworkResponse(NetworkResponse response) {
                                String data = new String(response.data);
                                Response<JSONObject> res = Response.success(finalJobj, null);
                                Log.d("Save", "parseNetworkResponse called");
                                finish();
                                return res;
                            }
                        };

                queue.add(jsonRequest);

            }
        });
    }
}