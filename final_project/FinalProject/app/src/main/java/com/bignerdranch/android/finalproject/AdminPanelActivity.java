package com.bignerdranch.android.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminPanelActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private EditText editTextPrice;
    private EditText editTextDescription;
    private Button buttonAddProduct;

    private static final String TAG = "AdminPanelActivity";
    private static final String API_URL = "http://10.0.0.78:5100/api/products";
    // http://10.2.106.113:5100 SCHOOL
    // http://10.0.0.78:5100 HOME

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_panel);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = editTextProductName.getText().toString().trim();
                String price = editTextPrice.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                if (productName.isEmpty() || price.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AdminPanelActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    addProductToServer(productName, price, description);
                }
            }
        });
    }

    private void addProductToServer(String productName, String price, String description) {
        JSONObject productData = new JSONObject();
        try {
            productData.put("productName", productName);
            productData.put("price", price);
            productData.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL, productData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(AdminPanelActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error adding product: " + error.getMessage());
                        Toast.makeText(AdminPanelActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}