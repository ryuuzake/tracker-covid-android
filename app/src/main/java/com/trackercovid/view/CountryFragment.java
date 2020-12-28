package com.trackercovid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.gson.Gson;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.trackercovid.R;
import com.trackercovid.adapter.CountryCasesRecyclerViewAdapter;
import com.trackercovid.contract.CountryContract;
import com.trackercovid.model.Country;

import java.util.Collections;

import static com.trackercovid.constant.Constants.COUNTRY_KEY;

public class CountryFragment extends BaseFragment<CountryContract.Presenter> implements CountryContract.View {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvCountry;
    private TextView btnSeeMore;
    private GoogleMap mMap;
    private Country country;

    public CountryFragment() {
        super();
    }

    public CountryFragment(Country country) {
        super();
        this.country = country;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress_circular);
        tvCountry = view.findViewById(R.id.tv_country);
        btnSeeMore = view.findViewById(R.id.btn_see_more);
        setUpRecyclerView();
        setUpMap();
        btnSeeMore.setOnClickListener(v -> this.redirectToDetail());
        return view;
    }

    private void setUpMap() {
        final SupportMapFragment fragment = SupportMapFragment.newInstance();
        fragment.getMapAsync(map -> {
            mMap = map;
            presenter.requestCountryData(country);
        });
        requireFragmentManager().beginTransaction().replace(R.id.mapView, fragment).commit();
    }

    private void setUpRecyclerView() {
        final LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void showCountryData(Country country) {
        tvCountry.setText(country.getName());
        recyclerView.setAdapter(new CountryCasesRecyclerViewAdapter(country));
        LatLng latLng = new LatLng(country.getLatitude(), country.getLongitude());
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .weightedData(Collections.singletonList(new WeightedLatLng(latLng, country.getCases())))
                .radius(40)
                .maxIntensity(100_000)
                .build();

        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void redirectToDetail() {
        Intent intent = new Intent(getActivity(), CountryDetailActivity.class);
        intent.putExtra(COUNTRY_KEY, new Gson().toJson(country));
        startActivity(intent);
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
