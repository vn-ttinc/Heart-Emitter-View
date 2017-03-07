package com.ttinc.android.apps.heartemiterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ttinc.android.apps.heartemiterview.layout.HeartEmitterView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HeartEmitterView mEmitterView;
    private View mEmitterButton;
    private int[] mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mImages = new int[]{R.raw.heart1, R.raw.heart2, R.raw.heart3, R.raw.heart4, R.raw.heart5};

        this.mEmitterView = (HeartEmitterView) findViewById(R.id.emitter_view);
        this.mEmitterButton = findViewById(R.id.emitter_button);
        this.mEmitterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == this.mEmitterButton) {
            emitButtonClicked(v);
        }
    }

    private void emitButtonClicked(View v) {
        Random r = new Random();
        int idx = r.nextInt(this.mImages.length);
        this.mEmitterView.emitImage(this.mImages[idx]);
    }
}
