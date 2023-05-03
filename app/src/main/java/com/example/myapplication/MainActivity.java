package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
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
            canvas.drawText(getString(R.string.keep_tapping), 100, 100, paintText);
            offset+= OFFSET;
        }
        else{
            if (offset < halfWidth && offset < halfHeight){
                paint.setColor(colorReactangle - MULTIPLIER*offset);
                rect.set(offset, offset, width-offset, height-offset);
                canvas.drawRect(rect,paint);
                offset+= OFFSET;
            }
            else {
            paint.setColor(colorCircle - MULTIPLIER * offset);
            canvas.drawCircle(halfWidth, halfHeight, halfHeight/4, paint);
            String text = getString(R.string.done);
            paintText.getTextBounds(text, 0, text.length(), bounds);
            int x = halfWidth - bounds.centerX();
            int y = halfHeight - bounds.centerY();
            canvas.drawText(text, x, y, paintText);
            offset += OFFSET;
            paint.setColor(colorBackground- MULTIPLIER*offset);
            Point a = new Point(halfWidth-50, halfHeight-50);
            Point b = new Point(halfWidth+50, halfHeight-50);
            Point c = new Point(halfWidth, halfHeight+250);

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.lineTo(a.x, a.y);
            path.lineTo(b.x, b.y);
            path.lineTo(c.x, c.y);
            path.lineTo(a.x, a.y);
            path.close();
            canvas.drawPath(path, paint);
            offset += OFFSET;
        }}
        view.invalidate();
    }
}