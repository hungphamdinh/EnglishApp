package com.example.callvideo.View.Translate;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.callvideo.Presenter.Translation.TranslatePresenter;
import com.example.callvideo.R;
import com.example.callvideo.Model.Entities.Languages;
import com.example.callvideo.Model.Entities.TranslatedText;
import com.example.callvideo.View.TranslationActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by almaz on 16.04.17.
 */

public class MainFragment extends Fragment implements ITranslateView,TextToSpeech.OnInitListener {
    private Button btnTranslate;
    private View rootView;
    private TextToSpeech textToSpeech;
    private ImageView btnSpeakOut,btnListen;
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText txtToTranslate;
    private ImageButton btnAddToFavourites;
    private ImageButton btnChangeLanguages;
    private TextView txtTranslated;
    private static final int REQ_CODE_SPEECH_INPUT = 1;
    private boolean isFavourite; // if current word is favourite.
    private boolean noTranslate; // do not translate at 1-st text changing. Need when initialize
                                // with some text.

    private TranslatePresenter translatePresenter;
    /**
     * Initialize widget elements and create view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return created view of fragment
     */
    private Context context;
    private String userPhone;
    public static final String LOG_TAG = MainFragment.class.getName();
    public static final int RESULT_OK = -1;

    private String wordTranslate;
    public MainFragment(Context context,String userPhone,String wordTranslate){
        this.context=context;
        this.userPhone=userPhone;
        this.wordTranslate=wordTranslate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    /*
                            Dialog box to show list of processed Speech to text results
                            User selects matching text to display in chat
                     */
                    final Dialog match_text_dialog = new Dialog(context);
                    match_text_dialog.setContentView(R.layout.dialog_matches_frag);
                    match_text_dialog.setTitle(getString(R.string.select_matching_text));
                    ListView textlist = (ListView)match_text_dialog.findViewById(R.id.list);
                    final ArrayList<String> matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,matches_text);
                    textlist.setAdapter(adapter);
                    textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            txtToTranslate.setText(matches_text.get(position));
                            match_text_dialog.dismiss();
                        }
                    });
                    match_text_dialog.show();
                    break;
                }
            }
        }
    }
    private void onClickSpeakOut() {
        textToSpeech = new TextToSpeech(context, this);
        HashMap<String,Object>inputMap=new HashMap<>();
        btnSpeakOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputMap.put("translatedText",txtTranslated.getText().toString());
                inputMap.put("languageCode","en");
                inputMap.put("textToSpeech",textToSpeech);
                translatePresenter.speakOut(inputMap);
            }
        });
    }

    /**
     * Add listeners and set data.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        spinner1 = (Spinner) rootView.findViewById(R.id.languages1);
        spinner2 = (Spinner) rootView.findViewById(R.id.languages2);
        txtToTranslate = (EditText) rootView.findViewById(R.id.textToTranslate);
        txtToTranslate.setMovementMethod(new ScrollingMovementMethod());
        txtToTranslate.setVerticalScrollBarEnabled(true);
        btnChangeLanguages = (ImageButton) rootView.findViewById(R.id.changeLanguages);
        btnAddToFavourites = (ImageButton) rootView.findViewById(R.id.addToFavourites1);
        btnTranslate=(Button)rootView.findViewById(R.id.btnTranslate);
        txtTranslated = (TextView) rootView.findViewById(R.id.translatedText);
        btnSpeakOut=(ImageView) rootView.findViewById(R.id.btnSpeakOut);
        btnListen=(ImageView)rootView.findViewById(R.id.btnListen);
        txtTranslated.setMovementMethod(new ScrollingMovementMethod());
        txtTranslated.setVerticalScrollBarEnabled(true);
        translatePresenter=new TranslatePresenter(this);
        txtToTranslate.setText(wordTranslate);
        // setArgs();
        btnSpeakOut.setEnabled(false);
        setSpinners();
        swapLanguage();
        onClickTranslate();
        onAddFavour();
        onClickSpeakOut();
        onClickListen();
        super.onViewCreated(view, savedInstanceState);
    }

    private void onClickListen() {
        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(context, getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void swapLanguage() {
        btnChangeLanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sourceLng = spinner1.getSelectedItemPosition();
                int targetLng = spinner2.getSelectedItemPosition();
                spinner1.setSelection(targetLng);
                spinner2.setSelection(sourceLng);
            }
        });
    }

    private void onAddFavour() {
        btnAddToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> favourMap=new HashMap<>();
                favourMap.put("userPhone",userPhone);
                favourMap.put("beTrans", txtToTranslate.getText().toString());
                favourMap.put("afTrans", txtTranslated.getText().toString());
                translatePresenter.onSetToFavourite(favourMap);
            }
        });
    }

    private void onClickTranslate() {
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> spinnerMap=new HashMap<>();
                spinnerMap.put("spinner1",spinner1.getSelectedItem());
                spinnerMap.put("spinner2",spinner2.getSelectedItem());
                translatePresenter.onTranslate(txtToTranslate.getText().toString(),spinnerMap);
            }
        });
    }

    @Override
    public void onDestroyView() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("selection1", spinner1.getSelectedItemPosition());
        editor.putInt("selection2", spinner2.getSelectedItemPosition());
        editor.putString("txtToTranslate", txtToTranslate.getText().toString());
        editor.putString("txtTranslated", txtTranslated.getText().toString());
        editor.putBoolean("isFavourite", isFavourite);
        editor.apply();
        super.onDestroyView();
    }
    public void setSpinners() {
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        if(Locale.getDefault().getLanguage().equals("en")) {
            Collections.addAll(categories, Languages.getLangsEN());
        } else{
            Collections.addAll(categories, Languages.getLangsEN());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        String compareValue1="English";
        String compareValue2="Vietnamese";

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);
        if (compareValue1 != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue1);
            spinner1.setSelection(spinnerPosition);
        }
        spinner2.setAdapter(dataAdapter);
        if (compareValue2 != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue2);
            spinner2.setSelection(spinnerPosition);
        }
    }

    @Override
    public void onReturnRespone(Call<TranslatedText> call, String textTranslate) {
        call.enqueue(new Callback<TranslatedText>() {
            @Override
            public void onResponse(Call<TranslatedText> call, Response<TranslatedText> response) {
                if(response.isSuccessful()){
                    if(getActivity() == null)
                        return;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtTranslated.setText(response.body().getText().get(0));
                            //                     checkIfInFavourites();
                            //                         addToHistory();
                            btnSpeakOut.setEnabled(true);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<TranslatedText> call, Throwable t) {}
        });
    }

    @Override
    public void onSetFavorite(String mssg) {
        Toast.makeText(context,mssg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMissedPackage(String msg) {
        Intent installIntent = new Intent();
        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        startActivity(installIntent);
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotSupport(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInit(int status) {
        Log.e("Inside----->", "onInit");
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(new Locale("en"));
            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(context, getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
            //mImageSpeak.setEnabled(true);
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.e("Inside","OnStart");
                    //process_tts.hide();
                }
                @Override
                public void onDone(String utteranceId) {
                }
                @Override
                public void onError(String utteranceId) {
                }
            });
        } else {
            Log.e(LOG_TAG,"TTS Initilization Failed");
        }
    }
}
