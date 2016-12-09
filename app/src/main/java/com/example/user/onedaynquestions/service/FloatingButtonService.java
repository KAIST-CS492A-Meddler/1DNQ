package com.example.user.onedaynquestions.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.view.activity.NewCardActivity;

public class FloatingButtonService extends Service {

    private View floatView;
    private WindowManager windowManager;

    public FloatingButtonService() {

    }

    @Override
    public void onCreate(){
        super.onCreate();


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        floatView = inflater.inflate(R.layout.activity_floating_widget, null);

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatView, layoutParams);

        floatView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(getApplicationContext(), NewCardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
