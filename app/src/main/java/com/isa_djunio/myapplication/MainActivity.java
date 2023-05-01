package com.isa_djunio.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
        startCicle();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    running = true;
                    startButton.setText("Stop");

                    if (timer == null) {
                        timer = new Timer();
                        timerTask = new STimerTask(MainActivity.this);
                        timer.scheduleAtFixedRate(timerTask, 0, 1390);
                    }

                    playRandomMusic();

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
        changeSphereColor();
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(time);
    }

    private void playRandomMusic() {
        int[] sounds = new int[]{R.raw.meditation, R.raw.meditation2, R.raw.meditation3};
        int index = random.nextInt(sounds.length);
        mediaPlayer = MediaPlayer.create(this, sounds[index]);
        if (mediaPlayer != null)
            mediaPlayer.start();
    }

    private void stopMusic()
    {
        if (mediaPlayer != null)
        {
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = null;
        }
    }

    void startCicle()
    {
        int index = random.nextInt(colors.length);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        drawable.setGradientCenter(0.5f, 0.5f); // centraliza o gradiente na imagem
        drawable.setGradientRadius(900f); // define o raio do gradiente
        drawable.setColors(new int[]{colors[index], Color.WHITE}); // define as cores do gradiente

        sphereImage.setBackground(drawable);
    }
    private void changeSphereColor() {
                startCicle();
    }
}
