package com.example.knoxpo.myexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ToDoListFragment extends Fragment implements ToDoAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private List<ToDoModel> mToDoList = new ArrayList<>();
    private ToDoAdapter mToDoAdapter;
    private int position;
    private CallBackInterface mCallBackInterface;
    private static final int UPDATE_REQUESTCODE = 1;
    private static final int REQUEST_INSERT = 2;

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallBackInterface = (CallBackInterface) getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBackInterface = null;
    }*/

    public interface CallBackInterface {
        void onPassData(String himanshu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        position = getArguments().getInt("pos", 0);

    }

    public static Fragment newInstance(int pos) {

        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);

        ToDoListFragment toDoListFragment = new ToDoListFragment();
        toDoListFragment.setArguments(bundle);

        return toDoListFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mRecyclerView = view.findViewById(R.id.todo_recycler_view);


        mToDoAdapter = new ToDoAdapter(mToDoList);

        mToDoAdapter.setONclick(ToDoListFragment.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mToDoAdapter);


        prepareData();

        removeData();

        return view;
    }

    private void removeData() {

        if (position != 0) {
            ToDoModel toDoModel = new ToDoModel();
            toDoModel.setTitle("");
            toDoModel.setCheck(false);
            mToDoList.remove(position);
            mToDoAdapter.notifyDataSetChanged();


        }
    }

    private void prepareData() {

        for (int i = 0; i < 100; i++) {
            ToDoModel toDoModel = new ToDoModel();
            toDoModel.setTitle("item " + i);
            mToDoList.add(toDoModel);
        }
        mToDoAdapter.notifyDataSetChanged();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.additem, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.new_item:

                Intent intent = new Intent(getActivity(), AddNewItemActivity.class);
                startActivityForResult(intent, REQUEST_INSERT);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_INSERT && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            boolean check = data.getBooleanExtra("check", false);

            ToDoModel toDoModel = new ToDoModel();
            toDoModel.setTitle(title);
            toDoModel.setCheck(check);
            mToDoList.add(toDoModel);

            mToDoAdapter.notifyItemInserted(mToDoList.size() - 1);
        } else if (requestCode == UPDATE_REQUESTCODE && resultCode == RESULT_OK && data != null) {
            int pos = data.getIntExtra("updatePosition", 0);

            if (AddNewItemActivity.ACTION_DELETE_ITEM.equals(data.getAction())) {
                mToDoList.remove(pos);
                mToDoAdapter.notifyItemRemoved(pos);
            } else if (AddNewItemActivity.ACTION_SAVE_ITEM.equals(data.getAction())) {
                String updateTitle = data.getStringExtra("title");
                boolean updateCheck = data.getBooleanExtra("check", false);

                ToDoModel toDoModel = new ToDoModel();
                toDoModel.setTitle(updateTitle);
                toDoModel.setCheck(updateCheck);
                mToDoList.set(pos, toDoModel);

                //mToDoAdapter.notifyDataSetChanged();
                mToDoAdapter.notifyItemChanged(pos);
            }

        }
    }


    @Override
    public void onItemClick(int position) {

        int pos = position;
        String s = mToDoList.get(position).getTitle();
        boolean check = mToDoList.get(position).isCheck();

        Intent intent = new Intent(getActivity(), AddNewItemActivity.class);
        intent.putExtra("updateTitle", s);
        intent.putExtra("updateCheck", check);
        intent.putExtra("position", pos);

        startActivityForResult(intent, UPDATE_REQUESTCODE);

    }


}
