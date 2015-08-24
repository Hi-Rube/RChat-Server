package com.talkwithstranger.controller;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.talkwithstranger.activity.R;
import com.talkwithstranger.adapter.TalkAdapter;
import com.talkwithstranger.data.ClientStatus;
import com.talkwithstranger.data.TalkMessage;

import java.util.ArrayList;
import java.util.List;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by rube on 14-8-17.
 */
public class Server {
    private WebSocketConnection wsc = null;
    private String host = "ws://stranger.taskoops.com:3636";
    private Activity act = null;
    private List<TalkMessage> chatList = null;
    private Button switchButton = null;
    private Button sendButton = null;
    private TextView titleText = null;
    private EditText sendText = null;
    private ListView chatView = null;
    private TalkAdapter chatAdapter = null;
    //0:未连接 1:连接
    private int webSocketStatus = 0;
    public Server(WebSocketConnection wsc, Activity act) {
        this.wsc = wsc;
        this.act = act;
        uiInit();
        String firstMessage = "Hello~ 点击talk键开始匹配聊天,点击stop结束聊天";
        TalkMessage talkMessage = new TalkMessage(3, firstMessage);
        chatList = new ArrayList<TalkMessage>();
        chatList.add(talkMessage);
        chatAdapter = new TalkAdapter(act.getApplicationContext(), chatList);
        chatView.setAdapter(chatAdapter);
        try {
            startServer();
            sendListener();
            switchListener();
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws WebSocketException {
        this.wsc.connect(host, new WebSocketHandler(){
            @Override
            public void onBinaryMessage(byte[] payload) {

            }

            @Override
            public void onClose(int code, String reason) {
                webSocketStatus = 0;
            }

            @Override
            public void onOpen() {
                webSocketStatus = 1;
            }
            @Override
            public void onRawTextMessage(byte[] payload) {

            }

            @Override
            public void onTextMessage(String payload) {
                ReceiveController.parse(payload, act, chatView, chatList, chatAdapter);
            }
        });
    }

    private void uiInit(){
        sendButton = (Button) this.act.findViewById(R.id.send_button);
        switchButton = (Button) this.act.findViewById(R.id.switch_button);
        chatView = (ListView) act.findViewById(R.id.listview);
        titleText = (TextView) act.findViewById(R.id.title_text);
        sendText = (EditText) act.findViewById(R.id.send_text);
    }

    private void switchListener(){
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webSocketStatus != 1){
                    String message = "正在连接服务器中~ 请稍等";
                    SendController.systemInformation(chatView, chatList, chatAdapter, message);
                    return;
                }
                if (ClientStatus.talkConnect == -1) {
                    ClientStatus.talkConnect = 0;
                    titleText.setText("Searching ...");
                    switchButton.setText("stop");
                    SendController.systemInformation(chatView, chatList, chatAdapter, "searching");
                    SendController.connect(wsc);
                } else {
                    ClientStatus.talkConnect = -1;
                    titleText.setText("Stranger");
                    switchButton.setText("talk");
                    String message = "停止成功~ 点击talk键匹配聊天";
                    SendController.systemInformation(chatView, chatList, chatAdapter, message);
                    SendController.disconnect(wsc);
                }
            }
        });
    }

    private void sendListener(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = sendText.getText().toString();
                sendText.setText("");
                if (webSocketStatus != 1){
                    String message = "正在连接服务器中~ 请稍等";
                    SendController.systemInformation(chatView, chatList, chatAdapter, message);
                    return;
                }
                if (ClientStatus.talkConnect == -1) {
                    String message = "还没有匹配到Stranger呢~";
                    SendController.systemInformation(chatView, chatList, chatAdapter, message);
                    return;
                } else if (ClientStatus.talkConnect == 0) {
                    String message = "正在寻找Stranger呢~";
                    SendController.systemInformation(chatView, chatList, chatAdapter, message);
                    return;
                }
                SendController.sendWordMessage(wsc, content);
                SendController.selfInformation(chatView, chatList, chatAdapter, content);
            }
        });
    }
}
