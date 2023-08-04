package com.animall.Database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.animall.Daos.MilkSaleDao
import com.animall.Entity.MilkSaleEntity

@Database(entities = [MilkSaleEntity::class], version = 1, exportSchema = false)
abstract class MilkSaleDatabase : RoomDatabase() {

    abstract fun milkSaleDao(): MilkSaleDao

    companion object {
        @Volatile
        private var INSTANCE: MilkSaleDatabase? = null

        fun getDatabase(context: Context): MilkSaleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MilkSaleDatabase::class.java,
                    "milk_sale_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
