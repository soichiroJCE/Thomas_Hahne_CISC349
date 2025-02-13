package com.bignerdranch.android.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView listView;
    TextView textView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LayoutInflater factory = getLayoutInflater();
        final View myView = factory.inflate(R.layout.my_list, listView, false);

        textView = myView.findViewById(R.id.textView);

        listView = findViewById(R.id.listView);
        listItem = getResources().getStringArray(R.array.array_technology);

        for (String s : listItem) {
            Log.d(TAG, s);
        }

        ArrayList<User> arrayOfUsers = new ArrayList<>();
        arrayOfUsers.add(new User("Thomas", "712-266-5034", "https://avatars.fastly.steamstatic.com/d7e9a0363d45014b8d19075639cec63d816a77d6_full.jpg"));
        arrayOfUsers.add(new User("Junko", "777-777-7777", "https://static-cdn.jtvnw.net/jtv_user_pictures/645d98a0-a2eb-48a3-b687-cfbc55243e4a-profile_image-70x70.png"));
        arrayOfUsers.add(new User("Mingojce", "777-777-7777", "https://static-cdn.jtvnw.net/jtv_user_pictures/fe3b0811-4d02-44c8-9adf-2182cda6eea5-profile_image-70x70.png"));
        arrayOfUsers.add(new User("stompgoat", "777-777-7777", "https://static-cdn.jtvnw.net/jtv_user_pictures/9c8b0771-4f8c-4fe0-94cc-0c0400887305-profile_image-70x70.png"));
        arrayOfUsers.add(new User("RaidBullys", "777-777-7777", "https://static-cdn.jtvnw.net/jtv_user_pictures/732470c3-09ab-437b-af3f-d97c6cb88cca-profile_image-70x70.png"));
        arrayOfUsers.add(new User("glyboo", "777-777-7777", "https://static-cdn.jtvnw.net/jtv_user_pictures/c31f7fe7-819e-4d24-9484-5400db28d708-profile_image-70x70.png"));
        arrayOfUsers.add(new User("Vicbands", "777-777-7777", "https://static-cdn.jtvnw.net/jtv_user_pictures/edb3259f-5f2f-42a6-b443-7eb89b5b95ea-profile_image-70x70.png"));


        // Create the adapter to convert the array to views
        UserAdapter adapter = new UserAdapter(this, arrayOfUsers);

        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
    }
}