package com.trackercovid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.trackercovid.R;
import com.trackercovid.adapter.CountriesRecyclerViewAdapter;
import com.trackercovid.contract.CaseCountryContract;
import com.trackercovid.model.Country;

import java.util.List;

import static com.trackercovid.constant.Constants.COUNTRY_KEY;

public class CaseCountryFragment extends BaseFragment<CaseCountryContract.Presenter> implements CaseCountryContract.View {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.case_country_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress_circular);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    private void redirectToCountry(Country country) {
        Intent intent = new Intent(getActivity(), CountryActivity.class);
        intent.putExtra(COUNTRY_KEY, new Gson().toJson(country));
        startActivity(intent);
    }

    @Override
    public void showCountries(List<Country> countries) {
        recyclerView.setAdapter(new CountriesRecyclerViewAdapter(countries, this::redirectToCountry));
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
