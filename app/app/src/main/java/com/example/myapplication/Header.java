package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Header extends AppCompatActivity {

    Connector Car = new Connector();
    boolean carConnectionActive = false;

    Connector Headset = new Connector();
    boolean headsetConnectionActive = false;

    // Header buttons in content_header.xml
    private ImageButton github = findViewById(R.id.githubBtn);

    private Button connectCar = findViewById(R.id.connectCarBtn);
    private Button connectedCar = findViewById(R.id.connectedCarBtn);

    private Button connectHeadset = findViewById(R.id.connectHeadsetBtn);
    private Button connectedHeadset = findViewById(R.id.connectedHeadsetBtn);

    private Button switchToJoystick = findViewById(R.id.switchToJoystickBtn);
    private Button switchToEeg = findViewById(R.id.switchToEegBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_header);

        // Hardware connection - start

        connectCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Car.findBT("Car");
            try {
                Car.openBT();
                carConnectionActive = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });

        if (carConnectionActive == true) {
            displayCarConnected();
        } else {
            displayCarDisconnected();
        }

        connectHeadset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Headset.findBT("Force Trainer II");
            try {
                Headset.openBT();
                headsetConnectionActive = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
            }

        });

        if (headsetConnectionActive == true) {
            displayHeadsetConnected();
        } else {
            displayCarDisconnected();
        }

        // Hardware connection - end

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

    // Go to webpage through the link in GitHub shape
    public void goToUrl(View view) {
        String url = "https://github.com/DIT112-V20/group-08";
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
