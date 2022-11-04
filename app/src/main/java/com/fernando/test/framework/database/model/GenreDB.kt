package com.fernando.test.framework.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fernando.test.domain.Genre

@Entity(tableName = "genre")
class GenreDB(@PrimaryKey override  val id: Long, name: String) : Genre(id, name)