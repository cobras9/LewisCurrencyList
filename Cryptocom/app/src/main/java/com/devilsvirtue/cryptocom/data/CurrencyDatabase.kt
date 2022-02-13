package com.devilsvirtue.cryptocom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type


@Database(entities = [Currency::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        val type: Type = object : TypeToken<List<Currency?>?>() {}.type

        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(
            context: Context,
        ): CurrencyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(
            context: Context,
        ) =
            Room.databaseBuilder(
                context.applicationContext,
                CurrencyDatabase::class.java, "currency.db"
            )
                    // Failed using this callback
/*                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch(Dispatchers.IO) {
                            // insert the data on the IO Thread
                            // There are definitely more elegant ways of inserting records
                            // not to mention later on syncing with backend services
                            // but for the sake of test without knowing too much context
                            // Thus inserting data here
                            val currencies = loadJSONFromAsset(context)

                            currencies?.let { list ->
                                val currencyList: List<Currency> =
                                    Gson().fromJson(list, type)
                                INSTANCE?.currencyDao()?.insertAll(currencyList)

                            }
                        }
                    }
                })*/
                .build()


    }


}
