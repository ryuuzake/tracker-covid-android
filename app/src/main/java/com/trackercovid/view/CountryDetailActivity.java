package com.trackercovid.view;

import com.google.gson.Gson;
import com.trackercovid.AppContainer;
import com.trackercovid.TrackerCovidApplication;
import com.trackercovid.contract.CountryDetailContract;
import com.trackercovid.interactor.HistoricalRemoteDataSourceImpl;
import com.trackercovid.interactor.HistoricalRepositoryImpl;
import com.trackercovid.model.Country;
import com.trackercovid.presenter.CountryDetailPresenter;
import com.trackercovid.util.HistoricalResponseUtil;

import static com.trackercovid.constant.Constants.COUNTRY_KEY;

public class CountryDetailActivity extends BaseFragmentHolderActivity {
    @Override
    protected void initializeFragment() {
        final Country country = new Gson().fromJson(getIntent().getStringExtra(COUNTRY_KEY), Country.class);
        final CountryDetailFragment fragment = new CountryDetailFragment(country);
        fragment.setPresenter(getPresenter(fragment));
        setCurrentFragment(fragment, true);
    }

    private CountryDetailContract.Presenter getPresenter(CountryDetailFragment fragment) {
        final AppContainer appContainer = ((TrackerCovidApplication) getApplication()).appContainer;
        return new CountryDetailPresenter(fragment, new HistoricalRepositoryImpl(
                new HistoricalRemoteDataSourceImpl(appContainer.historicalService, new HistoricalResponseUtil()),
                appContainer.getNetworkInfoSource(this)
        ));
    }
}
