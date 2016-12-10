package com.example.user.onedaynquestions.model;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by PCPC on 2016-12-10.
 */

public class MyCardView extends View {

    private Context context;
    private WindowManager windowManager;
    private int columnNum;
    private int orientation;

    public MyCardView(Context c){
        super(c);
        context = c;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = 300;
        params.height = 300;

        setLayoutParams(params);

    }
    public MyCardView(Context c, LinearLayout.LayoutParams layoutParams){
        super(c);
        context = c;

        setLayoutParams(layoutParams);

    }

    public MyCardView(Context c, AttributeSet attrib){
        super(c, attrib);
        context = c;
    }

    public MyCardView(Context c, AttributeSet attrib, int style){
        super(c, attrib, style);
        context = c;
    }

    public void init(){
        setColumnNum(1);
    }
    public void setColumnNum(int num){
        columnNum = num;
    }

    public void setOrientation(int ori){
        orientation = ori;

    }

    public void getColumnNum(){
        windowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }


}
