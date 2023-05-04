package com.isa_djunio.cronometro_maluco.ui.gallery;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.isa_djunio.cronometro_maluco.R;
import com.isa_djunio.cronometro_maluco.databinding.FragmentGalleryBinding;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GalleryFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private FragmentGalleryBinding binding;
    private TextView timerText;
    private Button startButton;
    private Button resetButton;
    private int seconds;
    private int minutes;
    private int hours;
    private boolean running;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler;
    private Random random;
    private ImageView sphereImage;
    private int[] colors;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        timerText = root.findViewById(R.id.timer);
        sphereImage = root.findViewById(R.id.sphere);
        startButton = root.findViewById(R.id.startButton);
        resetButton = root.findViewById(R.id.resetButton);

        handler = new Handler();
        random = new Random();
        colors = new int[] {getResources().getColor(R.color.red),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.yellow)};
        startCicle();

        startButton.setOnClickListener(new View.OnClickListener() {
            private long elapsedTime = 0;

            @Override
            public void onClick(View v) {
                if (!running) {
                    running = true;
                    startButton.setText("Stop");
                    playRandomMusic();
                    if (timer == null) {
                        timer = new Timer();
                        timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        elapsedTime += 1000; // increment elapsed time by 1 second
                                        updateTimerText();
                                        changeSphereColor();
                                    }
                                });
                            }
                        };
                        timer.scheduleAtFixedRate(timerTask, 0, 1000);
                    }

                } else {
                    running = false;
                    startButton.setText("Start");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    stopMusic();
                }
            }
            private void playRandomMusic() {
                int[] sounds = new int[]{R.raw.meditation, R.raw.meditation2, R.raw.meditation3};
                int index = random.nextInt(sounds.length);
                mediaPlayer = MediaPlayer.create(root.getContext(),sounds[index]);
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
            private void updateTimerText() {
                long hours = elapsedTime / 3600000;
                long minutes = (elapsedTime / 60000) % 60;
                long seconds = (elapsedTime / 1000) % 60;
                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timerText.setText(timeString);
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
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
            }
        });

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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