package com.talkwithstranger.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.talkwithstranger.activity.R;
import com.talkwithstranger.data.TalkMessage;

import java.util.List;

/**
 * Created by rube on 14-8-17.
 */
public class TalkAdapter extends BaseAdapter {

    private Context context = null;
    private List<TalkMessage> chatList = null;
    private LayoutInflater inflater = null;
    private Bitmap[] bitmaps = null;
    public TalkAdapter(Context context, List<TalkMessage> chatList){
        this.context = context;
        this.chatList = chatList;
        this.inflater = LayoutInflater.from(this.context);
        this.bitmaps = new Bitmap[3];
        bitmaps[0] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.default_avatar);
        bitmaps[1] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.system_avatar);
        bitmaps[2] = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.my_avatar);
    }

    @Override
    public int getCount() {
        return this.chatList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount(){
        return 3;
    }

    @Override
    public int getItemViewType(int i){
        return this.chatList.get(i).getType()-1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatHolder chatHolder = null;
        TalkMessage message = chatList.get(i);
        if (view == null){
            chatHolder = new ChatHolder();
            switch (message.getType()) {
                case 1: {
                    view = inflater.inflate(R.layout.my_talk_item, null);
                    break;
                }
                case 2: {
                    view = inflater.inflate(R.layout.other_talk_item, null);
                    break;
                }
                case 3: {
                    view = inflater.inflate(R.layout.system_talk_item, null);
                    break;
                }
            }
            chatHolder.headView = (ImageView) view.findViewById(R.id.user_img);
            chatHolder.msgView = (TextView) view.findViewById(R.id.user_text);
            chatHolder.timeView = (TextView) view.findViewById(R.id.time);
            view.setTag(chatHolder);
        } else {
            chatHolder = (ChatHolder)view.getTag();
        }
        chatHolder.timeView.setText(message.getTime());
        chatHolder.msgView.setText(message.getContent());
        if (message.getType() == 3) {
            chatHolder.headView.setImageBitmap(bitmaps[1]);
        } else if (message.getType() == 2) {
            chatHolder.headView.setImageBitmap(bitmaps[0]);
        } else {
            chatHolder.headView.setImageBitmap(bitmaps[2]);
        }
        return view;
    }

    private class ChatHolder{
        private TextView timeView;
        private TextView msgView;
        private ImageView headView;
    }

    protected void finalize() throws java.lang.Throwable {
        int i;
        for (i = 0; i < this.bitmaps.length; i++) {
            if (!bitmaps[i].isRecycled()){
                bitmaps[i].recycle();
            }
        }
        System.gc();
        super.finalize();

    }
}
