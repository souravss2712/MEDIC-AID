package in.codepredators.vedanta.voice.states;

import android.os.Handler;
import android.view.View;

import com.hanks.htextview.fade.FadeTextView;

import java.util.ArrayList;

import in.codepredators.vedanta.Constants;
import in.codepredators.vedanta.voice.VoiceActivity;

public class NewPrescriptionSymptoms implements State, Constants {
    private VoiceActivity voiceActivity;
    View focus;

    public NewPrescriptionSymptoms(VoiceActivity voiceActivity) {
        this.voiceActivity = voiceActivity;
        showSuggestions(voiceActivity.suggestions);
    }

    @Override
    public void handleUserInput(ArrayList<String> input) {
        voiceActivity.audioResult.setText(input.get(0));
        switch (input.get(0).toLowerCase()) {
            case COMMAND_STOP_LISTENING:
                voiceActivity.stopListening();
                voiceActivity.assistantOutput.animateText("Thanks for using!");
                voiceActivity.mainSwitch.setChecked(false);
                break;
            case COMMAND_SYMPTOMS:
                // TODO focus edittext here
                voiceActivity.assistantOutput.animateText("Say symptoms");
                restartListening();
                break;

            case COMMAND_CLEAR:
                voiceActivity.assistantOutput.animateText("Cleared data");
                restartListening();
                break;

            case COMMAND_NEXT:
                voiceActivity.assistantOutput.animateText("");
                restartListening();
                break;

            case COMMAND_CREATE_PRESCRIPTION:
                voiceActivity.assistantOutput.animateText("Enter prescription details");
                restartListening();
                break;

            default:
                restartListening();
                break;
        }
    }

    void restartListening(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                voiceActivity.startListening();
            }
        }, 2000);
    }

    @Override
    public void showSuggestions(FadeTextView suggestions) {
        suggestions.animateText("Try saying\nSymptoms          Next\nClear          Back");

    }
}
