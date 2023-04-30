package com.isa_djunio.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView timerText;
    private ImageView sphereImage;
    private Button startButton;
    private Button resetButton;
    private int seconds;
    private int minutes;
    private int hours;
    private boolean running;
    private Timer timer;
    private STimerTask timerTask;
    private Handler handler;
    private Random random;
    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer);
        sphereImage = findViewById(R.id.sphere);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);

        handler = new Handler();
        random = new Random();
        colors = new int[] {getResources().getColor(R.color.red),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.yellow)};

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    running = true;
                    startButton.setText("Stop");

                    if (timer == null) {
                        timer = new Timer();
                        timerTask = new STimerTask(MainActivity.this);
                        timer.scheduleAtFixedRate(timerTask, 0, 1000);
                    }

                    playRandomMusic();
                    changeSphereColor();
                } else {
                    running = false;
                    startButton.setText("Start");
                    stopMusic();
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                startButton.setText("Start");
                seconds = 0;
                minutes = 0;
                hours = 0;
                timerText.setText("00:00:00");
                stopMusic();
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }

                sphereImage.setColorFilter(getResources().getColor(R.color.gray));
            }
        });
    }

    public void updateTimerText() {
        seconds++;

        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }

        if (minutes == 60) {
            minutes = 0;
            hours++;
        }

        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(time);
    }

    private void playRandomMusic() {
        int[] sounds = new int[] {R.raw.meditation, R.raw.meditation2, R.raw.meditation3};
        int index = random.nextInt(sounds.length);
        mediaPlayer = MediaPlayer.create(this, sounds[index]);
        mediaPlayer.start();
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void changeSphereColor() {
        int index = random.nextInt(colors.length);
        sphereImage.setColorFilter(colors[index]);
    }
}
