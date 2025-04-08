package com.bignerdranch.android.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        Product product = productList.get(position);

        TextView productIdTextView = convertView.findViewById(R.id.productId);
        TextView productNameTextView = convertView.findViewById(R.id.productName);
        TextView priceTextView = convertView.findViewById(R.id.productPrice);
        TextView descriptionTextView = convertView.findViewById(R.id.productDescription);

        productIdTextView.setText("ID: " + product.getId());
        productNameTextView.setText(product.getProductName());
        priceTextView.setText(String.format("$%.2f", product.getPrice()));
        descriptionTextView.setText(product.getDescription());

        return convertView;
    }
}