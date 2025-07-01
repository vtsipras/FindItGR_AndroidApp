package com.example.finditgr.data

import com.example.finditgr.data.LostItemDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Βασική βάση δεδομένων Room για τα LostItem αντικείμενα
@Database(entities = [LostItem::class], version = 3, exportSchema = false)
abstract class LostDatabase : RoomDatabase() {

    // Παρέχει πρόσβαση στις DAO μεθόδους
    abstract fun lostItemDao(): LostItemDao

    companion object {
        @Volatile
        private var INSTANCE: LostDatabase? = null  // Singleton instance για αποφυγή πολλαπλών βάσεων

        // Επιστρέφει την υπάρχουσα βάση ή την δημιουργεί αν δεν υπάρχει
        fun getDatabase(context: Context): LostDatabase {
            return INSTANCE ?: synchronized(this) {
                // Δημιουργούμε τη βάση δεδομένων, και αν αλλάξει η έκδοση, διαγράφουμε παλιά δεδομένα
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LostDatabase::class.java,
                    "lost_database"
                )
                    .fallbackToDestructiveMigration()  // Αν αλλάξει schema, ξεκινάμε από το μηδέν (καθαρή βάση)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
