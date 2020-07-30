package com.pramu.idare.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.pramu.idare.MainActivity;
import com.pramu.idare.R;

public class IntroActivity extends AppIntro{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //mengecek apakah sudah melalui intro
//        if (restorePrefData()) {
//
//            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class );
//            startActivity(mainActivity);
//            finish();
//        }
        addSlide(DataIntroActivity.newInstance(R.layout.w1));
        addSlide(DataIntroActivity.newInstance(R.layout.w2));
        addSlide(DataIntroActivity.newInstance(R.layout.w3));
        addSlide(DataIntroActivity.newInstance(R.layout.w4));
        showSkipButton(true);

        // make the activity on full screen

    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        Intent intent=new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
//        savePrefsData();
        finish();
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        Intent intent=new Intent(IntroActivity.this,MainActivity.class);
        startActivity(intent);
//        savePrefsData();
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when slide is changed

    }
//    private boolean restorePrefData() {
//
//
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
//        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
//        return  isIntroActivityOpnendBefore;
//
//
//
//    }
//
//    private void savePrefsData() {
//
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean("isIntroOpnend",true);
//        editor.commit();
//
//
//    }
}
