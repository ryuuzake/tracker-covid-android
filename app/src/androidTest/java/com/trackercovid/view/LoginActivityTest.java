package com.trackercovid.view;


import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.trackercovid.R;

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
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> scenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest_success() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction etEmail = onView(withId(R.id.et_email));
        etEmail.perform(replaceText("testing@example.com"), closeSoftKeyboard());

        ViewInteraction etPassword = onView(withId(R.id.et_password));
        etPassword.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction btLogin = onView(withId(R.id.bt_login));
        btLogin.perform(click()).check(matches(not(isDisplayed())));
    }

    @Test
    public void loginActivityTest_redirectToRegister() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction tvRegister = onView(withId(R.id.tv_register));
        tvRegister.perform(click());

        onView(withId(R.id.bt_register)).check(matches(isDisplayed()));
    }

    @Test
    public void loginActivityTest_redirectToRegisterAndGoBack() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction tvRegister = onView(withId(R.id.tv_register));
        tvRegister.perform(click());

        onView(withId(R.id.bt_register)).check(matches(isDisplayed()));

        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.bt_login)).check(matches(isDisplayed()));
    }
}
