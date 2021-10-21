package com.example.myfrags;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Fragment4 extends Fragment {

    private FragsData fragsData;
    private Observer<Integer> numberObserver;

    private EditText edit;
    private TextWatcher textWatcher;
    private boolean turnOffWatcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_4, container, false);

        edit = view.findViewById(R.id.editTextNumber);

        fragsData = new ViewModelProvider(requireActivity()).get(FragsData.class);

        numberObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer newInteger) {
                turnOffWatcher = true;
                edit.setText(newInteger.toString());
            }
        };

        fragsData.counter.observe(getViewLifecycleOwner(), numberObserver);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!turnOffWatcher){
                    Integer i;

                    if(s.toString().equals("")){
                        fragsData.counter.setValue(0);
                        return;
                    }

                    try{
                        i = Integer.parseInt( s.toString() );
                    } catch (NumberFormatException e){
                        i = fragsData.counter.getValue();
                    }

                        fragsData.counter.setValue(i);

                } else {
                    turnOffWatcher = !turnOffWatcher;
                }
            }
        };

        edit.addTextChangedListener(textWatcher);

        return view;
    }
}