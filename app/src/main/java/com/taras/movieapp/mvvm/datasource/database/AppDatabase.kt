package com.taras.movieapp.mvvm.datasource.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taras.movieapp.MovieApplication
import com.taras.movieapp.mvvm.datasource.database.dao.BaseResponseDao
import com.taras.movieapp.mvvm.datasource.database.dao.MovieDao
import com.taras.movieapp.mvvm.datasource.database.dao.PosterDao
import com.taras.movieapp.mvvm.model.BaseResponse
import com.taras.movieapp.mvvm.model.Movie
import com.taras.movieapp.mvvm.model.Poster

@Database(
    entities = [
        (BaseResponse::class),
        (Movie::class),
        (Poster::class)
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun baseResponseDao(): BaseResponseDao
    abstract fun movieDao(): MovieDao
    abstract fun posterDao(): PosterDao

    companion object INSTANCE {
        private var sInstance: AppDatabase? = null

        @Synchronized
        private fun newInstance(): AppDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(MovieApplication.getAppInstance, AppDatabase::class.java, "Movie-Database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return sInstance!!
        }

        @JvmStatic
        fun getInstance(): AppDatabase {
            if (sInstance == null) {
                newInstance()
            }
            return sInstance!!
        }
    }
}