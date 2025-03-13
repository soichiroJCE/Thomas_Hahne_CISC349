package com.bignerdranch.android.walmartlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class StoreFragment extends Fragment
{
    protected Store store;

    public StoreFragment(Store store)
    {
        this.store = store;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);

        TextView tv = view.findViewById(R.id.storeName);
        tv.setText(store.getName());
        tv = view.findViewById(R.id.address);
        tv.setText(store.getAddress());
        tv = view.findViewById(R.id.city);
        tv.setText(store.getCity());
        tv = view.findViewById(R.id.phoneNumber);
        tv.setText(store.getPhoneNumber());
        return view;
    }

}