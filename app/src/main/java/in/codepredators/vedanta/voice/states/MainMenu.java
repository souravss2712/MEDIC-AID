package in.codepredators.vedanta.voice.states;

import android.os.Handler;

import com.hanks.htextview.fade.FadeTextView;

import java.util.ArrayList;

import in.codepredators.vedanta.Constants;
import in.codepredators.vedanta.voice.VoiceActivity;

public class MainMenu implements State, Constants {

    private VoiceActivity voiceActivity;

    public MainMenu(VoiceActivity voiceActivity) {
        this.voiceActivity = voiceActivity;
        showSuggestions(voiceActivity.suggestions);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainMenu.this.voiceActivity.setAnimation(false, 0);
            }
        }, 100);
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
            case COMMAND_FIND_A_PATIENT:
                voiceActivity.assistantOutput.animateText("Tell me some detail");
                restartListening();
                break;
            case COMMAND_CREATE_A_NEW_PRESCRIPTION:
            case COMMAND_CREATE_A_PRESCRIPTION:
            case COMMAND_CREATE_NEW_PRESCRIPTION:
            case COMMAND_CREATE_PRESCRIPTION:
                voiceActivity.assistantOutput.animateText("Enter prescription details");
                voiceActivity.state = new NewPrescriptionBasic(voiceActivity);
                restartListening();
                break;
            default:
                restartListening();
                break;
        }
    }

    void restartListening() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                voiceActivity.startListening();
            }
        }, 500);
    }

    @Override
    public void showSuggestions(FadeTextView suggestions) {
        suggestions.animateText("Try saying\nFind a patient\nCreate a new prescription");

    }
}
