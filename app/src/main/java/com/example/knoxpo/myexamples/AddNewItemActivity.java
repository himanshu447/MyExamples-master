package com.example.knoxpo.myexamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

public class AddNewItemActivity extends SingleFragmentActivity implements AddNewItemFragment.Callback{

    private static final String TAG = AddNewItemActivity.class.getSimpleName();

    public static final String ACTION_SAVE_ITEM = TAG + ".ACTION_SAVE_ITEM";
    public static final String ACTION_DELETE_ITEM = TAG + ".ACTION_DELETE_ITEM";

    String check;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String aB = getIntent().getStringExtra("updateTitle");



        Toast.makeText(this, ""+aB, Toast.LENGTH_SHORT).show();

        if(aB == null)
        {
            getSupportActionBar().setTitle("Edit Item");
        }
        else
            getSupportActionBar().setTitle("Add Item");

    }

    @Override
    protected Fragment getFragment() {



        String updateTitle = getIntent().getStringExtra("updateTitle");
        boolean check = getIntent().getBooleanExtra("updateCheck",false);
        int pos = getIntent().getIntExtra("position",0);
        return AddNewItemFragment.newInstance(updateTitle,check,pos);

    }


    @Override
    public void onSaveForDelete(int position) {
        Intent intent = new Intent(ACTION_DELETE_ITEM);
        intent.putExtra("updatePosition", position);
        setResult(Activity.RESULT_OK,intent);
        finish();

        /*Intent intent=new Intent(AddNewItemActivity.this,ToDoListActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
        //startActivityForResult(intent,456);
        */
    }

    @Override
    public void onSave(String title, boolean isCheck,int position) {

        Intent intent=new Intent(ACTION_SAVE_ITEM);
        intent.putExtra("title",title);
        intent.putExtra("check",isCheck);
        intent.putExtra("updatePosition",position);
        setResult(RESULT_OK,intent);
        finish();

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==456 && requestCode==RESULT_OK && data!=null)
        {


        }
    }*/


}
