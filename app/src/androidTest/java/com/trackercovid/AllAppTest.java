package com.trackercovid;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.trackercovid.view.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.trackercovid.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AllAppTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> scenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void allAppTest() {
        loginTest();

        homeTest();

        heatMapTest();

        caseCountryTest();

        countryTest();

        countryDetailTest();

        goBack();
    }

    protected void loginTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        sleep();

        ViewInteraction etEmail = onView(withId(R.id.et_email));
        etEmail.perform(replaceText("testing@example.com"), closeSoftKeyboard());

        ViewInteraction etPassword = onView(withId(R.id.et_password));
        etPassword.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction btLogin = onView(withId(R.id.bt_login));
        btLogin.perform(click()).check(matches(not(isDisplayed())));
    }

    protected void homeTest() {
        sleep();

        checkHome();

        onView(withId(R.id.btm_heatmap)).perform(click());
    }

    private void checkHome() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    protected void heatMapTest() {
        sleep();

        checkHeatMap();

        onView(withId(R.id.btm_country)).perform(click());
    }

    private void checkHeatMap() {
        onView(withId(R.id.mapView)).check(matches(isDisplayed()));
    }

    protected void caseCountryTest() {
        sleep();

        checkCaseCountry();

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    private void checkCaseCountry() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    protected void countryTest() {
        sleep();

        checkCountry();

        onView(withId(R.id.btn_see_more)).perform(click());
    }

    private void checkCountry() {
        onView(withId(R.id.mapView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    protected void countryDetailTest() {
        sleep();

        final ViewInteraction recyclerView = onView(withId(R.id.recyclerView));
        recyclerView.check(matches(isDisplayed()));
        recyclerView.check(withItemCount(greaterThan(3)));
    }

    protected void goBack() {
        sleep();
        for (int i = 0; i < 4; i++) {
            onView(isRoot()).perform(pressBack());

            sleep();
            switch (i) {
                case 0:
                    checkCountry();
                    break;
                case 1:
                    checkCaseCountry();
                    break;
                case 2:
                    checkHeatMap();
                    break;
                case 3:
                    checkHome();
                    break;
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
