package com.example.finditgr.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finditgr.data.LostItem
import kotlinx.coroutines.flow.Flow

// Data Access Object για τη διαχείριση των LostItem στην βάση δεδομένων
@Dao
interface LostItemDao {

    // Προσθέτει μια νέα αγγελία και επιστρέφει το νέο ID
    @Insert
    fun insert(item: LostItem): Long

    // Διαγράφει μια υπάρχουσα αγγελία
    @Delete
    fun delete(item: LostItem): Unit

    // Φέρνει όλες τις αγγελίες, με πιο πρόσφατες πρώτες
    @Query("SELECT * FROM lost_items ORDER BY date DESC")
    fun getAllItems(): Flow<List<LostItem>>

    // Φέρνει τις αγγελίες φιλτραρισμένες ανά κατηγορία (π.χ. "Χάθηκε" ή "Βρέθηκε")
    @Query("SELECT * FROM lost_items WHERE category = :category ORDER BY date DESC")
    fun getItemsByCategory(category: String): Flow<List<LostItem>>

    // Αναζήτηση τίτλου ή περιγραφής που περιέχουν το query, πιο πρόσφατα πρώτα
    @Query("SELECT * FROM lost_items WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY date DESC")
    fun searchItems(query: String): Flow<List<LostItem>>
}
