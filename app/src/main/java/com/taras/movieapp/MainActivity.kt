package com.taras.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taras.movieapp.content.ContentPagerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, ContentPagerFragment.newInstance())
            .commit()
    }
}