package com.melodyxxx.blurdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.iv_normal)
    ImageView mNormalImage;

    @BindView(R.id.iv_blur)
    ImageView mBlurImage;

    @BindView(R.id.et_scale)
    EditText mScaleEdit;

    @BindView(R.id.et_radius)
    EditText mRadiusEdit;

    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBlurImage.post(new Runnable() {
            @Override
            public void run() {
                blur(1, 25);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e(TAG, "onProgressChanged() called with: progress = [" + progress + "]");
                mNormalImage.setAlpha(1.0f - progress / 100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void readyBlur(View view) {
        try {
            float scaleFactor = Float.parseFloat(mScaleEdit.getText().toString());
            float radius = Float.parseFloat(mRadiusEdit.getText().toString());
            blur(scaleFactor, radius);
            mSeekBar.setProgress(0);
            Toast.makeText(MainActivity.this, "模糊成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "缩放参数 > 0.0f\n0.0f < 模糊半径 <= 25.0f", Toast.LENGTH_SHORT).show();
        }
    }

    public void blur(float scaleFactor, float radius) {
        Blur blur = new Blur(MainActivity.this, mNormalImage, mBlurImage);
        blur.blur(scaleFactor, radius);
    }

}
