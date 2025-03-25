package com.bignerdranch.android.customerlist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DisplayCustomerActivity extends AppCompatActivity {
    protected static final String url = "http://192.168.0.18:5000/update";
    static CustomerListAdapter adapter;
    static RequestQueue queue;
    Context context;

    Customer customer;
    public static Intent newIntent(Context packageContext, CustomerListAdapter adapterRef, RequestQueue queue) {
        Intent i = new Intent(packageContext, DisplayCustomerActivity.class);
        adapter = adapterRef;
        DisplayCustomerActivity.queue = queue;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_display_layout);
        context = this;

        int index = getIntent().getIntExtra(CustomerListAdapter.EXTRA_SELECTED_ITEM, -1);
        if (index >= 0)
        {
            customer = (Customer) adapter.getItem(index);
            populateView(customer);
        }

        TextView nameField = findViewById(R.id.name_view);
        TextView addressField = findViewById(R.id.address_view);
        TextView phoneField = findViewById(R.id.phone_view);
        TextView comment_text = findViewById(R.id.comment_input);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String address = addressField.getText().toString();
                String phone = phoneField.getText().toString();
                String new_comment = comment_text.getText().toString();

                List<String> commentList = customer.getComments();
                if (null == commentList) {
                    commentList = new ArrayList<String>();
                }
                commentList.add(new_comment);
                customer.setComments(commentList);

                String data = String.format("{ \"_id\": \"%s\", \"name\":\"%s\", \"address\":\"%s\", \"phone\":\"%s\" }", customer.getId(), name, address, phone);
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(data);
                    JSONArray cl = new JSONArray(commentList);
                    jobj.put("comments", commentList);
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

                                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                                        adapter.notifyDataSetChanged();
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
                                return res;
                            }
                        };
                queue.add(jsonRequest);
            }
        });

    }

    protected void populateView(Customer customer)
    {
        TextView t = findViewById(R.id.name_view);
        t.setText(customer.getName());
        t = findViewById(R.id.address_view);
        t.setText(customer.getAddress());
        t.findViewById(R.id.phone_view);
        t.setText(customer.getPhone());

        ListView l = findViewById(R.id.commentListView);
        ArrayAdapter<String> array = new ArrayAdapter<>(this, R.layout.comment_item_layout, customer.getComments());
        l.setAdapter(array);
    }

}