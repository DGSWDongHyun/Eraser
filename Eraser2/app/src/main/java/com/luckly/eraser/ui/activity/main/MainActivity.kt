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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.luckly.eraser.R
import com.luckly.eraser.databinding.ActivityMainBinding
import com.luckly.eraser.ui.activity.splash.SplashActivity
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    // using Class Variable
    private var mediaPlayer: MediaPlayer? = null
    private val nowPlaying = 0
    var bitmap: Bitmap? = null
    var lawBitmap: String? = null
    private val MusicList = intArrayOf(R.raw.upon_a_star, R.raw.ghostsong)
    var sharedPreferences: SharedPreferences? = null
    private lateinit var mainBinding : ActivityMainBinding

    // using Class Variable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initLayout()
    }

    private fun initLayout() {
        val intent = Intent(applicationContext, SplashActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.visible_effects, R.anim.invisible_effects)
        container = findViewById(R.id.container)



        sharedPreferences = getSharedPreferences(KEY, MODE_PRIVATE)

        lawBitmap = sharedPreferences!!.getString("Bitmap_String", null)

        if (lawBitmap == null) container!!.background = getDrawable(R.drawable.background) else {
            bitmap = stringToBitMap(lawBitmap)
            container!!.background = BitmapDrawable(bitmap)
        }

        //init navView.
        val navController = findNavController(this, R.id.fragmentNavHost)
        mainBinding.bottomNavigation.setupWithNavController(navController)
        //end. - navView

        musicPlayer() // music On.
    }

    override fun onBackPressed() {
         buildReqEndAlert()
    }

    private fun buildReqEndAlert() {
        val alertReq = AlertDialog.Builder(this)
                .setTitle("고민이 많을때면, 언제든 다시 찾아오세요.")
                .setMessage("\n저는 언제든지 이곳에 있습니다 :)")
                .setNegativeButton("아직 안 나갈래요.") { i, dlInterface ->  }
                .setPositiveButton("언제든 다시 올게요.") { i, dlInterface -> finish() }
                .create()

        alertReq.show()
    }

    private fun bitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun getBackground(): BitmapDrawable {
        return BitmapDrawable((container!!.background as BitmapDrawable).bitmap)
    }
    fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    fun setBackground(bitmap: BitmapDrawable?, lawBitmap: Bitmap) {
        this.lawBitmap = bitMapToString(lawBitmap)
        container!!.background = bitmap
    }

    public override fun onDestroy() {
        mediaPlayer!!.stop()
        sharedPreferences = getSharedPreferences(KEY, MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putString("Bitmap_String", lawBitmap)
        editor.apply()
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