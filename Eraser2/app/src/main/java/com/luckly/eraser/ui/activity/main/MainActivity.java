package com.luckly.eraser.ui.activity.main;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luckly.eraser.R;
import com.luckly.eraser.ui.fragment.write.WriteFragment;
import com.luckly.eraser.ui.fragment.home.HomeFragment;
import com.luckly.eraser.ui.fragment.setting.SettingFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageView Recorder_;
    private int nowPlaying = 0;
    private int[] MusicList = {R.raw.upon_a_star, R.raw.ghostsong};
    private HomeFragment Home = new HomeFragment();
    private WriteFragment DashBoard = new WriteFragment();
    private SettingFragment Notification = new SettingFragment();
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
        mediaPlayer.setLooping(true);
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