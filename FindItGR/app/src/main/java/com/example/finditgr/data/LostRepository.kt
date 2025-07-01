package com.example.finditgr.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

// Repository για την πρόσβαση και διαχείριση των LostItem δεδομένων
// Κάνει "μετάφραση" από Flow σε LiveData για χρήση στο UI
class LostRepository(private val dao: LostItemDao) {

    // Όλες οι αγγελίες, ενημερώνονται αυτόματα όταν αλλάζει η βάση
    val allItems: LiveData<List<LostItem>> = dao.getAllItems().asLiveData()

    // Αναζήτηση αγγελιών με βάση το query, επίσης LiveData για παρακολούθηση
    fun searchItems(query: String): LiveData<List<LostItem>> = dao.searchItems(query).asLiveData()

    // Προσθήκη νέας αγγελίας (suspend για χρήση σε coroutine)
    suspend fun insert(item: LostItem) = dao.insert(item)

    // Διαγραφή αγγελίας (suspend για χρήση σε coroutine)
    suspend fun delete(item: LostItem) = dao.delete(item)
}
