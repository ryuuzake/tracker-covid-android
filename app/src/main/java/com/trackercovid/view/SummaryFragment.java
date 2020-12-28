package com.trackercovid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trackercovid.R;
import com.trackercovid.adapter.SummaryRecyclerViewAdapter;
import com.trackercovid.contract.SummaryContract;
import com.trackercovid.model.Summary;

public class SummaryFragment extends BaseFragment<SummaryContract.Presenter> implements SummaryContract.View {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summary_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress_circular);
        setUpRecyclerView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void showSummary(Summary summary) {
        recyclerView.setAdapter(new SummaryRecyclerViewAdapter(summary));
    }

    @Override
    public void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}
