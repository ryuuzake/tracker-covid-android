package com.trackercovid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.trackercovid.R;
import com.trackercovid.model.Summary;
import com.trackercovid.util.BigNumberUtil;

public class SummaryRecyclerViewAdapter extends RecyclerView.Adapter<SummaryRecyclerViewAdapter.ViewHolder> {
    final int FIXED_SIZE = 5;
    private final Summary summary;
    private Context mContext;

    public SummaryRecyclerViewAdapter(Summary summary) {
        this.summary = summary;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.summary_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.tvNumber.setText(BigNumberUtil.format(summary.getCases()));
                holder.tvDescription.setText(R.string.confirmed);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.cases));
                break;
            case 1:
                holder.tvNumber.setText(BigNumberUtil.format(summary.getDeaths()));
                holder.tvDescription.setText(R.string.deaths);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.deaths));
                break;
            case 2:
                holder.tvNumber.setText(BigNumberUtil.format(summary.getRecovered()));
                holder.tvDescription.setText(R.string.recovered);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.recovered));
                break;
            case 3:
                holder.tvNumber.setText(BigNumberUtil.format(summary.getActive()));
                holder.tvDescription.setText(R.string.active);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.active));
                break;
            case 4:
                holder.tvNumber.setText(BigNumberUtil.format(summary.getCritical()));
                holder.tvDescription.setText(R.string.critical);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.critical));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return FIXED_SIZE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNumber;
        private final TextView tvDescription;
        private final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvDescription = itemView.findViewById(R.id.tv_description);
            cardView = (CardView) itemView.getRootView();
        }
    }
}
