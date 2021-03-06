package com.example.panpan.amazingdrum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.panpan.amazingdrum.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainBandActivity extends PermissionReqiureActivity {

    @InjectView(R.id.button1)
    Button button1;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;
    public static int instrumentType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_band);
        ButterKnife.inject(this);
        setButtonVisible(true);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                instrumentType = 0;
                startActivity(new Intent(this, JoinActivity2.class));
                break;
            case R.id.button2:
                instrumentType = 1;
                startActivity(new Intent(this, JoinActivity2.class));
                break;
            case R.id.button3:
                instrumentType = 2;
                startActivity(new Intent(this, JoinActivity2.class));
                break;
            case R.id.button4:
                instrumentType = 3;
                startActivity(new Intent(this, JoinActivity2.class));
                break;
            case R.id.button5:
                setButtonVisible(false);
                break;
            case R.id.button6:
                startActivity(new Intent(this, RoomActivity2.class));
                break;
        }
    }

    private void setButtonVisible(boolean model) {
        if (model) {
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
            button4.setVisibility(View.GONE);
            button5.setVisibility(View.VISIBLE);
            button6.setVisibility(View.VISIBLE);
        } else {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            button5.setVisibility(View.GONE);
            button6.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (button1.getVisibility() != View.VISIBLE)
                return super.onKeyDown(keyCode, event);
            else {
                setButtonVisible(true);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
