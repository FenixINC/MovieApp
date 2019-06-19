package com.taras.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taras.movieapp.coroutines.CoroutineFragment
import com.taras.movieapp.mvvm.view.ContentPagerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, ContentPagerFragment.newInstance())
            .commit()

//        supportFragmentManager.beginTransaction()
//                .replace(R.id.content, CoroutineFragment.newInstance())
//                .commit()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }
}