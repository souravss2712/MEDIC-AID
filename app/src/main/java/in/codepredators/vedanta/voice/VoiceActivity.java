package in.codepredators.vedanta.voice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hanks.htextview.evaporate.EvaporateTextView;
import com.hanks.htextview.fade.FadeTextView;
import com.hanks.htextview.typer.TyperTextView;
import com.scwang.wave.MultiWaveHeader;

import java.util.Locale;

import in.codepredators.vedanta.Constants;
import in.codepredators.vedanta.R;
import in.codepredators.vedanta.voice.states.MainMenu;
import in.codepredators.vedanta.voice.states.State;

public class VoiceActivity extends AppCompatActivity implements Constants {

    public SpeechRecognizer speechRecognizer;
    RecognitionListener recognitionListener;
    public MultiWaveHeader wave, waveInverted;
    public TyperTextView audioResult;
    public EvaporateTextView assistantOutput;
    public Intent recognizerIntent;
    public FadeTextView suggestions;
    public TextToSpeech tts;
    public State state;
    boolean ttsReady = false;
    public Switch mainSwitch;

    public EditText name, age, gender, address;

    Boolean currentStatus = false;
    ConstraintLayout constraintLayout;

    public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LightTheme);
        setContentView(R.layout.activity_voice);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mainSwitch = findViewById(R.id.switchWidget);
        constraintLayout = findViewById(R.id.root_layout);
        mainSwitch.setChecked(true);
        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    finish();
                }
            }
        });

        suggestions = findViewById(R.id.trysaying);
        wave = findViewById(R.id.wave);
        waveInverted = findViewById(R.id.wave_inv);
        assistantOutput = findViewById(R.id.assistant_output);
        audioResult = findViewById(R.id.audio_result);
        recognizerIntent = RecognizerIntent.getVoiceDetailsIntent(VoiceActivity.this);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "in.codepredators.vedanta");
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS)
                    ttsReady = true;
            }
        });

        state = new MainMenu(this);
        state.showSuggestions(suggestions);

        recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                animateWave(0.1f);

            }

            @Override
            public void onBeginningOfSpeech() {
                animateWave(0.5f);
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                animateWave(rmsdB / 10f);
            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                animateWave(0);
            }

            @Override
            public void onError(int error) {
                Log.i(TAG, "onError: " + error);
                stopListening();

                switch (error) {
                    case SpeechRecognizer.ERROR_CLIENT:
                        stopListening();
                        startListening();
                        break;

                    case SpeechRecognizer.ERROR_AUDIO:
                        showOutputText(RESPONSE_ERROR);
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        showOutputText(RESPONSE_TIMEOUT);
                        stopListening();
                        startListening();
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    case SpeechRecognizer.ERROR_NETWORK:
                        showOutputText(RESPONSE_CHECK_NETWORK);
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        showOutputText(RESPONSE_GRANT_PERMISSIONS);
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        showOutputText(RESPONSE_UNRECOGNIZED);
                        stopListening();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startListening();
                            }
                        }, 1000);
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        showOutputText(RESPONSE_BUSY);
                        stopListening();
                        animateWave(0.2f);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startListening();
                            }
                        }, 2000);
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        showOutputText(RESPONSE_SERVER_ERROR);
                        break;

                }
            }

            @Override
            public void onResults(Bundle results) {
                if (results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)[0] > 0.5)
                    state.handleUserInput(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));

            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.i(TAG, "onPartialResults: ");
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        };

        speechRecognizer.setRecognitionListener(recognitionListener);
        startListening();

    }


    public void setAnimation(boolean stateConstraint, int value) {
        if (currentStatus != stateConstraint) {
            float start = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            float top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72, getResources().getDisplayMetrics());
            float bottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 130, getResources().getDisplayMetrics());
            int assistantOutputLocation[] = {0, 0};
            int wavePos[] = {0, 0};
            assistantOutput.getLocationOnScreen(assistantOutputLocation);
            assistantOutput.animate().translationX((start - assistantOutputLocation[0])).translationY(top - assistantOutputLocation[1]).setDuration(200);
            top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 102, getResources().getDisplayMetrics());
            audioResult.getLocationOnScreen(assistantOutputLocation);
            audioResult.animate().translationX((start - assistantOutputLocation[0])).translationY(top - assistantOutputLocation[1]).setDuration(200);
            suggestions.getLocationOnScreen(assistantOutputLocation);
            waveInverted.getLocationOnScreen(wavePos);
            suggestions.animate().translationX((start - assistantOutputLocation[0])).translationY(wavePos[1] - assistantOutputLocation[1] - bottom);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    constraintLayout.setVisibility(View.VISIBLE);
                }
            }, 250);
            currentStatus = stateConstraint;
        }
        switch (value) {
            case 0:
                if (view != null) {
                    view.setVisibility(View.GONE);
                    constraintLayout.removeView(view);
                    Log.i(TAG, "setAnimation: ");
                }
                break;
            case 1:
                view = LayoutInflater.from(constraintLayout.getContext()).inflate(R.layout.fragment_voice_recycler, constraintLayout, false);
                break;
            case 2:
                view = LayoutInflater.from(constraintLayout.getContext()).inflate(R.layout.fragment_voice_basic_details, constraintLayout, false);
                name = view.findViewById(R.id.full_name_edit);
                age = view.findViewById(R.id.age_edit);
                gender = view.findViewById(R.id.gender_edit);
                address = view.findViewById(R.id.address_edit);
                break;
            case 3:
//                view = LayoutInflater.from(constraintLayout.getContext()).inflate(R.layout.fragment_voice_symptoms,constraintLayout,false);
                break;
            case 4:
                view = LayoutInflater.from(constraintLayout.getContext()).inflate(R.layout.prescription_details, constraintLayout, false);
                break;
        }
        if (view != null)
            constraintLayout.addView(view);


    }


    @Override
    protected void onDestroy() {
        speechRecognizer.destroy();
        super.onDestroy();
    }

    void animateWave(float amplitude) {
        wave.animate().scaleY(-amplitude).setDuration(50);
        waveInverted.animate().scaleY(amplitude).setDuration(50);
    }

    void animateWave(float amplitude, int duration) {
        wave.animate().scaleY(-amplitude).setDuration(50);
        waveInverted.animate().scaleY(amplitude).setDuration(duration);
    }

    void showOutputText(String text) {
        assistantOutput.animateText(text);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && tts != null) {
//            tts.setLanguage(Locale.ENGLISH);
//            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//        }
    }

    public void startListening() {
        speechRecognizer.startListening(recognizerIntent);
    }

    public void stopListening() {
        speechRecognizer.stopListening();
        speechRecognizer.destroy();
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(recognitionListener);
        animateWave(0.05f, 600);
    }
}
