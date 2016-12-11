package com.example.user.onedaynquestions.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.view.activity.NewCardActivity;

public class FloatingButtonService extends Service {

    private View floatView, backgroundView;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private WindowManager.LayoutParams layoutParamsBackground;
    private CountDownTimer anim;
    private ImageButton exitButton;

    private int originWidth, originHeight;
    private int magWidth, magHeight;
    public static float xPos, yPos;
    private float sum;
    private int sumThreshold;

    private boolean isActivate;
    final int dt = 20, total = 500;
    float gap, gapSum;
    private ImageView floatBtn;

    private int screenWidth, screenHeight;
    private boolean wait = false;
    public FloatingButtonService() {

    }

    @Override
    public void onCreate(){
        super.onCreate();
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
        sumThreshold = screenWidth / 3;
        gap = (float)originWidth / ((float)total/dt);

        magWidth = originWidth * 2;
        magHeight= originHeight * 2;

        layoutParams = new WindowManager.LayoutParams(
                originWidth,
                originHeight,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams .gravity = Gravity.LEFT | Gravity.TOP;

        layoutParams.x = (int)(FloatingButtonService.xPos);
        if(layoutParams.x > screenWidth / 2) layoutParams.x = screenWidth;
        else layoutParams.x = 0;
        layoutParams.y = (int)(FloatingButtonService.yPos);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        floatView = inflater.inflate(R.layout.activity_floating_widget, null);

        backgroundView= inflater.inflate(R.layout.background_floating_button, null);
        backgroundView.setBackgroundColor(Color.argb(125, 0,0,0));
        backgroundView.setAlpha(0);
        exitButton = (ImageButton) backgroundView.findViewById(R.id.exitButton);
        layoutParamsBackground = new WindowManager.LayoutParams(
                magWidth,
                magHeight,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParamsBackground .gravity = Gravity.LEFT | Gravity.TOP;
        layoutParamsBackground.x = (screenWidth - magWidth)/ 2;
        layoutParamsBackground.y = (screenHeight - magHeight)/ 2;
        windowManager.addView(backgroundView,layoutParamsBackground);



        floatView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float distX, distY;
                int len;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        wait = true;
                        layoutParams.width = magWidth;
                        layoutParams.height = magHeight;
                        windowManager.updateViewLayout(floatView, layoutParams);
                        gapSum = 0;
                        anim.start();
                        FloatingButtonService.xPos = event.getRawX() - layoutParams.width / 2;
                        FloatingButtonService.yPos = event.getRawY() - layoutParams.height ;
                        sum = 0;
                        isActivate = true;
                        wait = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(wait) return true;
                        float dx = FloatingButtonService.xPos - (event.getRawX() - layoutParams.width / 2);
                        float dy = FloatingButtonService.yPos - (event.getRawY() - layoutParams.height);
                        sum += Math.sqrt(dx * dx + dy * dy);
                        FloatingButtonService.xPos = event.getRawX() - layoutParams.width / 2;
                        FloatingButtonService.yPos = event.getRawY() - layoutParams.height;

                        layoutParams.x = (int)(FloatingButtonService.xPos);
                        layoutParams.y = (int)(FloatingButtonService.yPos);
                        if(sum > sumThreshold){
                            anim.onFinish();
                            if(backgroundView.getAlpha() < 0.1) {
                                backgroundView.setAlpha(1);
                                windowManager.updateViewLayout(backgroundView, layoutParamsBackground);
                            }
                        }

                        distX = xPos - layoutParamsBackground.x;
                        distY = yPos - layoutParamsBackground.y;
                        len = (int)Math.sqrt(distX * distX + distY * distY);

                        if(len < layoutParamsBackground.width / 2){
                            backgroundView.setBackgroundColor(Color.argb(125, 255,0,0));
                        }else{
                            backgroundView.setBackgroundColor(Color.argb(125, 0,0,0));
                        }

                        windowManager.updateViewLayout(floatView, layoutParams);
//                        layoutParams.x = (int)event.getRawX();
//                        layoutParams.y = (int)event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        anim.cancel();
                        layoutParams.width = originWidth;
                        layoutParams.height = originHeight;
                        FloatingButtonService.xPos = event.getRawX() - layoutParams.width / 2;
                        FloatingButtonService.yPos = event.getRawY() - layoutParams.height;

                        distX = xPos - layoutParamsBackground.x;
                        distY = yPos - layoutParamsBackground.y;
                        len = (int)Math.sqrt(distX * distX + distY * distY);
                        layoutParams.x = (int) (FloatingButtonService.xPos);
                        if (layoutParams.x > screenWidth / 2) layoutParams.x = screenWidth;
                        else layoutParams.x = 0;
                        layoutParams.y = (int) (FloatingButtonService.yPos);
                        windowManager.updateViewLayout(floatView, layoutParams);

                        backgroundView.setAlpha(0);
                        windowManager.updateViewLayout(backgroundView, layoutParamsBackground);
                        if(  len < layoutParamsBackground.width / 2 ){
                            stopSelf();
                        }else {
                            if (isActivate && (sum < sumThreshold)) {
                                Intent intent_newcard = new Intent(getBaseContext(), NewCardActivity.class);
                                intent_newcard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent_newcard.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent_newcard);
                            }
                        }
                        //Intent intent = new Intent(getBaseContext(), NewCardActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(intent);
                        break;
                }
                return true;
            }
        });


        windowManager.addView(floatView, layoutParams);


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
