package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Header extends AppCompatActivity {

    // Header buttons in content_header.xml
    final ImageButton github = findViewById(R.id.githubBtn);

    final Button connectCar = findViewById(R.id.connectCarBtn);
    final Button connectedCar = findViewById(R.id.connectedCarBtn);

    final Button connectHeadset = findViewById(R.id.connectHeadsetBtn);
    final Button connectedHeadset = findViewById(R.id.connectedHeadsetBtn);

    final Button switchToJoystick = findViewById(R.id.switchToJoystickBtn);
    final Button switchToEeg = findViewById(R.id.switchToEegBtn);

    // Button logic for external GitHub link
    public static void github() {
        // Issues #32 && #37
    }

    // Methods for switching header button styles
    public void displayCarConnected() {
        connectCar.setVisibility(View.GONE);
        connectedCar.setVisibility(View.VISIBLE);
    }

    public void displayCarDisconnected() {
        connectCar.setVisibility(View.VISIBLE);
        connectedCar.setVisibility(View.GONE);
    }

    public void displayHeadsetConnected() {
        connectHeadset.setVisibility(View.GONE);
        connectedHeadset.setVisibility(View.VISIBLE);
    }

    public void displayHeadsetDisconnected() {
        connectHeadset.setVisibility(View.VISIBLE);
        connectedHeadset.setVisibility(View.GONE);
    }

    public void displayEegSwitch() {
        switchToJoystick.setVisibility(View.GONE);
        switchToEeg.setVisibility(View.VISIBLE);
    }

    public void displayControlsSwitch() {
        switchToJoystick.setVisibility(View.VISIBLE);
        switchToEeg.setVisibility(View.GONE);
    }

}
