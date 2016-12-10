package com.example.user.onedaynquestions.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.view.activity.NewCardActivity;

public class FloatingButtonService extends Service {

    private View floatView;
    private WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    private CountDownTimer anim;

    private int originWidth, originHeight;
    private int magWidth, magHeight;
    private float xPos, yPos;

    private boolean isActivate;
    final int dt = 20, total = 1000;
    float gap, gapSum;
    private ImageView floatBtn;

    private int screenWidth, screenHeight;
    private boolean wait = false;
    public FloatingButtonService() {

    }

    @Override
    public void onCreate(){
        super.onCreate();
        xPos = 0;
        yPos = 0;
        isActivate = false;

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight= size.y;
        int min = screenWidth < screenHeight ? screenWidth: screenHeight;
        originHeight = min / 6;
        originWidth = originHeight;
        gap = (float)originWidth / ((float)total/dt);

        magWidth = originWidth * 2;
        magHeight= originHeight * 2;

        layoutParams = new WindowManager.LayoutParams(
                originWidth,
                originHeight,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        floatView = inflater.inflate(R.layout.activity_floating_widget, null);


        floatView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        wait = true;
                        layoutParams.width = magWidth;
                        layoutParams.height = magHeight;
                        windowManager.updateViewLayout(floatView, layoutParams);
                        gapSum = 0;
                        anim.start();
                        xPos = event.getRawX();
                        yPos = event.getRawY();

                        isActivate = true;
                        wait = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                            if(wait) return true;
                            xPos = event.getRawX();
                            yPos = event.getRawY();
                            layoutParams.x = (int)(screenWidth - xPos);
                            layoutParams.y = (int)(screenHeight - yPos);
                            windowManager.updateViewLayout(floatView, layoutParams);
//                        layoutParams.x = (int)event.getRawX();
//                        layoutParams.y = (int)event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(layoutParams.x > screenWidth /2)
                            layoutParams.x = screenWidth;
                        else
                            layoutParams.x = 0;
                        if(isActivate) {
                            layoutParams.width = originWidth;
                            layoutParams.height = originHeight;
                            windowManager.updateViewLayout(floatView, layoutParams);

                            Intent intent_newcard = new Intent(getBaseContext(), NewCardActivity.class);
                            intent_newcard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent_newcard.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent_newcard);
                        }
                        anim.onFinish();
                        //Intent intent = new Intent(getBaseContext(), NewCardActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(intent);
                        break;
                }
                return true;
            }
        });


        //windowManager.addView(floatView, layoutParams);


        anim = new CountDownTimer(total, dt) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                layoutParams.width = originWidth;
                layoutParams.height = originHeight;
                windowManager.updateViewLayout(floatView, layoutParams);
                isActivate = false;
            }
        };
    }
    @Override
    public void onDestroy() {
        if(windowManager != null) {
            if(floatView != null) windowManager.removeView(floatView);
        }
        anim.cancel();
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
