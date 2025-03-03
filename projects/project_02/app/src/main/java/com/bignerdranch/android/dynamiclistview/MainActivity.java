package com.bignerdranch.android.dynamiclistview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://setify-server.herokuapp.com/holiday_songs_spotify";
    private ListView listView;
    private ArrayList<HolidaySongs> holidaySongsList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        holidaySongsList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);

        loadLocalJsonData();

        fetchHolidaySongsData();
    }

    private void loadLocalJsonData() {
        try {
            InputStream is = getAssets().open("holiday_songs.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            parseHolidaySongsResponse(jsonArray);

        } catch (IOException | JSONException e) {
            Log.e("JSON Read Error", "Error: " + e.getMessage());
        }
    }

    private void fetchHolidaySongsData() {
        /*
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                response -> parseHolidaySongsResponse(response),
                error -> Log.e("JSONArray Error", "Error: " + error.getMessage()));

        queue.add(jsonArrayRequest);
         */
    }

    private void parseHolidaySongsResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);
                HolidaySongs album = new HolidaySongs(jsonObject);
                holidaySongsList.add(album);
            }

            HolidaySongsAdapter adapter = new HolidaySongsAdapter(this, holidaySongsList, queue);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(adapter);

        } catch (JSONException e) {
            Log.e("JSON Parse Error", "Error: " + e.getMessage());
        }
    }

    public ArrayList<HolidaySongs> getHolidaySongs() {
        return holidaySongsList;
    }
}