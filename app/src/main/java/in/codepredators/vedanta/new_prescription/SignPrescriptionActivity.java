package in.codepredators.vedanta.new_prescription;

import android.content.Context;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;

import in.codepredators.vedanta.R;
import in.codepredators.vedanta.Vedanta;
import in.codepredators.vedanta.home.fragments.PrescriptionSaved;
import in.codepredators.vedanta.home.fragments.PrescriptionSigned;
import in.codepredators.vedanta.models.Prescription;


import in.codepredators.vedanta.R;


public class SignPrescriptionActivity extends AppCompatActivity {

    DrawingView dv;
    private Paint mPaint;
    Button saveButton;

    public String signString="";
    String name="",age="",sex="",address="";
    Intent i;
    PrescriptionSaved prescriptionSaved=new PrescriptionSaved();
    Prescription prescription=new Prescription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_prescription);
//        dv = findViewById(R.id.drawingView);
        saveButton = findViewById(R.id.saveButton);

        i=getIntent();

        dv = new DrawingView(this);
        dv.setLayoutParams(new LinearLayout.LayoutParams(
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics())),
                Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()))
        ));
        ((LinearLayout) findViewById(R.id.parent)).addView(dv);
//        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signBitmap = dv.getBitmap();
                if (signBitmap != null) {

                    signString = BitMapToString(signBitmap);
                }
                name=i.getStringExtra("NAME");
                String age=i.getStringExtra("AGE");
                String sex=i.getStringExtra("SEX");
                String address=i.getStringExtra("ADDRESS");
                String medications=i.getStringExtra("MEDICATIONS");
                String diagnosis=i.getStringExtra("DIAGNOSIS");
                String doctor_id=i.getStringExtra("DOCTOR_ID");
                String id=i.getStringExtra("ID");
                String advice=i.getStringExtra("ADVICE");
                prescription.advice=new ArrayList<>();
                prescription.advice.add(advice);
                prescription.diagnosis=new ArrayList<>();
                prescription.diagnosis.add(diagnosis);
                Log.i("id",id+"  idd");
                prescription.setId("12345");
                prescription.prescription=new ArrayList<>();
                prescription.prescription.add(medications);
                //  prescription.setDate(Integer.parseInt("12-12-2020"));
                prescription.setDoctorId("DOC123");
                //  prescription.setName(name);
                prescription.setSignature(signString);
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Vedanta.getDatabase(getApplicationContext()).VedantaDAO().insertpresciption(prescription);
                    }
                });
                t.start();
                finish();
            }
        });
    }
    public String sendSign(){
        return signString;
    }


    public class DrawingView extends View {

        public int width;
        public int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(circlePath, circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }


        public Bitmap getBitmap() {
            this.setDrawingCacheEnabled(true);
            this.buildDrawingCache();
            Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
            this.setDrawingCacheEnabled(false);


            return bmp;
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap = bitmap.createScaledBitmap(bitmap, 300, 100, false);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}