package com.trackercovid.view;

import com.trackercovid.AppContainer;
import com.trackercovid.TrackerCovidApplication;
import com.trackercovid.contract.HeatMapContract;
import com.trackercovid.interactor.CountryLocalDataSourceImpl;
import com.trackercovid.interactor.CountryRemoteDataSourceImpl;
import com.trackercovid.interactor.CountryRepositoryImpl;
import com.trackercovid.presenter.HeatMapPresenter;
import com.trackercovid.util.CountryResponseUtil;

public class HeatMapActivity extends BaseFragmentHolderActivity {

    @Override
    protected void initializeFragment() {
        final AppContainer appContainer = ((TrackerCovidApplication) getApplication()).appContainer;
        final HeatMapFragment currentFragment = new HeatMapFragment();
        final HeatMapContract.Presenter presenter = new HeatMapPresenter(currentFragment, new CountryRepositoryImpl(
                new CountryRemoteDataSourceImpl(appContainer.countryService, new CountryResponseUtil()),
                new CountryLocalDataSourceImpl(appContainer.getAppDatabase(this).countryDao()),
                appContainer.getNetworkInfoSource(this)
        ));
        currentFragment.setPresenter(presenter);
        setCurrentFragment(currentFragment, false);
    }
}
