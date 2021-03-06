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
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityScenarioRule<RegisterActivity> scenarioRule = new ActivityScenarioRule<>(RegisterActivity.class);

    @Test
    public void registerActivityTest_success() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction etFirstName = onView(withId(R.id.et_first_name));
        etFirstName.perform(replaceText("User"), closeSoftKeyboard());

        ViewInteraction etLastName = onView(withId(R.id.et_last_name));
        etLastName.perform(replaceText("Testing"), closeSoftKeyboard());

        ViewInteraction etEmail = onView(withId(R.id.et_email));
        etEmail.perform(replaceText("testing@example.com"), closeSoftKeyboard());

        ViewInteraction etPassword = onView(withId(R.id.et_password));
        etPassword.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction etConfirmPassword = onView(withId(R.id.et_confirm_password));
        etConfirmPassword.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction btRegister = onView(withId(R.id.bt_register));
        btRegister.perform(click()).check(matches(not(isDisplayed())));
    }
}
