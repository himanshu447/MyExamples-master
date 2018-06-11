package com.example.knoxpo.myexamples;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddNewItemFragment extends Fragment {

    private EditText mETAddItem;
    private CheckBox mCBNewItem;
    private Button mbuttonsave;
    private int position;
    private static final int REQUEST_CODE = 123;
    private Callback mCallback;
    String title;
    boolean check;

    public static Fragment newInstance(String updateTitle, boolean check, int pos) {

        Bundle bundle = new Bundle();
        bundle.putString("updateTitle", updateTitle);
        bundle.putBoolean("updateCheck", check);
        bundle.putInt("updatePosition", pos);

        AddNewItemFragment addNewItemFragment = new AddNewItemFragment();
        addNewItemFragment.setArguments(bundle);
        return addNewItemFragment;
    }

    public interface Callback {

        void onSaveForDelete(int position);

        void onSave(String title, boolean isChecked, int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        title = getArguments().getString("updateTitle");
        check = getArguments().getBoolean("updateCheck");
        position = getArguments().getInt("updatePosition");


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) getActivity();
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_item, container, false);

        mETAddItem = view.findViewById(R.id.editTextAddItem);
        mCBNewItem = view.findViewById(R.id.checkboxNewItem);
        mbuttonsave = view.findViewById(R.id.buttonsave);

        if (title != null) {
            mETAddItem.setText(title);
            mCBNewItem.setChecked(check);
        }
        mbuttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.onSave(mETAddItem.getText().toString(), mCBNewItem.isChecked(), position);
            }
        });

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.deleteitem, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_item:

                //showDialog();

                DialogFragment newFragment = new AlertDialogFragment();

                Bundle bundle =new Bundle();
                bundle.putInt("position",position);
                newFragment.setArguments(bundle);

                newFragment.show(getFragmentManager(), "dialog");
                newFragment.setTargetFragment(this,REQUEST_CODE);


                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

       if(requestCode == REQUEST_CODE && resultCode== Activity.RESULT_OK && data!=null)
        {
            int pos = data.getIntExtra("position",0);

            mCallback.onSaveForDelete(pos);

        }
    }

    private AlertDialog.Builder showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item ? ")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mCallback.onSaveForDelete(position);

                    }
                });
        builder.create();
        builder.show();
        return builder;
    }

}
