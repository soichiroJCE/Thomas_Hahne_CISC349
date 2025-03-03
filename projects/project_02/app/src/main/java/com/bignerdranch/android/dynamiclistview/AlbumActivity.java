package com.bignerdranch.android.dynamiclistview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import android.util.LruCache;
import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_ITEM = "edu.harrisburgu.cisc349.dynamiclist.selecteditem";
    public static final String EXTRA_SONGS_LIST = "SONGS_LIST";

    private HolidaySongs selectedAlbum;

    public static Intent newIntent(Context packageContext, int position, ArrayList<HolidaySongs> songsList) {
        Intent intent = new Intent(packageContext, AlbumActivity.class);
        intent.putExtra(EXTRA_SELECTED_ITEM, position);
        intent.putExtra(EXTRA_SONGS_LIST, songsList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        int index = getIntent().getIntExtra(EXTRA_SELECTED_ITEM, -1);
        ArrayList<HolidaySongs> songsList = (ArrayList<HolidaySongs>) getIntent().getSerializableExtra(EXTRA_SONGS_LIST);

        if (index >= 0 && songsList != null) {
            selectedAlbum = songsList.get(index);

            TextView albumNameTextView = findViewById(R.id.albumDisplayName);
            albumNameTextView.setText(selectedAlbum.getName());

            TextView artistNameTextView = findViewById(R.id.artistDisplayName);
            artistNameTextView.setText(selectedAlbum.getArtist());

            NetworkImageView albumImageView = findViewById(R.id.albumDisplayImageView);
            ImageView staticAlbumImageView = findViewById(R.id.staticAlbumImageView);  // Corrected ID

            if (selectedAlbum.getImage() != null && !selectedAlbum.getImage().isEmpty()) {

                ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(this), new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> mCache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return mCache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        mCache.put(url, bitmap);
                    }
                });
                albumImageView.setImageUrl(selectedAlbum.getImage(), imageLoader);
                albumImageView.setVisibility(NetworkImageView.VISIBLE);
                staticAlbumImageView.setVisibility(ImageView.GONE);
            } else {
                staticAlbumImageView.setImageResource(R.drawable.huva);
                albumImageView.setVisibility(NetworkImageView.GONE);
                staticAlbumImageView.setVisibility(ImageView.VISIBLE);
            }
        }
        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(v -> finish());
    }
}
