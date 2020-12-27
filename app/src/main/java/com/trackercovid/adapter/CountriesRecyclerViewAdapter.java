package com.trackercovid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trackercovid.R;
import com.trackercovid.model.Country;

import java.util.List;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder> {
    private final List<Country> countries;

    public CountriesRecyclerViewAdapter(@NonNull List<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_country_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCountry.setText(countries.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tv_country);
        }
    }
}
