package com.example.panpan.amazingdrum.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.panpan.amazingdrum.JoinThread;
import com.example.panpan.amazingdrum.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class JoinActivity2 extends Activity {

    @InjectView(R.id.edit_room)
    EditText editRoom;
    @InjectView(R.id.button_join)
    Button buttonJoin;
    @InjectView(R.id.edit_name)
    EditText editName;
    private JoinThread joinThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.button_join)
    public void onViewClicked() {
        if (buttonJoin.getText().toString().equals("加入"))
            join();
        if (buttonJoin.getText().toString().equals("退出")) {
            close();
        }
    }

    private JoinThread.OnJoinListener onJoinListener = new JoinThread.OnJoinListener() {
        @Override
        public void OnStateChanged(JoinThread thread, JoinThread.State state) {
            switch (state) {
                case Linked:
                    break;
                case Verified:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonJoin.setEnabled(true);
                            buttonJoin.setText("退出");
                        }
                    });
                    break;
                case Dislink:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (buttonJoin.getText().toString().contains("正在加入")) {
                                Toast.makeText(JoinActivity2.this, "加入失败", Toast.LENGTH_LONG).show();
                            }
                            buttonJoin.setEnabled(true);
                            buttonJoin.setText("加入");
                        }
                    });
                    break;
            }
        }

        @Override
        public void OnDataReceived(JoinThread thread, byte[] data, int length) {
            PlayActivity.joinThread=joinThread;
            if (data[0] == 0x01) {
                if (MainBandActivity.instrumentType == 0x01) {
                    startActivity(new Intent(JoinActivity2.this, PlayDrumActivity2.class));
                    finish();
                }
                if (MainBandActivity.instrumentType == 0x00) {
                    startActivity(new Intent(JoinActivity2.this, PlayGuitarActivity2.class));
                    finish();
                }
                if (MainBandActivity.instrumentType == 0x02) {
                    startActivity(new Intent(JoinActivity2.this, PlayEGuitarActivity2.class));
                    finish();
                }
                if (MainBandActivity.instrumentType == 0x03) {
                    startActivity(new Intent(JoinActivity2.this, PlayBassActivity2.class));
                    finish();
                }
            }
        }
    };

    private void join() {
        buttonJoin.setEnabled(false);
        buttonJoin.setText("正在加入...");
        String host = editRoom.getText().toString();
        String name=editName.getText().toString();
        if(MainBandActivity.instrumentType==0)
            name+="——吉他";
        if(MainBandActivity.instrumentType==1)
            name+="——架子鼓";
        if(MainBandActivity.instrumentType==2)
            name+="——电吉他";
        if(MainBandActivity.instrumentType==3)
            name+="——贝斯";
        joinThread = new JoinThread(name, host);
        joinThread.setListener(onJoinListener);
        joinThread.start();
    }

    private void close() {
        buttonJoin.setEnabled(false);
        buttonJoin.setText("正在退出...");
        if (joinThread != null)
            joinThread.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
