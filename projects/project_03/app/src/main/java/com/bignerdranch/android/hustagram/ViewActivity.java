package com.bignerdranch.android.hustagram;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import com.android.volley.toolbox.JsonArrayRequest;

public class ViewActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Bitmap> results = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        gridView = (GridView) findViewById(R.id.gridView);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
        String url = "http://10.0.0.78:5100/images";

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("ViewActivity", "Response: " + response.toString());  // Log the response

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject data = response.getJSONObject(i);

                                        if (data.has("image_path")) {
                                            String imagePath = data.getString("image_path");
                                            Log.d("ViewActivity", "Image path: " + imagePath);

                                            String imageUrl = "http://10.0.0.78:5100/" + imagePath;

                                            loadImageFromUrl(imageUrl);
                                        } else {
                                            Log.e("ViewActivity", "No 'image_path' field found for index " + i);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSONArray Error", "Error:" + error);
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private void loadImageFromUrl(String imageUrl) {
        // Create an ImageRequest to download the image
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // Add the downloaded image to the results
                        results.add(response);

                        // Notify the adapter that the data has changed
                        PictureListAdapter adapter = new PictureListAdapter(ViewActivity.this, results);
                        gridView.setAdapter(adapter);
                    }
                },
                0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ViewActivity", "Error loading image: " + error.getMessage());
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(imageRequest);
    }
}