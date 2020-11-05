package com.example.findcoffee.ui.arView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.findcoffee.R;

public class ArViewFragment extends Fragment {

    private ArViewViewModel arViewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arViewViewModel =
                ViewModelProviders.of(this).get(ArViewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ar_view, container, false);
        final TextView textView = root.findViewById(R.id.text_arView);
        arViewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("The string here is",s);
                textView.setText(s);
            }
        });
        return root;
    }

}
