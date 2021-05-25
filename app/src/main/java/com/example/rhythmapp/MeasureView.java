package com.example.rhythmapp;

import android.content.Context;
import android.content.res.Resources;
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

public class MeasureView extends View {
    //MeasureView is used to draw the measures onto the screen


    private Measure currentMeasure;

    //Images
    private Bitmap[] noteImages;

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
        //Initialize the images
        //The images have the same index (in noteImages) as the notes
        noteImages = new Bitmap[13];
        Resources r = getResources();
        noteImages[0] = BitmapFactory.decodeResource(r, R.drawable.whole_note);
        noteImages[1] = BitmapFactory.decodeResource(r, R.drawable.half_note);
        noteImages[2] = BitmapFactory.decodeResource(r, R.drawable.quarter_note);
        noteImages[3] = BitmapFactory.decodeResource(r, R.drawable.eighth_note);
        noteImages[4] = BitmapFactory.decodeResource(r, R.drawable.sixteenth_note);
        noteImages[5] = BitmapFactory.decodeResource(r, R.drawable.whole_rest);
        noteImages[6] = BitmapFactory.decodeResource(r, R.drawable.half_rest);
        noteImages[7] = BitmapFactory.decodeResource(r, R.drawable.quarter_rest);
        noteImages[8] = BitmapFactory.decodeResource(r, R.drawable.eighth_rest);
        noteImages[9] = BitmapFactory.decodeResource(r, R.drawable.sixteenth_rest);
        noteImages[10] = BitmapFactory.decodeResource(r, R.drawable.half_note_dot);
        noteImages[11] = BitmapFactory.decodeResource(r, R.drawable.quarter_note_dot);
        noteImages[12] = BitmapFactory.decodeResource(r, R.drawable.eighth_note_dot);
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
            int spacing = currentMeasure.getSize();

            //Draw time signature:
            String topSig = String.valueOf((int) currentMeasure.getTimeSignature()[0]);
            String botSig = String.valueOf((int) (4.0 / currentMeasure.getTimeSignature()[1]));
            p.setTextSize(100);
            canvas.drawText(topSig, 5, h / 2 - 10, p);
            canvas.drawText(botSig, 5, h/2 + 90, p);

            int curX = w / (spacing + 1);
            int curY = (h / 2) - 120;
            for (int i = 0; i < currentMeasure.getSize(); i++)
            {

                canvas.drawBitmap(noteImages[currentMeasure.getNote(i).getIndex()], curX, curY, p);
                Log.println(Log.DEBUG, "ImageDraw", "Image drawn: " + String.valueOf(currentMeasure.getNote(i).getIndex()));
                Log.println(Log.DEBUG, "ImageDraw", "Note duration: " + String.valueOf(currentMeasure.getNote(i).getDuration()));
                curX += w / (spacing + 1);
            }
        }
    }





}