package com.example.countdownapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mTimeList = new ArrayList<>();
    private ArrayList<String> mTaskList = new ArrayList<>();
    private ArrayList<String> mDateList = new ArrayList<>();
    private Context mContext;
    private RecyclerViewAdapter recyclerViewAdapter;

    public RecyclerViewAdapter(ArrayList<String> timeList, ArrayList<String> taskList,
                               ArrayList<String> dateList, Context context){

        mTaskList = taskList;
        mTimeList = timeList;
        mDateList = dateList;
        mContext = context;
        recyclerViewAdapter = this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tasklist, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.dateviewTv.setText(mDateList.get(position));
        holder.taskviewTv.setText(mTaskList.get(position));
        holder.timeviewTv.setText(mTimeList.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mTaskList.get(position), Toast.LENGTH_SHORT).show();
                Intent viewTaskIntent = new Intent(mContext, ViewTaskActivity.class);
                viewTaskIntent.putExtra("task", mTaskList.get(position));
                mContext.startActivity(viewTaskIntent);
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                                                                        ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String taskRemoved = mTaskList.get((int)viewHolder.getAdapterPosition());
                DatabaseHelper db = new DatabaseHelper(mContext);
                db.deleteData(taskRemoved);

                mTaskList.remove(viewHolder.getAdapterPosition());
                mDateList.remove(viewHolder.getAdapterPosition());
                mTimeList.remove(viewHolder.getAdapterPosition());

                recyclerViewAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView timeviewTv, taskviewTv, dateviewTv;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeviewTv = (TextView)itemView.findViewById(R.id.tv_timeview);
            taskviewTv = (TextView)itemView.findViewById(R.id.tv_taskview);
            dateviewTv = (TextView)itemView.findViewById(R.id.tv_dateview);

            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.layout_relative);


        }
    }
}
