package com.example.sushrut.sscp;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private static int SPL = 4000;
    private TextView tv1,tv2;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        iv = (ImageView) findViewById(R.id.iv);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.myanim);
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        iv.startAnimation(scale);
        tv1.startAnimation(myanim);
        tv2.startAnimation(myanim);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable()
        {
            public void run() {
                Intent i = new Intent(SplashActivity.this, password.class);
                startActivity(i);
                finish();
            }
        },SPL);
    }
}

