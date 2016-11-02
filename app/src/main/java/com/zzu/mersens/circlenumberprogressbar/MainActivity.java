package com.zzu.mersens.circlenumberprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.mersens.view.CircleNumberProgressBar;
import com.mersens.view.NumberProgressBar;

public class MainActivity extends AppCompatActivity {
    private CircleNumberProgressBar progressbar;
    private NumberProgressBar numberProgressBar;
    private MyRunnable myRunnable=new MyRunnable();
    private static final int DELAYED_TIME=100;
    private  int progress=0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        progressbar = (CircleNumberProgressBar) findViewById(R.id.progressbar);
        numberProgressBar=(NumberProgressBar)findViewById(R.id.numberProgressBar);
        handler.postDelayed(myRunnable,DELAYED_TIME);
    }

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            progress++;
            if(progress<=100){
                progressbar.setProgress(progress);
                numberProgressBar.setProgress(progress);
                handler.postDelayed(myRunnable,DELAYED_TIME);
            }else{
                handler.removeCallbacksAndMessages(myRunnable);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(myRunnable);
    }
}
