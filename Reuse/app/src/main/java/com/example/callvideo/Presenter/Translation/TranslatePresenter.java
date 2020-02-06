package com.example.callvideo.Presenter.Translation;

import com.example.callvideo.Model.Entities.TranslatedText;
import com.example.callvideo.View.Translate.ITranslateView;

import java.util.HashMap;

import retrofit2.Call;

public class TranslatePresenter implements ITranslateListener{
    private Translate mainInterator;
    private ITranslateView translateView;
    public TranslatePresenter(ITranslateView translateView){
        this.translateView=translateView;
        mainInterator=new Translate(this);
    }
    public void onTranslate(String translateTest, HashMap<String,Object>spinnerMap){
        mainInterator.textChangedListener(translateTest,spinnerMap);
    }
    public void speakOut(HashMap<String,Object>inputMap){
        mainInterator.speakOut(inputMap);
    }
    public void onSetToFavourite(HashMap<String,Object>favorMap){
        mainInterator.setTextToFirebase(favorMap);
    }
    @Override
    public void onReturnRespone(Call<TranslatedText> call, String textTranslate) {
        translateView.onReturnRespone(call,textTranslate);
    }

    @Override
    public void onSetFavorite(String mssg) {
        translateView.onSetFavorite(mssg);
    }

    @Override
    public void onMissedPackage(String msg) {
        translateView.onMissedPackage(msg);
    }

    @Override
    public void onNotSupport(String msg) {
        translateView.onNotSupport(msg);
    }
}
