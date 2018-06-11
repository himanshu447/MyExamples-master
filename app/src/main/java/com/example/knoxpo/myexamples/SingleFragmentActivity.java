package com.example.knoxpo.myexamples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment getFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();

        Fragment existingFragment = fm.findFragmentById(R.id.fragment_container);

        if (existingFragment == null) {
            fm
                    .beginTransaction()
                    .replace(R.id.fragment_container,   getFragment())
                    .commit();
        }
    }
}
