package com.raysk.timetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.raysk.timetracker.databinding.ActivityTestBinding;

public class ActivityTest extends DrawerBaseActivity {

    ActivityTestBinding activityTestBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTestBinding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(activityTestBinding.getRoot());
        allocateActivityTitle("Test");
    }
}