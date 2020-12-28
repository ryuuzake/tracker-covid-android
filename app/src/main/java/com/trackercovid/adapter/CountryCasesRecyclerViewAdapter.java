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
import com.trackercovid.model.Country;
import com.trackercovid.util.BigNumberUtil;

public class CountryCasesRecyclerViewAdapter extends RecyclerView.Adapter<CountryCasesRecyclerViewAdapter.ViewHolder> {

    private final static int FIXED_VALUE = 3;
    private final Country mCountry;
    private Context mContext;

    public CountryCasesRecyclerViewAdapter(Country country) {
        this.mCountry = country;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_cases_card_item, parent, false);

        final int height = ViewGroup.LayoutParams.MATCH_PARENT;
        final int DISTANCE_BETWEEN_VIEW = 16;
        int width = (parent.getMeasuredWidth() / FIXED_VALUE) - DISTANCE_BETWEEN_VIEW;

        final RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width, height);
        final int MARGIN = 8;
        params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
        view.setLayoutParams(params);

        mContext = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.tvNumber.setText(BigNumberUtil.format(mCountry.getCases()));
                holder.tvDescription.setText(R.string.confirmed);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.cases));
                break;
            case 1:
                holder.tvNumber.setText(BigNumberUtil.format(mCountry.getRecovered()));
                holder.tvDescription.setText(R.string.recovered);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.recovered));
                break;
            case 2:
                holder.tvNumber.setText(BigNumberUtil.format(mCountry.getDeaths()));
                holder.tvDescription.setText(R.string.deaths);
                holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.deaths));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return FIXED_VALUE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private TextView tvNumber;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvDescription = itemView.findViewById(R.id.tv_description);
            cardView = (CardView) itemView.getRootView();
        }
    }
}
