package com.example.codemon.countdown;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    Button start,refresh;
    TextView timer;
    EditText minutes,seconds;
    int minute,second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        refresh = findViewById(R.id.refresh);
        timer = findViewById(R.id.timer);
        minutes = findViewById(R.id.minutes);
        seconds = findViewById(R.id.seconds);

        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    minute = Integer.parseInt(String.valueOf(minutes.getText()));
                    second = Integer.parseInt(String.valueOf(seconds.getText()));
                    if(second > 59 || minute > 59){
                        Toast.makeText(MainActivity.this, "Invalid Time Input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Timer timer = new Timer();
                    timer.execute(minute,second);
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Please Enter Time", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static void wait1sec(){
        long currentTime = System.currentTimeMillis();
        while(System.currentTimeMillis() < currentTime + 1000);
    }

    class Timer extends AsyncTask<Integer, Integer, Void> {
        boolean running;
        @Override
        protected Void doInBackground(Integer... integers) {
            running = true;
          for(int i=integers[0];i>=0;i--){
              for(int j=integers[1];j>=0;j--){
                  publishProgress(i,j);
                  wait1sec();
                  if(!running) {
                      i = 0;
                      j = 0;
                      break;
                  }
                  running = true;
              }
              if(running)
              integers[1] = 59;
          }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            String mins = "";
            if(values[0]/10 == 0){
                mins = "0"+values[0];
            }
            else
            {
                mins = ""+values[0];
            }
            String secs = "";
            if(values[1]/10 == 0){
                secs = "0"+values[1];
            }
            else
            {
                secs = ""+values[1];
            }
            timer.setText(mins+":"+secs);
            if(values[0] == 0){
                if(values[1] == 0){
                    timer.setText("Times Up!!!");
                }
            }

            refresh.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    timer.setText("00:00");
                    minutes.setText("");
                    seconds.setText("");
                    running = false;
                }
            });
        }
    }
}
