package in.codepredators.vedanta.voice.states;

import com.hanks.htextview.fade.FadeTextView;

import java.util.ArrayList;

public interface State {

    void handleUserInput(ArrayList<String> input);

    void showSuggestions(FadeTextView textView);

}
