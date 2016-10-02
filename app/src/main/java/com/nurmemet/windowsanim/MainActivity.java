package com.nurmemet.windowsanim;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    ImageView mImg1;
    ImageView mImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImg1 = (ImageView) findViewById(R.id.circle_anim);
        WidowsStartCircle circleAnim = new WidowsStartCircle();
        mImg1.setBackground(circleAnim);
        circleAnim.startAnim();

        mImg2 = (ImageView) findViewById(R.id.line_anim);
        WindowsStartLine lineAnim = new WindowsStartLine();
        mImg2.setImageDrawable(lineAnim);

        lineAnim.startAnim();

    }
}
