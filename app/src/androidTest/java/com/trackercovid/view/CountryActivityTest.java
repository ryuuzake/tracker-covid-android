package com.trackercovid.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.gson.Gson;
import com.trackercovid.R;
import com.trackercovid.model.Country;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.trackercovid.constant.Constants.COUNTRY_KEY;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CountryActivityTest {

    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), CountryActivity.class);
        Bundle bundle = new Bundle();
        final Country country = new Country();
        country.setName("Italy");
        bundle.putString(COUNTRY_KEY, new Gson().toJson(country));
        intent.putExtras(bundle);
    }

    @Rule
    public ActivityScenarioRule<CountryActivity> scenarioRule = new ActivityScenarioRule<>(intent);

    @Test
    public void countryActivityTest_shown() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.mapView)).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }
}
