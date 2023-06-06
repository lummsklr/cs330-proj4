package com.example.pj4test

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.pj4test.fragment.AudioFragment
import com.example.pj4test.fragment.CameraFragment
import java.time.Duration
import java.time.LocalTime
import java.util.*

class MainActivity : AppCompatActivity(), Communicator {
    private val TAG = "MainActivity"

    // permissions
    private val permissions = arrayOf(RECORD_AUDIO, CAMERA)
    private val PERMISSIONS_REQUEST = 0x0000001;

    private lateinit var audioFragment: AudioFragment
    private lateinit var cameraFragment: CameraFragment

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions() // check permissions

        audioFragment = supportFragmentManager.findFragmentById(R.id.audioFragmentContainerView) as AudioFragment
        cameraFragment = supportFragmentManager.findFragmentById(R.id.cameraFragmentContainerView) as CameraFragment
    }

    private fun checkPermissions() {
        if (permissions.all{ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED}){
            Log.d(TAG, "All Permission Granted")
        }
        else{
            requestPermissions(permissions, PERMISSIONS_REQUEST)
        }
    }

    override fun controlAudio(switch: Boolean) {
        if (switch)
            audioFragment.onResume()
        else
            audioFragment.onPause()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun controlCamera(switch: Boolean) {
        if (switch) {
            cameraFragment.resume()
            cameraFragment.setStartTime(LocalTime.now())
        }
        else
            cameraFragment.pause()
    }
}