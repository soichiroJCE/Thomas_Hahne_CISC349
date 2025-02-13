package com.bignerdranch.android.listview;

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
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private final Context context;
    private final ArrayList<User> arrayList;
    private final ImageLoader imageLoader;

    public UserAdapter(Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        RequestQueue queue = Volley.newRequestQueue(context);

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
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.phone = convertView.findViewById(R.id.phone);
            holder.image = convertView.findViewById(R.id.networkImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = arrayList.get(position);
        holder.name.setText(user.getName());
        holder.phone.setText(user.getPhone());
        holder.image.setImageUrl(user.getImageUrl(), imageLoader);

        return convertView;
    }

    private static class ViewHolder {
        TextView name, phone;
        NetworkImageView image;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        User selectedUser = arrayList.get(i);
        Intent intent = new Intent(adapterView.getContext(), UserActivity.class);
        intent.putExtra("user_name", selectedUser.getName());
        intent.putExtra("user_phone", selectedUser.getPhone());
        intent.putExtra("user_image", selectedUser.getImageUrl());
        adapterView.getContext().startActivity(intent);
    }

}
