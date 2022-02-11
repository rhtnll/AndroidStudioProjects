package com.example.viewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        SpannableString span3 = new SpannableString("我如果爱你");
        ImageSpan image = new ImageSpan(this,R.drawable.qq, DynamicDrawableSpan.ALIGN_BOTTOM);
        span3.setSpan(image,3,4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(span3);

        vpager_one = (ViewPager) findViewById(R.id.pager);

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.view_one,null,false));
        aList.add(li.inflate(R.layout.view_two,null,false));
        aList.add(li.inflate(R.layout.view_three,null,false));
        mAdapter = new MyPagerAdapter(aList);
        vpager_one.setAdapter(mAdapter);

        final Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(MainActivity.this, "btn is clicked!", Toast.LENGTH_SHORT).show();
                final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                viewPager.setCurrentItem(0,false);
            }
        });

    }
}