package com.example.testcar;

import androidx.appcompat.app.AppCompatActivity;

import com.neurosky.connection.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    TextView myLabel;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    OutputStream mmOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLabel = (TextView) findViewById(R.id.myLabel);

        Button forwardBtn = (Button) findViewById(R.id.forwardBtn);
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goForward();
                } catch (IOException ex) {
                }
            }
        });
        Button connectBtn = (Button) findViewById(R.id.connectbtn);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    findBT();
                    openBT();
                } catch (IOException ex) {
                }
            }
        });
        Button leftBtn = (Button) findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goLeft();
                } catch (IOException ex) {
                }
            }
        });
        Button rightBtn = (Button) findViewById(R.id.rightBtn);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goRight();
                } catch (IOException ex) {
                }
            }
        });
        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    goBack();
                } catch (IOException ex) {
                }
            }
        });
    }


    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            myLabel.setText("No bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("Car"))// change accordingly
                {
                    mmDevice = device;
                    myLabel.setText("Bluetooth Device Found");
                    break;
                } else {
                    myLabel.setText("Bluetooth Device NOT Found");
                }
            }
        }

    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        if (mmDevice != null) {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();

        }
    }

    void goForward() throws IOException {
        String msg = "f";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Forward!");
    }
    void goBack() throws IOException {
        String msg = "b";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Backwards!");
    }
    void goLeft() throws IOException {
        String msg = "l";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Left!");
    }
    void goRight() throws IOException {
        String msg = "r";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Right!");
    }
    void goForwardLeft() throws IOException {
        String msg = "fl";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Forward Left!");
    }
    void goForwardRight() throws IOException {
        String msg = "fr";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Forward Right!");
    }
    void goBackwardLeft() throws IOException {
        String msg = "bl";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Backward Left!");
    }
    void goBackwardRight() throws IOException {
        String msg = "br";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Going Backward Right!");
    }
}
