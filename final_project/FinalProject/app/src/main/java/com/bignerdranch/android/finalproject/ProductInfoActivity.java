package com.bignerdranch.android.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductInfoActivity extends AppCompatActivity {

    private static final String TAG = "ProductInfoActivity";
    private static final String PRODUCTS_URL = "http://10.0.0.78:5100/api/products";
    // http://10.2.106.113:5100 SCHOOL
    // http://10.0.0.78:5100 HOME

    private ListView listViewProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listViewProducts = findViewById(R.id.productListView);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        listViewProducts.setAdapter(productAdapter);

        fetchProductsFromServer();
    }

    private void fetchProductsFromServer() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, PRODUCTS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseProductResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching products: " + error.getMessage());
                        Toast.makeText(ProductInfoActivity.this, "Failed to load products.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    private void parseProductResponse(JSONArray response) {
        try {
            productList.clear();

            for (int i = 0; i < response.length(); i++) {
                JSONObject productJson = response.getJSONObject(i);
                String id = productJson.getString("_id");
                String productName = productJson.getString("productName");
                double price = productJson.getDouble("price");
                String description = productJson.getString("description");

                Product product = new Product(id, productName, price, description);
                productList.add(product);
            }

            productAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing product data: " + e.getMessage());
            Toast.makeText(this, "Error parsing product data.", Toast.LENGTH_SHORT).show();
        }
    }
}