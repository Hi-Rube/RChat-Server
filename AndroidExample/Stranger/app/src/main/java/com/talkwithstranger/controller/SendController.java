package com.talkwithstranger.controller;

import android.widget.ListView;

import com.talkwithstranger.adapter.TalkAdapter;
import com.talkwithstranger.data.TalkMessage;

import java.util.List;

import de.tavendo.autobahn.WebSocketConnection;

/**
 * Created by rube on 14-8-17.
 */
public class SendController {
    public static void connect(WebSocketConnection wsc) {
        wsc.sendTextMessage("{\"status\": 0}");
    }

    public static void disconnect(WebSocketConnection wsc) {
        wsc.sendTextMessage("{\"status\": -1}");
    }

    public static void sendWordMessage(WebSocketConnection wsc, String content) {
        wsc.sendTextMessage("{\"status\": 1, \"content\":\"" + content + "\"}");
    }

    public static void systemInformation(ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter, String content){
        TalkMessage talkMessage = new TalkMessage(3, content);
        chatList.add(talkMessage);
        chatAdapter.notifyDataSetChanged();
        chatView.setSelection(chatList.size() - 1);
    }

    public static void selfInformation(ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter, String content){
        TalkMessage talkMessage = new TalkMessage(1, content);
        chatList.add(talkMessage);
        chatAdapter.notifyDataSetChanged();
        chatView.setSelection(chatList.size() - 1);
    }

    public static void sendFrom(ListView chatView, List<TalkMessage> chatList, TalkAdapter chatAdapter, String content) {
        TalkMessage talkMessage = new TalkMessage(2, content);
        chatList.add(talkMessage);
        chatAdapter.notifyDataSetChanged();
        chatView.setSelection(chatList.size() - 1);
    }
}