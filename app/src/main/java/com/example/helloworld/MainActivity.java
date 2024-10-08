package com.example.helloworld;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    private Button changeSpriteButton;
    private Vibrator vibrator;
    long[] timings = {0, 100, 330, 100};  // Vibrer 200ms, pause 100ms, vibrer 300ms
    int[] amplitudes = {0, 100, 0, 255};  // Intensité correspondante (0 pour pas de vibration, 100 et 255 pour vibrer)

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // Get instance of Vibrator from current Context
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Créer une instance de GameView et l'ajouter au FrameLayout
        FrameLayout gameFrame = findViewById(R.id.gameFrame);
        gameView = new GameView(this);
        gameFrame.addView(gameView);

        // Associer le bouton
        changeSpriteButton = findViewById(R.id.changeSpriteButton);
        //VibrationEffect vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, -1);

        // Définir l'écouteur d'événements pour changer le spritesheet
        changeSpriteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Changer le spritesheet quand le bouton est pressé
                    gameView.changeSpriteSheet(R.drawable.attack);  // Remplacer par ton nouveau spritesheet
                    // Vibrate for 300 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(timings,0);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Restaurer le spritesheet original quand le bouton est relâché
                    gameView.changeSpriteSheet(R.drawable.idle);  // Remplacer par le spritesheet original
                    vibrator.cancel();
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
