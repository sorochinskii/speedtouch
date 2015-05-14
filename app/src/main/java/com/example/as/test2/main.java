package com.example.as.test2;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;

import java.util.concurrent.CountDownLatch;

/**
 * Created by as on 08.05.15.
 */
public class main extends Activity implements View.OnClickListener {

    private static final int MILLIS_PER_SECOND = 1000;
    private static final int SECONDS_TO_COUNTDOWN = 30;
    private static final int LOSHIDZE = 3;
    private static final int LOSHPEK = 5;
    private static final int KRASAVCHEK = 8;
    private static final int INIT_R = 141;
    private static final int INIT_G = 255;
    private static final int INIT_B = 243;
    private static final int END_R = 9;
    private static final int END_G = 6;
    private static final int END_B = 15;

    private CountDownTimer timer;

    public int counter = 0;
    public int progress = 0;
    public int coeffR = (int) ((float) ((INIT_R - END_R) / SECONDS_TO_COUNTDOWN) + 0.5) ;
    public int coeffG = (int) ((float) ((INIT_G - END_G) / SECONDS_TO_COUNTDOWN) + 0.5) ;
    public int coeffB = (int) ((float) ((INIT_B - END_B) / SECONDS_TO_COUNTDOWN) + 0.5) ;
    public int tempColorR = INIT_R;
    public int tempColorG = INIT_G;
    public int tempColorB = INIT_B;

    View mainLayout;
    ProgressBar bar;
    TextView tvView;
    TextView countdownTvView;
    TextView tvViewAchiev;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        mainLayout = (View) findViewById(R.id.mainLayout);
        tvView = (TextView) findViewById(R.id.textView);
        countdownTvView = (TextView) findViewById(R.id.textView2);
        tvViewAchiev = (TextView) findViewById(R.id.achiev);
        bar = (ProgressBar) findViewById(R.id.progressBar);

        mainLayout.setBackgroundColor(Color.rgb(INIT_R, INIT_G, INIT_B));
        bar.setMax(100);
        bar.setProgress(0);

        try {
            showTimer(SECONDS_TO_COUNTDOWN * MILLIS_PER_SECOND);
        } catch (NumberFormatException e) {
            // method ignores invalid (non-integer) input and waits
            // for something it can use
        }

        mainLayout.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainLayout:
                counter += 1;
                tvView.setText(String.valueOf(counter));
        }
    }

    private void showTimer(final int countdownMillis) {
        tvView = (TextView) findViewById(R.id.textView);
        if(timer != null) { timer.cancel(); }
        timer = new CountDownTimer(countdownMillis, MILLIS_PER_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTvView.setText(String.valueOf(millisUntilFinished / MILLIS_PER_SECOND));
                tempColorR = tempColorR - coeffR;
                tempColorG = tempColorG - coeffG;
                tempColorB = tempColorB - coeffB;
                bar.setProgress((int) (100 - (100*(SECONDS_TO_COUNTDOWN*MILLIS_PER_SECOND -
                        millisUntilFinished)/(SECONDS_TO_COUNTDOWN*MILLIS_PER_SECOND))));
                mainLayout.setBackgroundColor(Color.rgb(tempColorR, tempColorG, tempColorB));
                if (Integer.parseInt(countdownTvView.getText().toString()) == 1) {
                    mainLayout.setBackgroundColor(Color.rgb(END_R, END_G, END_B));
                }

            }
            @Override
            public void onFinish() {
                tvViewAchiev.setText("");
                if (counter<=LOSHIDZE) tvViewAchiev.setText(R.string.loshidze);
                if ((counter>LOSHIDZE)&&(counter<=LOSHPEK)) tvViewAchiev.setText(R.string.loshpek);
                if ((counter>LOSHPEK)&&(counter<=KRASAVCHEK)) tvViewAchiev.setText(R.string.krasavchek);
                if ((counter>KRASAVCHEK)) tvViewAchiev.setText(R.string.boh);
                counter = 0;
                tvView.setText(String.valueOf(counter));
                mainLayout.setBackgroundColor(Color.rgb(INIT_R, INIT_G, INIT_B));
                ColorDrawable colorMainLayout = (ColorDrawable) mainLayout.getBackground();
                int colorID = colorMainLayout.getColor();
                tempColorR = (colorID >> 16) & 0xFF;
                tempColorG = (colorID >> 8) & 0xFF;
                tempColorB = (colorID >> 0) & 0xFF;

                try {
                    showTimer(SECONDS_TO_COUNTDOWN * MILLIS_PER_SECOND);
                } catch (NumberFormatException e) {
                    // method ignores invalid (non-integer) input and waits
                    // for something it can use
                }
            }
        }.start();
    }

    public static void  layoutColorInit(View layout) {
        layout.setBackgroundColor(Color.rgb(INIT_R, INIT_G, INIT_B));
    }

}
