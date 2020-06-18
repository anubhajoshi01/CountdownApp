package com.example.countdownapplication;

import android.Manifest;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NotificationTest {

    @Rule
    public ActivityTestRule<AddtaskActivity> mActivityTestRule = new ActivityTestRule<>(AddtaskActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_NOTIFICATION_POLICY);

    @Before
    public void setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

   /* @Before
    public void stub(){

        onView(withId(R.id.fab)).perform(click());
    }*/

    @Test
    public void main(){

        Calendar cal = Calendar.getInstance();
        int[] dateTime = new int[6];
        dateTime[0] = cal.get(Calendar.YEAR);
        dateTime[1] = cal.get(Calendar.MONTH);
        dateTime[2] = cal.get(Calendar.DAY_OF_MONTH);
        dateTime[3] = cal.get(Calendar.HOUR);
        dateTime[4] = cal.get(Calendar.MINUTE);
        dateTime[5] = cal.get(Calendar.SECOND);


        createTask(dateTime);
        wait(1);
        checkNotification();

    }




    public void createTask(int[] dateTime){
        onView(withId(R.id.ed_entertask)).perform(typeText("another task"));
        onView(withId(R.id.ed_setyear)).perform(typeText(String.valueOf(dateTime[0])));

        onView(withId(R.id.spinner_month)).perform(click());
        onData(Matchers.allOf(is(instanceOf(Integer.class)), is(dateTime[1]))).perform(click());

        onView(withId(R.id.spinner_day)).perform(click());
        onData(Matchers.allOf(is(instanceOf(Integer.class)), is(dateTime[2]))).perform(click());

        onView(withId(R.id.spinner_hour)).perform(click());
        onData(Matchers.allOf(is(instanceOf(Integer.class)), is(dateTime[3]))).perform(click());

        onView(withId(R.id.spinner_minute)).perform(click());
        onData(Matchers.allOf(is(instanceOf(Integer.class)), is(String.valueOf(dateTime[4]+1)))).perform(click());

        onView(withId(R.id.spinner_second)).perform(click());
        onData(Matchers.allOf(is(instanceOf(Integer.class)), is(dateTime[5]))).perform(click());

        onView(withId(R.id.btn_add)).perform(click());

    }


    public void wait(int millis){
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkNotification(){
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.openNotification();
        device.wait(Until.hasObject(By.text("Task scheduled due")) ,1000);
        UiObject2 title = device.findObject(By.text("Task scheduled due"));
        UiObject2 text = device.findObject(By.text("another task"));
        assertEquals("Task scheduled due", title.getText());
        assertEquals("another task", text.getText());

    }

    @After
    public void done(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }


}
