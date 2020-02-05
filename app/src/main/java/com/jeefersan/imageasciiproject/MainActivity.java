package com.jeefersan.imageasciiproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    private Loader loader;
    private ImageView imageView;
    private SubsamplingScaleImageView result;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imageView = findViewById(R.id.imageView);
        this.button = findViewById(R.id.button);
        this.result = findViewById(R.id.imageView2);
        loader = new Loader(this);
        button.setOnClickListener(v -> loadData());
        observeViewModel();
    }

    public void loadData() {

        loader.fetchBitmap();

    }


    public void observeViewModel() {
        loader.bitmapMutableLiveData.observe(this, bitmap -> imageView.setImageBitmap(bitmap));
        loader.resultMutableLiveData.observe(this, bitmap -> result.setImage(ImageSource.bitmap(bitmap)));

    }


}
