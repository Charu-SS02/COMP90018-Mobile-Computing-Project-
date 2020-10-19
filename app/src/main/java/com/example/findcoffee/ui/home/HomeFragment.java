package com.example.findcoffee.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findcoffee.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private static RecyclerView recyclerView;
    private static ArrayList<HomeViewModel> data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        data = new ArrayList<HomeViewModel>();
        for (int i = 0; i < CoffeeShopData.nameArray.length; i++) {
            data.add(new HomeViewModel(
                    CoffeeShopData.nameArray[i],
                    CoffeeShopData.versionArray[i],
                    CoffeeShopData.id_[i],
                    CoffeeShopData.drawableArray[i]
            ));
        }

        recyclerView.setAdapter(new CoffeeShopAdapter(data));

        return root;
    }
}