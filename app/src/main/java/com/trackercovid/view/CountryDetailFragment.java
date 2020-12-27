package com.trackercovid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trackercovid.R;
import com.trackercovid.adapter.HistoricalRecyclerViewAdapter;
import com.trackercovid.contract.CountryDetailContract;
import com.trackercovid.model.Country;
import com.trackercovid.model.Historical;

public class CountryDetailFragment extends BaseFragment<CountryDetailContract.Presenter> implements CountryDetailContract.View {

    private Country country;
    private TextView tvCountry;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;

    public CountryDetailFragment() {
        super();
    }

    public CountryDetailFragment(Country country) {
        super();
//        this.country = country;
        final Country country1 = new Country();
        country1.setName("Indonesia");
        this.country = country1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_detail_fragment, container, false);
        tvCountry = view.findViewById(R.id.tv_country);
        recyclerView = view.findViewById(R.id.recyclerView);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        progressBar = view.findViewById(R.id.progress_circular);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        final LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layout);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.requestHistoricalData(country.getName());
    }

    @Override
    public void showHistoricalData(Historical historical) {
        tvCountry.setText(historical.getCountryName());
        recyclerView.setAdapter(new HistoricalRecyclerViewAdapter(historical));
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
