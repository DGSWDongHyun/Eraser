package com.luckly.eraser.ui.activity.main

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.luckly.eraser.R
import com.luckly.eraser.ui.activity.splash.SplashActivity
import com.luckly.eraser.ui.button.back.BackPressCloseHandler
import com.luckly.eraser.ui.fragment.home.HomeFragment
import com.luckly.eraser.ui.fragment.setting.SettingFragment
import com.luckly.eraser.ui.fragment.write.WriteFragment
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    // using Class Variable
    private var mediaPlayer: MediaPlayer? = null
    private val nowPlaying = 0
    var bitmap: Bitmap? = null
    var backPressCloseHandler: BackPressCloseHandler? = null
    var lawBitmap: String? = null
    private val MusicList = intArrayOf(R.raw.upon_a_star, R.raw.ghostsong)
    private val Home = HomeFragment()
    private val DashBoard = WriteFragment()
    var sharedPreferences: SharedPreferences? = null
    private var navController : NavController ?= null

    // using Class Variable

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backPressCloseHandler = BackPressCloseHandler(this)
        val intent = Intent(applicationContext, SplashActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.visible_effects, R.anim.invisible_effects)
        container = findViewById(R.id.container)

        var navController2 = Navigation.findNavController(this, R.id.fragment)

        Log.d("d", navController2.currentDestination?.getId().toString());

        sharedPreferences = getSharedPreferences(KEY, MODE_PRIVATE)

        lawBitmap = sharedPreferences!!.getString("Bitmap_String", null)

        if (lawBitmap == null) container!!.setBackground(getDrawable(R.drawable.background)) else {
            bitmap = StringToBitMap(lawBitmap)
            container!!.setBackground(BitmapDrawable(bitmap))
        }

        //init navView.
        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build()
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(navView, navController!!)
        //end. - navView

        musicPlayer() // music On.
    }
    override fun onBackPressed() {
        backPressCloseHandler!!.onBackPressed()
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun getBackground(): BitmapDrawable? {
        return BitmapDrawable((container!!.background as BitmapDrawable).bitmap)
    }
    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    fun setBackground(bitmap: BitmapDrawable?, lawBitmap: Bitmap) {
        this.lawBitmap = BitMapToString(lawBitmap)
        container!!.background = bitmap
    }

    public override fun onDestroy() {
        mediaPlayer!!.stop()
        sharedPreferences = getSharedPreferences(KEY, MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putString("Bitmap_String", lawBitmap)
        editor.commit()
        super.onDestroy()
    }


    private fun musicPlayer() {
        mediaPlayer = MediaPlayer.create(applicationContext, MusicList[nowPlaying])
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.start()
    }
    companion object {
        var container: ConstraintLayout? = null
        private const val KEY = "SAVE_IMAGE"
    }
}