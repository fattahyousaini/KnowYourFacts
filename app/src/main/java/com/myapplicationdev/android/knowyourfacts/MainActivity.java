package com.myapplicationdev.android.knowyourfacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Fragment> al;
    MyFragmentPagerAdapter adapter;
    ViewPager viewPager;
    Button btnReadLater;
    AlarmManager am;

    int reqCode = 12345;
    int notificationID = 888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager1);
        btnReadLater = findViewById(R.id.btnBack);

        FragmentManager fm = getSupportFragmentManager();

        al = new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());


        adapter = new MyFragmentPagerAdapter(fm, al);

        viewPager.setAdapter(adapter);



        btnReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                System.exit(0);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 300);

                Intent intent = new Intent(MainActivity.this,
                        ScheduledNotificationReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.next:
                int max = viewPager.getChildCount();
                if(viewPager.getCurrentItem() < max-1){
                    int nextPage  = viewPager.getCurrentItem() + 1;
                    viewPager.setCurrentItem(nextPage, true);

                }
                return true;
            case R.id.previous:
                if(viewPager.getCurrentItem() > 0){
                    int previousPage = viewPager.getCurrentItem() - 1;
                    viewPager.setCurrentItem(previousPage, true);
                }
                return true;
            case R.id.random:
                Random rnd = new Random();
                viewPager.setCurrentItem(rnd.nextInt(3), true);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
