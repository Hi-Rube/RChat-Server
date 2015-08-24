package com.talkwithstranger.controller;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.talkwithstranger.activity.R;
import com.talkwithstranger.adapter.TalkAdapter;
import com.talkwithstranger.data.ClientStatus;
import com.talkwithstranger.data.TalkMessage;

import java.util.List;

/**
 * Created by rube on 14-8-17.
 */
public class ReceiveController {
    public static void parse(String content, Activity act, ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter){
        JSONObject params = JSON.parseObject(content);
        int code = params.getInteger("code");
        String msg = params.getString("msg");
        controller(code, msg, act, chatView, chatList, chatAdapter);
    }

    private static void controller(int code, String msg, Activity act, ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter){
        switch (code) {
            case 200: {
                getWordMessage(msg, act, chatView, chatList, chatAdapter);
                break;
            }
            case 201: {
                connectSuccess(act, chatView, chatList, chatAdapter);
                break;
            }
            case 202: {
                disconnectSuccess(act, chatView, chatList, chatAdapter);
                break;
            }
            case 410: {
                disconnectSuccess(act, chatView, chatList, chatAdapter);
                break;
            }
        }
    }

    private static void connectSuccess(Activity act, ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter){
        ClientStatus.talkConnect = 1;
        Button switchButton = (Button) act.findViewById(R.id.switch_button);
        TextView titleText = (TextView) act.findViewById(R.id.title_text);
        EditText sendText = (EditText) act.findViewById(R.id.send_text);
        switchButton.setText("stop");
        titleText.setText("Talk With Stranger");
        String message = "Hi ~";
        sendText.setHint("说点什么吧~");
        SendController.sendFrom(chatView, chatList, chatAdapter, message);
    }

    private static void disconnectSuccess(Activity act, ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter){
        if (ClientStatus.talkConnect == -1){
            return;
        }
        ClientStatus.talkConnect = -1;
        Button switchButton = (Button) act.findViewById(R.id.switch_button);
        TextView titleText = (TextView) act.findViewById(R.id.title_text);
        EditText sendText = (EditText) act.findViewById(R.id.send_text);
        switchButton.setText("talk");
        titleText.setText("Stranger");
        String message = "对方结束聊天,点击talk键匹配聊天";
        sendText.setHint("");
        SendController.systemInformation(chatView, chatList, chatAdapter, message);
    }

    private static void getWordMessage(String message, Activity act, ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter){
        SendController.sendFrom(chatView, chatList, chatAdapter, message);
    }

}
