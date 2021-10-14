package com.example.myfrags;

import android.os.Bundle;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends FragmentActivity implements Fragment1.OnButtonClickListener {

    private Map<Integer, Integer> frames = new ArrayMap<>();
    private boolean hidden;

    @Override
    public void onButtonClickShuffle() {

        List<Integer> list = new ArrayList<Integer>(Arrays.asList(frames.get(0), frames.get(1), frames.get(2), frames.get(3)));
        Collections.shuffle(list);
        for (int i = 0; i < 4; i++) {
            frames.remove(i);
            frames.put(i, list.get(i));
        }

        newFragments();
    }
    //1 2 3 4
    //1 2 3 4

    //1 2 3 4
    //2 1 3 4
    @Override
    public void onButtonClickClockwise() {


        frames.entrySet().stream().sorted(Map.Entry.comparingByValue());

            Integer tmp = frames.get(0);
            frames.remove(0);
            frames.put(0, frames.get(1));

            frames.remove(1);
            frames.put(1, frames.get(2));

        frames.remove(2);
        frames.put(2, frames.get(3));

        frames.remove(3);
        frames.put(3, tmp);

        newFragments();
    }

    private void newFragments() {

        Fragment[] newFragments = new Fragment[]{new Fragment1(), new Fragment2(), new Fragment3(), new Fragment4()};

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (int i : frames.keySet()) {
            transaction.replace(frames.get(i), newFragments[i]);
            if (hidden && !(newFragments[i] instanceof Fragment1)) transaction.hide(newFragments[i]);
        }

        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onButtonClickHide() {

        if(hidden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (Fragment f : fragmentManager.getFragments()) {

            if (f instanceof Fragment1 ) continue;

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(f);


            transaction.addToBackStack(null);
            transaction.commit();
        }

        hidden = true;
    }

    @Override
    public void onButtonClickRestore() {

        if (!hidden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (Fragment f : fragmentManager.getFragments()) {
            if (f instanceof Fragment1) continue;
            transaction.show(f);
        }

        transaction.addToBackStack(null);
        transaction.commit();

        hidden = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            int[] tmp = new int[]{R.id.frame1, R.id.frame2, R.id.frame3, R.id.frame4};
            for(int i=0;i<4;i++){
                frames.put(i,tmp[i]);
            }
            hidden = false;

            Fragment[] fragments = new Fragment[]{new Fragment1(), new Fragment2(), new Fragment3(), new Fragment4()};
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (int i = 0; i < 4; i++) {
                transaction.add(frames.get(i), fragments[i]);
            }
            transaction.addToBackStack(null);
            transaction.commit();


        } else {
            int[] tmp = savedInstanceState.getIntArray("FRAMES");
            for(int i=0; i<tmp.length; i++){
                frames.put(i, tmp[i]);
            }

            hidden = savedInstanceState.getBoolean("HIDDEN");
        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof Fragment1) {
            ((Fragment1) fragment).setOnButtonClickListener(this);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        int[] tmp = new int[4];
        for(int i=0; i<tmp.length; i++){
            tmp[i] = frames.get(i);
        }

        outState.putIntArray("FRAMES", tmp);
        outState.putBoolean("HIDDEN", hidden);
    }



}