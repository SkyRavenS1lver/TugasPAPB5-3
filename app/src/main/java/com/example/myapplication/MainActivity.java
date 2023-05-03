package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Canvas canvas;
    private Paint paint = new Paint();
    private Paint paintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap bitmap;
    private ImageView imageView;
    //yang akan digambar
    private Rect rect = new Rect();
    //untuk batasan ukuran text
    private Rect bounds = new Rect();
    //menggambarnya sampai kapan
    private static final int OFFSET = 120;
    private int offset = OFFSET;
    private static final int MULTIPLIER = 100;
    private int colorBackground;
    private int colorReactangle;
    private int colorCircle;
    private int colorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        colorReactangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        colorCircle = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        colorText = ResourcesCompat.getColor(getResources(), R.color.black, null);
        paint.setColor(colorBackground);
        paintText.setColor(colorText);
        paintText.setTextSize(70);
        imageView =findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawSomething(view);
            }
        });

    }

    private void drawSomething(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        int halfWidth = width/2;
        int halfHeight = height/2;
        if (offset == OFFSET){
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            imageView.setImageBitmap(bitmap);
            canvas = new Canvas(bitmap);
            canvas.drawColor(colorBackground);
        }
    }
}