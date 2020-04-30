package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri; //for hyperlink in url
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import java.io.IOException;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import com.neurosky.connection.DataType.MindDataType;

public class EegActivity extends AppCompatActivity {

    static final String TAG = null;
    TextView tv_attention;
    TextView myLabel;
    TgStreamReader tgStreamReader;

    boolean eegActive = false;

    Connector Car = new Connector();
    Connector Headset = new Connector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eeg);

        tv_attention = (TextView) findViewById(R.id.tv_attention);

        // Buttons to connect to external hardware, in content_header.xml
        Button btn_connectcar = findViewById(R.id.connectCarBtn);
        Button connectBtnH = findViewById(R.id.connectHeadsetBtn);

        // Buttons to control the start and stop of eeg reading in UI, found in content_controls.xml
        final Button controlEeg = findViewById(R.id.controlEegBtn);

        // Click listeners for connecting to external hardware
        btn_connectcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Car.findBT("Car");
                try {
                    Car.openBT();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        connectBtnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Headset.findBT("Force Trainer II");
                try {
                    Headset.openBT();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        // Click listeners for starting and stopping the eeg reading in the UI

        if (eegActive == false) {
            controlEeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    controlEeg.setBackground(getDrawable(R.drawable.bg_eegcontrol_stop));
                    controlEeg.setText(getString(R.string.stop));

                    start();

                }
            });
        }

        // TODO: Bug! The button doesn't switch back from "stop" to "start"?

        if (eegActive == true) {
            controlEeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    controlEeg.setBackground(getDrawable(R.drawable.bg_eegcontrol_start));
                    controlEeg.setText(getString(R.string.start));

                    stop();

                }
            });
        }
    }

    //Starts reading eeg data
    public void start() {

        if (eegActive == false) {
            createStreamReader(Headset.mmDevice);

            tgStreamReader.connectAndStart();

            eegActive = true;
        }
    }

    //Stops reading eeg data
    public void stop() {

        if (eegActive == true) {
            tgStreamReader.stop();
            tgStreamReader.close();//if there is not stop cmd, please call close() or the data will accumulate
            tgStreamReader = null;

            eegActive = false;
        }
    }

    public TgStreamHandler callback = new TgStreamHandler() { //Handles data recieved from headset

        @Override
        public void onDataReceived(int datatype, int data, Object obj) { //A sort of constructor
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = datatype; //The type of data
            msg.arg1 = data; //The actual data
            msg.obj = obj;
            LinkDetectedHandler.sendMessage(msg);
        }

        @Override
        public void onStatesChanged(int connectionStates) { //Checks if the headset is connected

            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTED:
                    tgStreamReader.start();
                    break;
            }
        }

        @Override
        public void onRecordFail(int flag) { //not used
        }

        @Override
        public void onChecksumFail(byte[] payload, int length, int checksum) { //not used
        }

        private Handler LinkDetectedHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) { //Method is used to determine what kind of data we want to gather, and what we use the data for
                switch (msg.what) {
                    case MindDataType.CODE_ATTENTION: // Here we establish the data we want to gather
                        Log.d(TAG, "CODE_ATTENTION " + msg.arg1);
                        tv_attention.setText("" + msg.arg1);
                        if (msg.arg1 > 60) {
                            String msgn = "f";
                            try {
                                Car.mmOutputStream.write(msgn.getBytes());
                            } catch (IOException e) {
                            }
                        } else {
                            String msgn = "k";
                            try {
                                Car.mmOutputStream.write(msgn.getBytes());
                            } catch (IOException e) {
                            }
                        }
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
    };

    //go to webpage through the link in GitHub shape
    public void goToUrl(View view) {
        String url = "https://github.com/DIT112-V20/group-08";
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public TgStreamReader createStreamReader(BluetoothDevice bd) { //here the data reader is being created

        if (tgStreamReader == null) {
            tgStreamReader = new TgStreamReader(bd, callback);
            tgStreamReader.startLog();
        }
        return tgStreamReader;
    }
}




