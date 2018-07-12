package com.example.me.sevenminutes;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScreenSlidePagerActivity extends FragmentActivity {


    /**
     * The pager widget, which handles animation and allows swiping
     * horizontally to access previous and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private TextView timerTextView;
    private CountDownTimerPausable timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        timerTextView = (TextView) findViewById(R.id.timer_text_view);
        timerTextView.setText("" + 10);

        timer = new CountDownTimerPausable(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("" + (Math.round(millisUntilFinished*0.001f)-1));
                Log.d("sec","second"+millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mPager.getCurrentItem()<mPagerAdapter.getCount()){
                            mPager.setCurrentItem(mPager.getCurrentItem()+1);
                        }
                        else {
                            timerTextView.setText("done!");
                        }
                    }
                },1500);
            }
        }.start();

        findViewById(R.id.pause_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.resumePause();
            }
        });

        mPager.addOnPageChangeListener(new PagerChangeListener());


    }

    public class PagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
        @Override
        public void onPageSelected(int position) {
            timerTextView.setText("" + 10);
            timer.reset(11000);
            if (!timer.isPaused()) {
                timer.start();
            }
        }


    }


    /*@Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system
            // to handle the Back button. This calls finish() on this activity and
            // pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }*/

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        //number of pages
        private static final int NUM_PAGES = 10;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt("index", position);
            WorkoutSlidePageFragment fragment = new WorkoutSlidePageFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}

