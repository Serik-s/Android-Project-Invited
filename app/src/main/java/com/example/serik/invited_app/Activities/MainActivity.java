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
import com.example.serik.invited_app.Models.Event;
import com.example.serik.invited_app.Models.User;
import com.example.serik.invited_app.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.roughike.bottombar.TabSelectionInterceptor;

import java.util.ArrayList;

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users");
        readUserData(userRef);

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

    private void readUserData(DatabaseReference userRef) {
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////              Object value = dataSnapshot.getValue(Object.class);
//                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
//                    Log.e(mainActivityTAG, "this is what we get from user table: " + dataSnapshot.getChildren());
//                }
//
//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    <YourClass> post = postSnapshot.getValue(<YourClass>.class);
//                    Log.e("Get Data", post.<YourMethod>());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(mainActivityTAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        });

        userRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.getKey());


                        ArrayList<Event> myDataset = new ArrayList<Event>();

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            System.out.println(dsp.getKey());
                            System.out.println(dsp.getValue());

                            Event myRestaurant = dsp.getValue(Event.class);

//                            myDataset.add(myRestaurant);
                            user.visitingEvents.add(myRestaurant);
                            Log.e(mainActivityTAG, "this is event:  " + myRestaurant);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
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
