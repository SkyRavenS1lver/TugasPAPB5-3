package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Canvas canvas;
    private Paint paint = new Paint();
    private Paint paint2 = new Paint();
    private Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private ImageView imageView;
    //yang akan digambar
    private Rect rect = new Rect();
    private Rect bounds = new Rect();
    private static final int SIZE = 100;
    private static int COUNTER = 0;
    private int color;
    private EditText editText;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button confirmButton;
    private int current= 0;
    private String[] clue = new String[]{"Expensive Fish", "100% Haram", "A Transportaion", "Spongebob's Boss",
                                            "Thing to unlock", "A mundane feeling", "No engine transport",
            "Defender of justice", "The door of knowledge", "Something we eat"};
    private String[] words = new String[]{"tuna", "pig", "car", "krab", "key", "sadness", "bike", "policeman", "book", "food"};
    private String soal = "";
    private String hint = "";
    private char[] tempSoal;
    private char[] tempJawaban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color = ResourcesCompat.getColor(getResources(), R.color.black, null);
        paint.setColor(color);
        paint2.setColor(Color.WHITE);
        paintText.setColor(color);
        paintText.setTextSize(70);
        imageView =findViewById(R.id.hangman);
        editText = findViewById(R.id.et);
        textView1 = findViewById(R.id.guess);
        textView2 = findViewById(R.id.guessed);
        textView3 = findViewById(R.id.word);
        confirmButton = findViewById(R.id.confirm_button);

