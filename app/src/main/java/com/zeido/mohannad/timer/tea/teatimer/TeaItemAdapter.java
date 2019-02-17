package com.zeido.mohannad.timer.tea.teatimer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;

import java.util.List;

public class TeaItemAdapter extends RecyclerView.Adapter<TeaItemAdapter.ViewHolder>{

    private List<Tea> mTeaList;
    private Context mContext;

    TeaItemAdapter(Context context, List<Tea> items) {
        this.mContext = context;
        this.mTeaList = items;
    }

    @Override
    public TeaItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.single_tea_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeaItemAdapter.ViewHolder holder, int position) {
        final Tea tea = mTeaList.get(position);
        holder.teaName.setText(tea.getTeaName());
        String instructions = mContext.getString(R.string.brew_minutes, tea.getBrewingTime()) + ", " + mContext.getString(R.string.brew_temperature, tea.getBrewingTemperature());
        holder.brewInstructions.setText(instructions);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "You selected " + tea.getTeaName(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), TimerPageActivity.class);
                intent.putExtra("teaObject", tea);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTeaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView teaName, brewInstructions;

        ViewHolder(View itemView) {
            super(itemView);

            teaName = itemView.findViewById(R.id.teaName);
            brewInstructions = itemView.findViewById(R.id.brewInstructions);
        }
    }
}
