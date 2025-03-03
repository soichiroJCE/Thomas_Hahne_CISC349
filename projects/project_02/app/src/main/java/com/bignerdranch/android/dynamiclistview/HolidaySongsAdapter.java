package com.bignerdranch.android.dynamiclistview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;

public class HolidaySongsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private static final int IMAGE_SIZE = 256;
    private Context context;
    private ArrayList<HolidaySongs> holidaySongs;
    private ImageLoader imageLoader;

    public static final String EXTRA_SELECTED_ITEM = "edu.harrisburgu.cisc349.dynamiclist.selecteditem";

    public HolidaySongsAdapter(Context context, ArrayList<HolidaySongs> holidaySongs, RequestQueue queue) {
        this.context = context;
        this.holidaySongs = holidaySongs;

        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
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
    }

    @Override
    public int getCount() {
        return holidaySongs == null ? 0 : holidaySongs.size();
    }

    @Override
    public Object getItem(int position) {
        return holidaySongs == null ? null : holidaySongs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return holidaySongs == null ? 0 : holidaySongs.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
            holder = new ViewHolder();
            holder.albumName = convertView.findViewById(R.id.albumName);
            holder.artistName = convertView.findViewById(R.id.artistName);
            holder.danceability = convertView.findViewById(R.id.danceability);
            holder.duration = convertView.findViewById(R.id.duration);
            holder.albumImageView = convertView.findViewById(R.id.albumImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HolidaySongs album = holidaySongs.get(position);

        holder.albumName.setText(album.getName());
        holder.artistName.setText(album.getArtist());
        holder.danceability.setText(String.format("%s: %.3f", context.getString(R.string.danceability), album.getDanceability()));
        holder.duration.setText(String.format("%d:%d", (album.getDurationMs() / 1000) / 60, (album.getDurationMs() / 1000) % 60));
        holder.albumImageView.setImageUrl(album.getImage(), imageLoader);

        return convertView;
    }

    private static class ViewHolder {
        TextView albumName;
        TextView artistName;
        TextView danceability;
        TextView duration;
        NetworkImageView albumImageView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(adapterView.getContext(), AlbumActivity.class);
        intent.putExtra(EXTRA_SELECTED_ITEM, position);
        adapterView.getContext().startActivity(intent);
    }

    public ArrayList<HolidaySongs> getHolidaySongs() {
        return holidaySongs;
    }
}