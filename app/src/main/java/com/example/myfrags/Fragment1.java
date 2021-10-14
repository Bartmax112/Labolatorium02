package com.example.myfrags;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment1 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        Button buttonShuffle = (Button) view.findViewById(R.id.button_shuffle);
        Button buttonClockwise = (Button) view.findViewById(R.id.button_clockwise);
        Button buttonHide = (Button) view.findViewById(R.id.button_hide);
        Button buttonRestore = (Button) view.findViewById(R.id.button_restore);


        buttonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickShuffle();
            }
        });

        buttonClockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickClockwise();
            }
        });

        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickHide();
            }
        });

        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickRestore();
            }
        });


        return view;
    }

    public interface OnButtonClickListener {
        public void onButtonClickShuffle();
        public void onButtonClickClockwise();
        public void onButtonClickHide();
        public void onButtonClickRestore();
    }

    private OnButtonClickListener callback = null;

    public void setOnButtonClickListener(OnButtonClickListener callback) {
        this.callback = callback;
    }
}