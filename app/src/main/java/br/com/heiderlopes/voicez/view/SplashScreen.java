package br.com.heiderlopes.voicez.view;

import android.os.Bundle;
import br.com.heiderlopes.voicez.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, NameList.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, SPLASH_TIME_OUT);
    }
}