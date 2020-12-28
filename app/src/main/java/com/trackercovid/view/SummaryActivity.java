package com.trackercovid.view;

import com.trackercovid.AppContainer;
import com.trackercovid.TrackerCovidApplication;
import com.trackercovid.contract.SummaryContract;
import com.trackercovid.db.SummaryDatabase;
import com.trackercovid.interactor.SummaryLocalDataSourceImpl;
import com.trackercovid.interactor.SummaryRemoteDataSourceImpl;
import com.trackercovid.interactor.SummaryRepositoryImpl;
import com.trackercovid.presenter.SummaryPresenter;
import com.trackercovid.util.CountryResponseUtil;

public class SummaryActivity extends BaseFragmentHolderActivity {
    @Override
    protected void initializeFragment() {
        final SummaryFragment fragment = new SummaryFragment();
        fragment.setPresenter(getPresenter(fragment));
        setCurrentFragment(fragment, true);
    }

    private SummaryContract.Presenter getPresenter(SummaryFragment fragment) {
        final AppContainer appContainer = ((TrackerCovidApplication) getApplication()).appContainer;
        return new SummaryPresenter(fragment, new SummaryRepositoryImpl(
                new SummaryRemoteDataSourceImpl(appContainer.countryService, new CountryResponseUtil()),
                new SummaryLocalDataSourceImpl(new SummaryDatabase(this)),
                appContainer.getNetworkInfoSource(this)
        ));
    }
}
