package com.example.rhythmapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.rhythmapp.notes.Measure;
import com.example.rhythmapp.notes.Note;

public class MeasureView extends View {
    //MeasureView is used to draw the measures onto the screen


    private Measure currentMeasure;

    //Test:
    private Bitmap testQBitmap;

    public MeasureView(Context context) {

        super(context);
        init();
    }


    //These constructors are required by extending View
    public MeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        testQBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.quarter_note);
    }

    public void setCurrentMeasure(Measure m)
    {
        currentMeasure = m;

        //Redraw:
        postInvalidate();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Color.BLACK);

        //Get the dimensions of the view
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        //Draw a line for the notes to sit on
        int thickness = 2;
        Rect middleLine = new Rect(0, height / 2 - thickness, width, height / 2 + thickness);
        canvas.drawRect(middleLine, paint);

        drawMeasure(canvas, width, height, paint);
    }

    private void drawMeasure(Canvas canvas, int w, int h, Paint p)
    {
        if (currentMeasure != null)
        {
            int spacing = currentMeasure.measure.size();

            //Draw time signature:
            String topSig = String.valueOf((int) currentMeasure.getTimeSignature()[0]);
            String botSig = String.valueOf((int) (4 * currentMeasure.getTimeSignature()[1]));
            p.setTextSize(100);
            canvas.drawText(topSig, 5, h / 2 - 10, p);
            canvas.drawText(botSig, 5, h/2 + 90, p);

            int curX = w / (spacing + 1);
            int curY = (h / 2) - 120;
            for (Note n : currentMeasure.measure)
            {
                if (n == Note.QUARTER_NOTE)
                {
                    canvas.drawBitmap(testQBitmap, curX, curY, p);
                    Log.println(Log.DEBUG, "DrawTag", "Drew bitmap");
                }

                curX += w / (spacing + 1);
            }
        }
    }





}
