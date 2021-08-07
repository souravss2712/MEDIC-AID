package in.codepredators.vedanta.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import in.codepredators.vedanta.R;

public class PrescriptionDetails extends AppCompatActivity {
    
    TextView referText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LightTheme);
        setContentView(R.layout.prescription_details_actions);
    }
    
    // todo add onclick funnction on view records
    
    private void assignVariables() {
        referText = findViewById(R.id.referText);
    }

    private void setup() {
        referText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 moveRecord();
            }
        });
    }


}
