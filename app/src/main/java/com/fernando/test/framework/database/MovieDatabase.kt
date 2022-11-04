package com.fernando.test.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fernando.test.framework.database.dao.MovieDao
import com.fernando.test.framework.database.model.GenreDB
import com.fernando.test.framework.database.model.MovieTopRateDB
import com.fernando.test.framework.database.model.MovieUpcomingDB

@Database(version = 1, entities = [MovieUpcomingDB::class, MovieTopRateDB::class, GenreDB::class])
@TypeConverters(ArrayIntConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}