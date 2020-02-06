package com.example.callvideo.View.Translate;

import com.example.callvideo.Model.Entities.TranslatedText;

import retrofit2.Call;

public interface ITranslateView {
    void onReturnRespone(Call<TranslatedText> call, String textTranslate);
    void onSetFavorite(String mssg);
    void onMissedPackage(String msg);
    void onNotSupport(String msg);
}
