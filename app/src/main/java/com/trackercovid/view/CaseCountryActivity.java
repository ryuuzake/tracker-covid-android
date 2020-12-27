package com.trackercovid.view;

import com.trackercovid.AppContainer;
import com.trackercovid.TrackerCovidApplication;
import com.trackercovid.contract.CaseCountryContract;
import com.trackercovid.interactor.CountryLocalDataSourceImpl;
import com.trackercovid.interactor.CountryRemoteDataSourceImpl;
import com.trackercovid.interactor.CountryRepositoryImpl;
import com.trackercovid.presenter.CaseCountryPresenter;
import com.trackercovid.util.CountryResponseUtil;

public class CaseCountryActivity extends BaseFragmentHolderActivity {
    @Override
    protected void initializeFragment() {
        final CaseCountryFragment fragment = new CaseCountryFragment();
        fragment.setPresenter(getPresenter(fragment));
        setCurrentFragment(fragment, false);
    }

    private CaseCountryContract.Presenter getPresenter(CaseCountryFragment fragment) {
        final AppContainer appContainer = ((TrackerCovidApplication) getApplication()).appContainer;
        return new CaseCountryPresenter(fragment, new CountryRepositoryImpl(
                new CountryRemoteDataSourceImpl(appContainer.countryService, new CountryResponseUtil()),
                new CountryLocalDataSourceImpl(appContainer.getAppDatabase(this).countryDao()),
                appContainer.getNetworkInfoSource(this)
        ));
    }
}
