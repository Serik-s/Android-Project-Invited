package com.example.serik.invited_app.Activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.serik.invited_app.Adapters.MainAdapter;
import com.example.serik.invited_app.Models.User;
import com.example.serik.invited_app.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.roughike.bottombar.TabSelectionInterceptor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    public static User user;

    private static String mainActivityTAG = "MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        user = new User(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getDisplayName());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MainAdapter adapterViewPager = new MainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOffscreenPageLimit(3);

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Toast.makeText(MainActivity.this,
                        "Tab position: " + tabId, Toast.LENGTH_SHORT).show();
                Log.e(mainActivityTAG, "tabID is " + tabId);
                Log.e(mainActivityTAG, "tab_events is " + R.id.tab_events);
                Log.e(mainActivityTAG, "tab_search is " + R.id.tab_search);
                Log.e(mainActivityTAG, "tab_profile is " + R.id.tab_profile);
                if (tabId == R.id.tab_events) {
                    mViewPager.setCurrentItem(0);
                } else if (tabId == R.id.tab_search) {
                    mViewPager.setCurrentItem(1);
                } else {
                    mViewPager.setCurrentItem(2);
                }
            }
        });

    }

    public void logOut(View v) {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
