package com.jeefersan.imageasciiproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;


public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";

    private Loader mLoader;
    private PhotoView mOutput;
    private PhotoView mInput;
    private Button mStartBtn;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mStartBtn = findViewById(R.id.start);
        this.mInput = findViewById(R.id.input);
        this.mOutput = findViewById(R.id.output);
        this.progressBar = findViewById(R.id.progress_bar);
        mLoader = new Loader(this);
        mStartBtn.setOnClickListener(v -> loadData());
        observeViewModel();
    }

    private void loadData() {
        mLoader.fetchBitmap();

    }


    private void observeViewModel() {
        mLoader.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    mOutput.setVisibility(View.GONE);
                }
            }
        });
        mLoader.inputLiveData.observe(this, input -> {
            if (input != null) {
                mInput.setImageBitmap(input);
            }
        });
        mLoader.outputLiveData.observe(this, output -> {
            if (output != null) {
                mOutput.setVisibility(View.VISIBLE);
                mOutput.setImageBitmap(output);
            }
        });


    }


}
