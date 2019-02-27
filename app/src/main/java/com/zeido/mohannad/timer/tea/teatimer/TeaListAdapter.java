package com.zeido.mohannad.timer.tea.teatimer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zeido.mohannad.timer.tea.teatimer.Database.Tea;

import java.util.List;

public class TeaListAdapter extends RecyclerView.Adapter<TeaListAdapter.ViewHolder>{

    private List<Tea> mTeaList;
    private Context mContext;

    TeaListAdapter(Context context) {
        this.mContext = context;
//        this.mTeaList = items;
    }

    @Override
    @NonNull
    public TeaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.single_tea_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeaListAdapter.ViewHolder holder, int position) {
        if(mTeaList != null){
            final Tea tea = mTeaList.get(position);
            holder.teaName.setText(tea.getTeaName());
            String instructions = mContext.getString(R.string.brew_minutes, tea.getBrewingTime()/60000) + ", " + mContext.getString(R.string.brew_temperature, tea.getBrewingTemperature());
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

        }else{
            holder.teaName.setText("No Tea Yet");
        }
    }

    public void setTeas(List<Tea> teas){
        mTeaList = teas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTeaList != null) {
            return mTeaList.size();
        }
        else{
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView teaName, brewInstructions;

        ViewHolder(View itemView) {
            super(itemView);

            teaName = itemView.findViewById(R.id.teaName);
            brewInstructions = itemView.findViewById(R.id.brewInstructions);
        }
    }
}
