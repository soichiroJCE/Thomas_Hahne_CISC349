package com.bignerdranch.android.hustagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class PictureListAdapter extends BaseAdapter {
    private List<Bitmap> pictures;
    private Context context;

    public PictureListAdapter(Context context, List<Bitmap> pictures) {
        this.context = context;
        this.pictures = pictures;
    }

    @Override
    public int getCount() {
        return (pictures == null) ? 0 : pictures.size();
    }

    @Override
    public Object getItem(int position) {
        return (pictures == null) ? null : pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (pictures == null) ? 0 : pictures.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Use GridView's own layout params
        ImageView imageView;
        if (convertView == null) {
            // Inflate the image item layout
            imageView = new ImageView(context);
            // Set image size and other properties for GridView
            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));  // Set image size
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);  // Padding between images
        } else {
            imageView = (ImageView) convertView;
        }

        // Get the current image
        Bitmap picture = pictures.get(position);

        // Set the image to the ImageView
        imageView.setImageBitmap(picture);

        return imageView;
    }
}