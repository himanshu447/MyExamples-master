package com.example.knoxpo.myexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class ToDoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {

        int pos =getIntent().getIntExtra("position",0);

        return ToDoListFragment.newInstance(pos);

    }



}
