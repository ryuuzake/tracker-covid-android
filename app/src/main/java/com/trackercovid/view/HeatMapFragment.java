package com.trackercovid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.trackercovid.R;
import com.trackercovid.contract.HeatMapContract;
import com.trackercovid.model.Country;
import com.trackercovid.util.MapUtil;

import java.util.List;

public class HeatMapFragment extends BaseFragment<HeatMapContract.Presenter> implements HeatMapContract.View {

    private GoogleMap mMap;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heatmap_fragment, container, false);
        final SupportMapFragment fragment = SupportMapFragment.newInstance();
        fragment.getMapAsync(map -> {
            mMap = map;
            presenter.start();
        });
        requireFragmentManager().beginTransaction().replace(R.id.mapView, fragment).commit();
        progressBar = view.findViewById(R.id.progress_circular);
        return view;
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

    @Override
    public void showHeatMapData(List<Country> countries) {
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder()
                .weightedData(MapUtil.toWeightedLatLngs(countries))
                .radius(40)
                .maxIntensity(100_000)
                .build();

        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }
}
