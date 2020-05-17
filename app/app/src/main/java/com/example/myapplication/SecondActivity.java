package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity implements JoyStick.JoyStickListener {

    public static void start(Context context) {
    Intent starter = new Intent(context, com.example.myapplication.SecondActivity.class);
     context.startActivity(starter);
        }

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        JoyStick joy1 = (JoyStick) findViewById(R.id.joy1);
        joy1.setListener(this);
        joy1.setPadColor(Color.parseColor("#55ffffff"));
//        joy1.setButtonColor(Color.parseColor("#55ff0000"));
        joy1.setPadBackground(R.drawable.icon_round_arrow);



    JoyStick joy2 = (JoyStick) findViewById(R.id.joy2);
    joy2.setListener(this);
    joy2.enableStayPut(true);
    joy2.setPadBackground(R.drawable.pad);
    joy2.setButtonDrawable(R.drawable.button);
    }


    @Override
    public void onMove(JoyStick joyStick, double angle, double power, int direction) {
        switch (joyStick.getId()) {
//            case R.id.joy1:
//                gameView.move(angle, power);
//                break;
//            case R.id.joy2:
//                gameView.rotate(angle);
//                break;
        }
    }

    @Override
    public void onTap() {}

    @Override
    public void onDoubleTap() {}
}
