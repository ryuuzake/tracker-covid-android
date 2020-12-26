package com.trackercovid.view;

import com.google.gson.Gson;
import com.trackercovid.AppContainer;
import com.trackercovid.TrackerCovidApplication;
import com.trackercovid.contract.SummaryContract;
import com.trackercovid.interactor.CountryLocalDataSourceImpl;
import com.trackercovid.interactor.CountryRemoteDataSourceImpl;
import com.trackercovid.interactor.CountryRepositoryImpl;
import com.trackercovid.model.Country;
import com.trackercovid.presenter.SummaryPresenter;
import com.trackercovid.util.CountryResponseUtil;

import static com.trackercovid.constant.Constants.COUNTRY_KEY;

public class SummaryActivity extends BaseFragmentHolderActivity {
    @Override
    protected void initializeFragment() {
        final String stringExtra = getIntent().getStringExtra(COUNTRY_KEY);
        final Country country = new Gson().fromJson(stringExtra, Country.class);
        final SummaryFragment fragment = new SummaryFragment(country);
        fragment.setPresenter(getPresenter(fragment));
        setCurrentFragment(fragment, true);
    }

    private SummaryContract.Presenter getPresenter(SummaryFragment fragment) {
        final AppContainer appContainer = ((TrackerCovidApplication) getApplication()).appContainer;
        return new SummaryPresenter(fragment, new CountryRepositoryImpl(
                new CountryRemoteDataSourceImpl(appContainer.countryService, new CountryResponseUtil()),
                new CountryLocalDataSourceImpl(appContainer.getAppDatabase(this).countryDao()),
                appContainer.getNetworkInfoSource(this)
        ));
    }
}
