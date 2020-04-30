package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class JoystickActivity extends AppCompatActivity {

    Connector Car = new Connector();

    // Smart car control buttons in content_controls.xml
    final ImageButton forward = findViewById(R.id.forwardBtn);
    final ImageButton backward = findViewById(R.id.backwardBtn);
    final ImageButton left = findViewById(R.id.leftBtn);
    final ImageButton right = findViewById(R.id.rightBtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Click listeners for the smart car navigation control buttons
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goForward();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goLeft();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goRight();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goBackward();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void goForward() throws IOException { //Buttons to steer the car
        String msg = "c";
        Car.mmOutputStream.write(msg.getBytes());
    }

    void goLeft() throws IOException {
        String msg = "l";
        Car.mmOutputStream.write(msg.getBytes());
    }

    void goRight() throws IOException {
        String msg = "r";
        Car.mmOutputStream.write(msg.getBytes());
    }

    void goBackward() throws IOException {
        String msg = "b";
        Car.mmOutputStream.write(msg.getBytes());
    }
}
