package com.devilsvirtue.cryptocom.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency ORDER BY id DESC")
    fun loadAll(): Flow<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currency: List<Currency?>?)

    @Query("DELETE FROM currency")
    fun nukeTable()

    /**
     *  Maybe we can have some Enum or const for different field,
     *  so that this method can take all fields when sorting
     */
    @Query(
        "SELECT * FROM currency ORDER BY " +
                "CASE WHEN :isAsc = 1 THEN name END ASC, " +
                "CASE WHEN :isAsc = 0 THEN name END DESC"
    )
    fun sortCurrencyByName(isAsc: Boolean?): Flow<List<Currency>>

}
