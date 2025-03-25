package com.bignerdranch.android.customerlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected static final String url = "http://10.0.0.182:5000/all";

    RequestQueue queue;

    List<Customer> customerList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listView);

        customerList = new ArrayList<Customer>();

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        queue.start();

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        Customer customer = new Customer(response.getJSONObject(i));
                                        customerList.add(customer);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                CustomerListAdapter adapter = new CustomerListAdapter(list.getContext(), customerList, queue);
                                list.setAdapter(adapter);
                                list.setOnItemClickListener(adapter);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSONArray Error", "Error:" + error);
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        Button addButton = (Button)findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddCustomerActivity
                Intent i = AddCustomerActivity.newIntent(MainActivity.this, queue);
                startActivity(i);
            }

        });
    }
}