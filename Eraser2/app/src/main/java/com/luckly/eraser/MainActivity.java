package com.luckly.eraser;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luckly.eraser.ui.dashboard.DashboardFragment;
import com.luckly.eraser.ui.home.HomeFragment;
import com.luckly.eraser.ui.notifications.NotificationsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageView Recorder_;
    private int nowPlaying = 0;
    private int[] MusicList = {R.raw.upon_a_star, R.raw.ghostsong};
    private HomeFragment Home = new HomeFragment();
    private DashboardFragment DashBoard = new DashboardFragment();
    private NotificationsFragment Notification = new NotificationsFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frames, Home).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        musicPlayer();// music On.



    }
    public void onDestroy(){
        mediaPlayer.stop();
        super.onDestroy();
    }
    private void musicPlayer(){
        mediaPlayer = MediaPlayer.create(getApplicationContext(), MusicList[nowPlaying]);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nowPlaying++;
                if(nowPlaying > 1) {
                    nowPlaying = 0;
                }
                else if(nowPlaying == 1)
                mp.start();
            }

        });

        mediaPlayer.start();
    }
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.navigation_home:
                    transaction.setCustomAnimations(R.anim.visible_effects, R.anim.invisible_effects);
                    transaction.replace(R.id.frames, Home).commitAllowingStateLoss();
                    break;
                case R.id.navigation_write:
                    transaction.setCustomAnimations(R.anim.visible_effects, R.anim.invisible_effects);
                    transaction.replace(R.id.frames, DashBoard).commitAllowingStateLoss();
                    break;
                case R.id.navigation_settings:
                    transaction.setCustomAnimations(R.anim.visible_effects, R.anim.invisible_effects);
                    transaction.replace(R.id.frames, Notification).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}