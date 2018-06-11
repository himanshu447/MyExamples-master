package com.example.knoxpo.myexamples;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class AlertDialogFragment extends DialogFragment {




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int pos = getArguments().getInt("position");

        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete Item")
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

                        Intent intent=new Intent();
                        intent.putExtra("position",pos);
                        getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,intent);
                        dismiss();

                    }
                })
                .create();
    }
}