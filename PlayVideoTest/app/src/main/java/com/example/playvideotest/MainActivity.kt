package com.example.playvideotest

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaController = MediaController(this)
        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")

        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)

        play.setOnClickListener {
            if (!videoView.isPlaying){
                videoView.start()
            }
        }

        pause.setOnClickListener {
            if(videoView.isPlaying){
               videoView.pause()
            }
        }

        stop.setOnClickListener {
            if (videoView.isPlaying){
                videoView.resume()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.suspend()
    }
}