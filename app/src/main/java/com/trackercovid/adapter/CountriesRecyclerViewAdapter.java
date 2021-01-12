package com.trackercovid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trackercovid.R;
import com.trackercovid.callback.ListClickListener;
import com.trackercovid.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder>
        implements Filterable {
    private final ListClickListener clickListener;
    private List<Country> countries;
    private List<Country> filteredCountries;

    public CountriesRecyclerViewAdapter(ListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_country_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Country country = filteredCountries.get(position);
        holder.tvCountry.setText(country.getName());
        holder.itemView.setOnClickListener(v -> clickListener.onCountryClick(country));
    }

    @Override
    public int getItemCount() {
        return (filteredCountries == null) ? 0 : filteredCountries.size();
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        this.filteredCountries = countries;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final String string = charSequence.toString();
                final FilterResults filterResults = new FilterResults();
                List<Country> filteredCountries = new ArrayList<>();

                if (string.isEmpty()) {
                    filteredCountries = countries;
                } else {
                    for (Country country : countries) {
                        if (country.getName().toLowerCase().contains(string.toLowerCase())) {
                            filteredCountries.add(country);
                        }
                    }
                }
                filterResults.values = filteredCountries;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCountries = (List<Country>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tv_country);
        }
    }
}
