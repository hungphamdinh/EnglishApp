package com.example.callvideo.Presenter.Chat;

import com.example.callvideo.Model.Entities.Chat;

import java.util.ArrayList;
import java.util.HashMap;

public interface ChatListener {
    void onClickSendMsg(HashMap<String,Object>msgMap);
    void onError(String msg);
    void sendMsg(HashMap<String,Object>map);
    void onAccesstoUser(String tutorName);
    void readMsg(ArrayList<Chat>chats,ArrayList<String>keys);
}
