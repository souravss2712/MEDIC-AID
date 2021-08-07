package in.codepredators.vedanta.new_prescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import in.codepredators.vedanta.R;
import in.codepredators.vedanta.home.fragments.AdviceFragment;
import in.codepredators.vedanta.home.fragments.BasicDetailsFragment;
import in.codepredators.vedanta.home.fragments.DiagnosisFragment;
import in.codepredators.vedanta.home.fragments.PrescriptionSaved;
import in.codepredators.vedanta.home.fragments.PrescriptionSigned;
import in.codepredators.vedanta.home.fragments.SymptomsFragment;

public class AddPrescriptionActivity extends AppCompatActivity {

    ImageView leftline,centerline,rightline;
    TextView bdTextView,sTextView,dTextView,fTextView,send,close;
    ProgressBar leftProgress,centerProgress,rightProgress;
    BasicDetailsFragment basicDetailsFragment=new BasicDetailsFragment();
    DiagnosisFragment diagnosisFragment=new DiagnosisFragment();
    PrescriptionSaved prescriptionSaved=new PrescriptionSaved();
    AdviceFragment adviceFragment=new AdviceFragment();
    SignPrescriptionActivity signPrescriptionActivity=new SignPrescriptionActivity();
    String pres_id="";
    PrescriptionSigned prescriptionSigned=new PrescriptionSigned();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SymptomsFragment symptomsFragment=new SymptomsFragment();
    int a[],b[];
    int degree;
    int progressId[] = {
            R.id.progressleft,
            R.id.progresscenter,
            R.id.progressright
    };
    int numberId[] = {
            R.id.basicdetailnumber,
            R.id.symptomsnumber,
            R.id.diagonisisnumber,
            R.id.forthnumber
    };
    int textId[] = {
            R.id.basicdetailtextview,
            R.id.symptomstextview,
            R.id.diagonisistextview,
            R.id.forthtextview
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        a = new int[2];
        b = new int[2];
        leftline = findViewById(R.id.dashlineleft);
        centerline = findViewById(R.id.dashlinecenter);
        rightline = findViewById(R.id.dashlineright);
        send=findViewById(R.id.send);
        close=findViewById(R.id.close);
        bdTextView = findViewById(R.id.basicdetailnumber);
        sTextView = findViewById(R.id.symptomsnumber);
        dTextView = findViewById(R.id.diagonisisnumber);
        fTextView = findViewById(R.id.forthnumber);
        leftProgress = findViewById(R.id.progressleft);
        rightProgress = findViewById(R.id.progressright);
        centerProgress = findViewById(R.id.progresscenter);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bdTextView.getLocationOnScreen(a);
                sTextView.getLocationOnScreen(b);
                Log.i("value", a[0] + " " + a[1] + " " + b[0] + " " + b[1]);
                degree = (int)  (Math.toDegrees(Math.atan((float)(a[1]-b[1])/(b[0]-a[0]))));
                Log.i("degree", String.valueOf(degree));
                sharedPreferences=getSharedPreferences("VEDANTA",MODE_PRIVATE);
                editor=sharedPreferences.edit();
                pres_id=System.currentTimeMillis()+"";
                editor.putString("PRES_ID",pres_id);
                editor.commit();
                leftline.animate().rotation(-1 * degree);
                centerline.animate().rotation(degree);
                rightline.animate().rotation(-1 * degree);
                leftProgress.animate().rotation(-1 * degree);
                centerProgress.animate().rotation(degree);
                rightProgress.animate().rotation(-1 * degree);
                leftProgress.setProgress(50);
                setPrescriptionStatus(0);
                setFragment(0);
            }
        }, 300);
        final int[] i = {0};
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i[0]++;
                setPrescriptionStatus(i[0]);
            }
        });
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                i[0]--;
                setPrescriptionStatus(i[0]);
            }
        });
    }
    //position starts from zero to 3
    public void setPrescriptionStatus(int position){
        findViewById(textId[position]).setAlpha(1);
        TextView tvActive = findViewById(numberId[position]);
        tvActive.setTextColor(Color.parseColor("#ffffff"));
        tvActive.setBackground(getResources().getDrawable(R.drawable.blue_bg));
        int i = position-1;
        for(;i>=0;i--){
            TextView tvdone = findViewById(numberId[i]);
            tvdone.setTextColor(Color.parseColor("#ffffff"));
            tvdone.setBackground(getResources().getDrawable(R.drawable.blue_bg));
            findViewById(textId[i]).setAlpha(0.7f);
            ProgressBar pb = findViewById(progressId[i]);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(100);
        }
        i=position + 1;
        if(position<3)
            findViewById(progressId[position]).setVisibility(View.GONE);
        for(;i<=3;i++){
            findViewById(textId[i]).setAlpha(0);
            TextView tvremain = findViewById(numberId[i]);
            tvremain.setTextColor(Color.parseColor("#1B7AE3"));
            tvremain.setBackground(getResources().getDrawable(R.drawable.white_bg));
            if(i!=3){
                findViewById(progressId[i]).setVisibility(View.GONE);
            }
        }
    }
    public void setFragment(final int position){
        if(position==0){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,basicDetailsFragment).addToBackStack(null).commit();
            send.setText("NEXT");
            send.setText("CLOSE");
        }
        if(position==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,symptomsFragment).addToBackStack(null).commit();
            send.setText("NEXT");
            send.setText("CLOSE");
        }
        if(position==2){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,diagnosisFragment).addToBackStack(null).commit();
            send.setText("NEXT");
            send.setText("CLOSE");
        }
        if(position==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,adviceFragment).addToBackStack(null).commit();
            send.setText("NEXT");
            send.setText("CLOSE");
        }
        if(position==4){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,prescriptionSigned).addToBackStack(null).commit();
            send.setText("");
            send.setText("CLOSE");
        }
        if(position==5){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,prescriptionSaved).addToBackStack(null).commit();
            send.setText("");
            send.setText("CLOSE");
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0){
                    setFragment(1);
                    editor.putString("NAME",basicDetailsFragment.name.getText().toString());
                    editor.putString("ADDRESS",basicDetailsFragment.address.getText().toString());
                    editor.putString("AGE",basicDetailsFragment.age.getText().toString());
                    editor.commit();
                }
                if(position==1){
                    setFragment(2);
                    editor.putString("SYMPTOMS",symptomsFragment.symptoms_text.getText().toString());
                    editor.commit();
                }
                if(position==2){
                    editor.putString("DIAGNOSIS",diagnosisFragment.diagnosis_text.getText().toString());
                    editor.commit();
                    setFragment(3);
                }
                if(position==3){
                    editor.putString("ADVICE",adviceFragment.advice_text.getText().toString());
                    editor.putString("MEDICATION",adviceFragment.prescription_text.getText().toString());
                    editor.commit();
                    setFragment(4);
                }
                if(position==4){
                    editor.putString("SIGN",signPrescriptionActivity.signString);
                    editor.commit();
                    setFragment(5);
                }
                if(position==5){
                    setFragment(5);
                }

            }
        });
    }

}