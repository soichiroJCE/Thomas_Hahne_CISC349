package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {

    private Button buttonProducts;
    private Button buttonSocial;
    private Button buttonStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        buttonProducts = findViewById(R.id.buttonProductInfo);
        buttonSocial = findViewById(R.id.buttonSocialPage);
        buttonStore = findViewById(R.id.buttonStore);

        buttonProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, ProductInfoActivity.class);
                startActivity(intent);
            }
        });

        buttonSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, SocialPage.class);
                startActivity(intent);
            }
        });

        buttonStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(browserIntent);
            }
        });
    }
}