//        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                char cari = charSequence.charAt(0);
////                cari = Character.toLowerCase(cari);
////                if (Arrays.asList(tempSoal).contains(cari)){
////                    tempJawaban[(new String(tempSoal)).indexOf(cari)] = cari;
////                    tempSoal[(new String(tempSoal)).indexOf(cari)] = '*';
////                }
////                else {
////                    draw(imageView);
////                }
////                editText.getText().clear();
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                int len = editable.length();
//                if (len>0 && !Character.isLetter(editable.charAt(len-1))){
//                    editable.delete(len-1, len);
//                    Selection.setSelection(editable, editable.length());
//                    return;
//                }
//                if (len>1){
//                    editable.delete(0, 1);
//                    Selection.setSelection(editable, editable.length());
//                }
//                if (len>0){
//                char cari = Character.toLowerCase(editable.charAt(editable.length()-1));
//                    System.out.println(cari);
//                    boolean contain = false;
//                    for (int i = 0; i<tempSoal.length; i++){
//                        if (tempSoal[i] == cari){
//                            tempSoal[i] = '*';
//                            tempJawaban[i] = cari;
//                            return;
//                        }
//                    }
//                    draw(imageView);
//
////                    System.out.println(Arrays.asList(tempSoal).contains(cari+""));
////                    if (Arrays.asList(tempSoal).contains(cari)){
////                    tempJawaban[(new String(tempSoal)).indexOf(cari)] = cari;
////                    tempSoal[(new String(tempSoal)).indexOf(cari)] = '*';
////                }
////                else {
////                    draw(imageView);
////                }
//                    System.out.println(tempJawaban);
//                    System.out.println(tempSoal);
//                }
//
//
//
//            }
//        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                COUNTER = 0;
                reset();
                draw(imageView);

            }
        });
        textView2.setPaintFlags(textView2.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        start();
    }

    public void draw(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int Appended =halfHeight/5;
        int left = width-(SIZE + SIZE+SIZE/4);
        int right = width-(SIZE + SIZE);
        if (COUNTER == 0) {
            editText.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            imageView.setImageBitmap(bitmap);
            canvas = new Canvas(bitmap);
            String title = getString(R.string.title);
            paintText.getTextBounds(title, 0, title.length(), bounds);
            int x = halfWidth - bounds.centerX();
            canvas.drawText(title, x, SIZE, paintText);
            rect.set(0, height-SIZE/2, width, height);
            canvas.drawRect(rect, paint);
            rect.set(SIZE + SIZE / 2, SIZE + SIZE, SIZE + SIZE, height-SIZE/2);
            canvas.drawRect(rect, paint);
            rect.set(SIZE + SIZE / 2, SIZE + SIZE, width-(SIZE + SIZE/2),SIZE + SIZE+ SIZE / 2);
            canvas.drawRect(rect, paint);
            current = SIZE + SIZE + SIZE / 2;
        } else if (COUNTER ==1) {
            rect.set(left, current, right,current+Appended);
            current+=Appended;
            canvas.drawRect(rect, paint);
        } else if (COUNTER==2) {
            float x = left+SIZE/8;
            float y = current + SIZE/4;
            current+=Appended-SIZE/8;
            canvas.drawCircle(x,y,Appended/2,paint);
        } else if (COUNTER==3) {
            rect.set(left, current, right, current + Appended);
            current += Appended;
            canvas.drawRect(rect, paint);
        } else if (COUNTER == 4) {
            Path path = new Path();
            path.addRect(SIZE+30, SIZE+550, SIZE+50, SIZE+650, Path.Direction.CW);
            canvas.save(); // first save the state of the canvas
            canvas.rotate(-45); // rotate it
            canvas.drawPath(path, paint); // draw on it
            canvas.restore(); // restore previous state (rotate it back)
        } else if (COUNTER == 5) {
            Path path = new Path();
            path.addRect(660, -160, 680, -60, Path.Direction.CW);
            canvas.save(); // first save the state of the canvas
            canvas.rotate(45); // rotate it
            canvas.drawPath(path, paint); // draw on it
            canvas.restore(); // restore previous state (rotate it back)

        } else if (COUNTER == 6) {
            Path path = new Path();
            path.addRect(70, 720, 90, 790, Path.Direction.CW);
            canvas.save(); // first save the state of the canvas
            canvas.rotate(-45); // rotate it
            canvas.drawPath(path, paint); // draw on it
            canvas.restore(); // restore previous state (rotate it back)
        }
        else if (COUNTER == 7) {
            Path path = new Path();
            path.addRect(720, -80, 740, -20, Path.Direction.CW);
            canvas.save(); // first save the state of the canvas
            canvas.rotate(45); // rotate it
            canvas.drawPath(path, paint); // draw on it
            canvas.restore(); // restore previous state (rotate it back)
            String over ="Game Over";
            paintText.getTextBounds(over, 0, over.length(), bounds);
//            int x = halfWidth - bounds.centerX();
            int x2 = SIZE + SIZE+SIZE/2;
            canvas.drawText(over, x2, height-(SIZE+SIZE/2), paintText);
            textView3.setText("Tap Here To Restart!");
            confirmButton.setClickable(false);
            confirmButton.setFocusable(false);
        }
//        }else if (COUNTER == 4) {
//            float x = right;
//            float y = current-Appended/2;
//            canvas.drawCircle(x,y, Appended/3, paint);
//            } else if (COUNTER == 5) {
//            float x = left;
//            float y = current-Appended/2;
//            canvas.drawCircle(x,y, Appended/3, paint);
//            } else if (COUNTER == 6) {
//            float x = right;
//            float y = current;
//            canvas.drawCircle(x,y, Appended/3, paint);
//            }
//            else if (COUNTER == 7) {
//            float x = left;
//            float y = current;
//            canvas.drawCircle(x,y, Appended/3, paint);
//                String over ="Game Over";
//                paintText.getTextBounds(over, 0, over.length(), bounds);
////            int x = halfWidth - bounds.centerX();
//                int x2 = SIZE + SIZE+SIZE/2;
//
//                canvas.drawText(over, x2, height-(SIZE+SIZE/2), paintText);
//                textView3.setText("Tap Here To Restart!");
//            confirmButton.setClickable(false);
//            confirmButton.setFocusable(false);
//            }
        COUNTER++;
        view.invalidate();
    }
    public void start(){
        confirmButton.setClickable(true);
        confirmButton.setFocusable(true);
        Random random = new Random();
        int randomed = random.nextInt((9) + 1);
        if (!Objects.equals(words[randomed], soal)){
        soal = words[randomed];
        hint = clue[randomed];}
        tempSoal = soal.toCharArray();
//        tempSoal = new String[soal.length()];
        tempJawaban = new char[soal.length()];
        for (int i = 0; i< soal.length();i++){
            tempJawaban[i] = '*';
        }
        textView2.setText(new String(tempJawaban));
        textView1.setText(hint);

    }
    public void reset(){
        COUNTER = 0;
        textView3.setText("");

        confirmButton.setClickable(true);
        confirmButton.setFocusable(true);
        start();

    }

    public void check(View view) {
        char cari;
        if (!editText.getText().toString().equals("")){
            cari = (editText.getText().toString().charAt(0));
        if (Character.isLetter(cari)) {
            cari = Character.toLowerCase(cari);
            System.out.println(cari);
            boolean found = false;
            for (int i = 0; i < tempSoal.length; i++) {
                if (tempSoal[i] == cari) {
                    tempSoal[i] = '*';
                    tempJawaban[i] = cari;
                    found = true;
                    textView2.setText(new String(tempJawaban));
                    break;
                }
            }
            if (!found) {
                draw(imageView);
            }
            System.out.println(tempSoal);
            System.out.println(tempJawaban);
            if ((new String(tempJawaban)).equals(soal)) {
                int x2 = SIZE + SIZE + SIZE / 2;
                String over = "You Win!";
                canvas.drawText(over, x2, 600, paintText);
                textView3.setText("Tap Here To Restart!");
                confirmButton.setClickable(false);
                confirmButton.setFocusable(false);
            }
        }
        }
    }
}