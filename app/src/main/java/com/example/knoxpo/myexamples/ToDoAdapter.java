package com.example.knoxpo.myexamples;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> toDoModels;

    private OnItemClickListener mListener;

    public ToDoAdapter(List<ToDoModel> toDoModels) {
        this.toDoModels = toDoModels;
    }

    public void setONclick(OnItemClickListener listener) {

        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private TextView title;
        public CheckBox checkBox;
        private ToDoModel mBoundModel;

        public MyViewHolder(final View itemView) {

            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);
            checkBox = itemView.findViewById(R.id.checkboxItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mListener !=null)
                    {
                        int position=getAdapterPosition();

                        mListener.onItemClick(position);

                    }
                }
            });

        }


        public void bindToDoModel(ToDoModel toDoModel) {

            mBoundModel = toDoModel;
            title.setText(toDoModel.getTitle());
            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(toDoModel.isCheck());
            checkBox.setOnCheckedChangeListener(this);

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mBoundModel.setCheck(isChecked);
        }

    }

    @NonNull
    @Override
    public ToDoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todolistrow, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.MyViewHolder holder, int position) {

        final ToDoModel toDoModel = toDoModels.get(position);
        holder.bindToDoModel(toDoModel);
    }


    @Override
    public int getItemCount() {
        return toDoModels.size();
    }


    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}
