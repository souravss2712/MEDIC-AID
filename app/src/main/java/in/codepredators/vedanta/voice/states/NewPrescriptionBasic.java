package in.codepredators.vedanta.voice.states;

import android.os.Handler;
import android.widget.TextView;

import com.hanks.htextview.fade.FadeTextView;

import java.util.ArrayList;

import in.codepredators.vedanta.Constants;
import in.codepredators.vedanta.voice.VoiceActivity;

public class NewPrescriptionBasic implements State, Constants {
    private VoiceActivity voiceActivity;

    public NewPrescriptionBasic(VoiceActivity voiceActivity) {
        this.voiceActivity = voiceActivity;
        showSuggestions(voiceActivity.suggestions);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NewPrescriptionBasic.this.voiceActivity.setAnimation(true, 2);
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
            case COMMAND_GENDER:
                voiceActivity.assistantOutput.animateText("Say gender");
                voiceActivity.gender.requestFocus();
                restartListening();
                break;
            case COMMAND_ADDRESS:
                voiceActivity.assistantOutput.animateText("Say address");
                voiceActivity.address.requestFocus();
                restartListening();
                break;
            case COMMAND_AGE:
                voiceActivity.assistantOutput.animateText("Say age");
                voiceActivity.age.requestFocus();
                restartListening();
                break;
            case COMMAND_NAME:
                voiceActivity.assistantOutput.animateText("Say name");
                voiceActivity.name.requestFocus();
                restartListening();
                break;

            case COMMAND_NEXT:
                voiceActivity.assistantOutput.animateText("Enter Prescription Details");
                voiceActivity.state = new NewPrescriptionSymptoms(voiceActivity);
                restartListening();
                break;
            case COMMAND_BACK:
                voiceActivity.state = new MainMenu(voiceActivity);
                restartListening();
                break;
            case COMMAND_CLEAR:
                ((TextView) voiceActivity.view.findFocus()).setText("");
                restartListening();
                break;
            default:
                if (voiceActivity.view != null && voiceActivity.view.findFocus() != null)
                    ((TextView) voiceActivity.view.findFocus()).setText(((TextView) voiceActivity.view.findFocus()).getText() + input.get(0));
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
        }, 1000);
    }

    @Override
    public void showSuggestions(FadeTextView suggestions) {
        suggestions.animateText("Try saying\nGender          Address\nName     Age     Next");

    }
}
