package com.talkwithstranger.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.talkwithstranger.controller.Server;
import com.talkwithstranger.data.ClientStatus;

import de.tavendo.autobahn.WebSocketConnection;


public class MainActivity extends Activity {
    private WebSocketConnection wsc = null;
    private Server server = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wsc = new WebSocketConnection();
        server = new Server(wsc, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            final AlertDialog isExit = new AlertDialog.Builder(this).create();
            isExit.show();
            Window window = isExit.getWindow();
            window.setContentView(R.layout.diy_dialog);
            Button exit = (Button) window.findViewById(R.id.dialog_button_ok);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isExit.dismiss();
                    MainActivity.this.finish();
                }
            });
            Button cancel = (Button) window.findViewById(R.id.dialog_button_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isExit.dismiss();
                }
            });
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy(){
        this.wsc.disconnect();
        ClientStatus.talkConnect = -1;
        super.onDestroy();
    }
}
