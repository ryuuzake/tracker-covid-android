package com.trackercovid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trackercovid.R;
import com.trackercovid.model.Historical;
import com.trackercovid.util.BigNumberUtil;
import com.trackercovid.util.DateUtil;

import java.util.Date;

public class HistoricalRecyclerViewAdapter extends RecyclerView.Adapter<HistoricalRecyclerViewAdapter.ViewHolder> {
    private final Historical historical;
    private final Object[] historicalKeys;

    public HistoricalRecyclerViewAdapter(@NonNull Historical historical) {
        this.historical = historical;
        this.historicalKeys = historical.getCases().keySet().toArray();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Date historicalKey = (Date) historicalKeys[position];
        holder.tvDate.setText(DateUtil.getDate(historicalKey));
        holder.tvConfirmed.setText(BigNumberUtil.format((long) historical.getCases().get(historicalKey)));
        holder.tvRecovered.setText(BigNumberUtil.format((long) historical.getRecovered().get(historicalKey)));
        holder.tvDeaths.setText(BigNumberUtil.format((long) historical.getDeaths().get(historicalKey)));
    }

    @Override
    public int getItemCount() {
        return historical.getCases().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvConfirmed;
        private final TextView tvRecovered;
        private final TextView tvDeaths;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvConfirmed = itemView.findViewById(R.id.tv_confirmed);
            tvRecovered = itemView.findViewById(R.id.tv_recovered);
            tvDeaths = itemView.findViewById(R.id.tv_deaths);
        }
    }
}
