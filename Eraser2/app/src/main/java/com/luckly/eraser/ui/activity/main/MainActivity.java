package com.luckly.eraser.ui.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luckly.eraser.R;
import com.luckly.eraser.ui.activity.splash.SplashActivity;
import com.luckly.eraser.ui.button.back.BackPressCloseHandler;
import com.luckly.eraser.ui.fragment.write.WriteFragment;
import com.luckly.eraser.ui.fragment.home.HomeFragment;
import com.luckly.eraser.ui.fragment.setting.SettingFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ImageView Recorder_;
    private int nowPlaying = 0;
    static ConstraintLayout container;
    Bitmap bitmap;
    BackPressCloseHandler backPressCloseHandler;
    String lawBitmap;
    private static final String KEY = "SAVE_IMAGE";
    private int[] MusicList = {R.raw.upon_a_star, R.raw.ghostsong};
    private HomeFragment Home = new HomeFragment();
    private WriteFragment DashBoard = new WriteFragment();
    SharedPreferences sharedPreferences;
    private SettingFragment Notification = new SettingFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.visible_effects, R.anim.invisible_effects);

        container = findViewById(R.id.container);
        sharedPreferences = getSharedPreferences(KEY,MODE_PRIVATE);

        lawBitmap = sharedPreferences.getString("Bitmap_String", null);

        Log.i("getResult()", lawBitmap);

        if(lawBitmap == null)
            container.setBackground(getDrawable(R.drawable.background));
        else {
            bitmap = StringToBitMap(lawBitmap);
            container.setBackground(new BitmapDrawable(bitmap));
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frames, Home).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        musicPlayer();// music On.



    }
    public static BitmapDrawable getBackground(){
        return new BitmapDrawable(((BitmapDrawable)container.getBackground()).getBitmap());
    }
    @Override
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }
    public String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);

        return temp;

    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public void setBackground(BitmapDrawable bitmap, Bitmap lawBitmap){
        this.lawBitmap = BitMapToString(lawBitmap);
        container.setBackground(bitmap);
    }
    public void onDestroy() {
        mediaPlayer.stop();
        sharedPreferences = getSharedPreferences(KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Bitmap_String", lawBitmap);
        editor.commit();
        super.onDestroy();
    }
    public void setMusic(){
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ghostsong);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
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