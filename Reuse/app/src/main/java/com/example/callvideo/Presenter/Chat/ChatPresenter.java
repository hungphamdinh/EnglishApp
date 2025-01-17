package com.example.callvideo.Presenter.Chat;

import com.example.callvideo.Model.Entities.Chat;
import com.example.callvideo.View.Chat.IUserChatView;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatPresenter implements ChatListener {

    public IUserChatView userChatView;
    public UserChat userChat;
    public ChatPresenter(IUserChatView userChatView){
        this.userChatView=userChatView;
        userChat=new UserChat(this);
    }
    public void loadChat(HashMap<String,Object>idMap)
    {
        userChat.accessToUser(idMap);
    }
    public void clickSend(HashMap<String,Object>sendMap){
        userChat.onClickSend(sendMap);

    }
    public void setStatus(String status,String userPhone){
        userChat.setStatus(status,userPhone);
    }
    @Override
    public void onClickSendMsg(HashMap<String, Object> msgMap) {
        userChatView.onClickSendMsg(msgMap);
    }

    @Override
    public void onError(String msg) {
        userChatView.onError(msg);
    }

    @Override
    public void sendMsg(HashMap<String, Object> map) {
        userChatView.onClickSendMsg(map);
    }

    @Override
    public void onAccesstoUser(String tutorName) {
        userChatView.onAccesstoUser(tutorName);
    }

    @Override
    public void readMsg(ArrayList<Chat> chats,ArrayList<String>keys) {
        userChatView.readMsg(chats,keys);
    }
}
