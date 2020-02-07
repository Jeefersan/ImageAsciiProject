package com.jeefersan.imageasciiproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Random;

/**
 * Created by JeeferSan on 31-1-20.
 */

public class Loader extends ViewModel {
    private String imageUrl = Conf.IMAGE_URL;

    private float mFontSize = Conf.FONT_SIZE;

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Bitmap> inputLiveData = new MutableLiveData<>();
    MutableLiveData<Bitmap> outputLiveData = new MutableLiveData<>();

    private Context mContext;

    private String charset = "`^\",:;Il!i~+_a-?qQrRtYhtTyh]SvV[}dsf{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";


    public Loader(Context c) {
        this.mContext = c;
    }

    public void fetchBitmap() {
        loading.setValue(true);
        Glide.with(mContext.getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        inputLiveData.setValue(resource);
                        bitmapToString(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }


    private void bitmapToString(Bitmap bitmap) {
        final Bitmap bit = bitmap;
        new Thread(() -> {
            int w = bit.getWidth();
            int h = bit.getHeight();
            int x = (int) (mFontSize * 0.7);

            Bitmap generatedBitmap = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), Bitmap.Config.ARGB_8888);
            Pixel[][] pixels;
            Canvas canvas = new Canvas(generatedBitmap);
            canvas.drawARGB(255, 0, 0, 0);

            Paint paint = new Paint();
            paint.setTextSize(mFontSize);
            paint.setStyle(Paint.Style.FILL);
            paint.setTypeface(Typeface.create("Consolas", Typeface.NORMAL));
            paint.setAntiAlias(true);


            pixels = new Pixel[h / x][];

            for (int i = 0; i < pixels.length * x; i++) {
                pixels[i / x] = new Pixel[w / x];
                for (int j = 0; j < pixels[0].length * x; j++) {
                    pixels[i / x][j / x] = getPixelColor(j, i, bit);
                    j = j + x - 1;
                }
                i = i + x - 1;
            }

            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[0].length; j++) {
                    String randy = getRandomChar();

                    paint.setARGB(255,
                            pixels[i][j].getPixel()[0],
                            pixels[i][j].getPixel()[1],
                            pixels[i][j].getPixel()[2]
                    );
                    canvas.drawText(randy, j * x, i * x, paint);
                }
            }

            final Bitmap result = generatedBitmap;

            mUiHandler.post(() -> {
                loading.setValue(false);
                outputLiveData.setValue(result);
            });
        }).start();
    }


    private Pixel getPixelColor(int x, int y, Bitmap bitmap) {

        int pix = bitmap.getPixel(x, y);
        int r = Color.red(pix);
        int g = Color.green(pix);
        int b = Color.blue(pix);

        return new Pixel(r, g, b);

    }

    private String getRandomChar() {
        return charset.charAt(new Random().nextInt(charset.length() - 1)) + "";
    }


}