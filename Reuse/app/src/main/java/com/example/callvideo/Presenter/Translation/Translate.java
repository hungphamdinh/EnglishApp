package com.example.callvideo.Presenter.Translation;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.example.callvideo.R;
import com.example.callvideo.Service.APIHelper;
import com.example.callvideo.Service.Client;
import com.example.callvideo.Model.Entities.Languages;
import com.example.callvideo.Model.Entities.TranslatedText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Retrofit;

public class Translate {
    private ITranslateListener iTranslateListener;
    private boolean noTranslate=true;
    public Translate(ITranslateListener iTranslateListener){
        this.iTranslateListener=iTranslateListener;
    }

    public void textChangedListener(String textToTranslate, HashMap<String,Object>spinnerMap) {
         translate(textToTranslate,spinnerMap);
         //checkIfInFavourites();

    }

    private void translate(String text,HashMap<String,Object>spinnerMap){
        if(noTranslate){
            noTranslate = false;
            return;
        }
        String APIKey = "trnsl.1.1.20170314T200256Z.c558a20c3d6824ff.7" +
                "860377e797dffcf9ce4170e3c21266cbc696f08";
        String language1 = spinnerMap.get("spinner1").toString();
        String language2 = spinnerMap.get("spinner2").toString();

        Retrofit query = Client.getClient("https://translate.yandex.net/");

        APIHelper apiHelper = query.create(APIHelper.class);
        Call<TranslatedText> call = apiHelper.getTranslation(APIKey, text,
                langCode(language1) + "-" + langCode(language2));
        iTranslateListener.onReturnRespone(call,text);
    }

    public String langCode(String selectedLang) {
        String code = null;

        if(Locale.getDefault().getLanguage().equals("en")) {
            for (int i = 0; i < Languages.getLangsEN().length; i++) {
                if(selectedLang.equals(Languages.getLangsEN()[i])){
                    code = Languages.getLangCodeEN(i);
                }
            }
        } else{
            for (int i = 0; i < Languages.getLangsEN().length; i++) {
                if(selectedLang.equals(Languages.getLangsEN()[i])){
                    code = Languages.getLangCodeEN(i);
                }
            }
        }
        return code;
    }
    public void setTextToFirebase(HashMap<String,Object>wordMap){
        DatabaseReference wordRef= FirebaseDatabase.getInstance().getReference("Word");
        wordRef.push().setValue(wordMap);
        iTranslateListener.onSetFavorite("Đã thêm vào sổ tay từ vựng");
    }
    public void speakOut(HashMap<String,Object>inputMap) {
        String txtMessage=inputMap.get("translatedText").toString();
        String mLanguageCodeTo=inputMap.get("languageCode").toString();
        TextToSpeech mTextToSpeech= (TextToSpeech) inputMap.get("textToSpeech");
        HashMap<String, String> map = new HashMap<>();
        int result = mTextToSpeech.setLanguage(new Locale(mLanguageCodeTo));
        Log.e("Inside", "speakOut " + mLanguageCodeTo + " " + result);
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            iTranslateListener.onMissedPackage("Language package is missing");
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            //Toast.makeText(getApplicationContext(),getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            iTranslateListener.onNotSupport("This language isn't support");
        } else {
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            mTextToSpeech.speak(txtMessage, TextToSpeech.QUEUE_FLUSH, map);
        }
    }
}
