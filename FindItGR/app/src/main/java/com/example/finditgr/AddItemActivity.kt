package com.example.finditgr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finditgr.data.LostDatabase
import com.example.finditgr.data.LostItem
import com.example.finditgr.databinding.ActivityAddItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding  // View binding για το layout
    private lateinit var database: LostDatabase            // Αναφορά στη Room Database
    private var category: String = "lost"                  // Η κατηγορία της αγγελίας (default "lost")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Αρχικοποιούμε το binding και θέτουμε το layout
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Παίρνουμε τη βάση δεδομένων
        database = LostDatabase.getDatabase(this)

        // Παίρνουμε την κατηγορία από το intent (π.χ. "lost" ή "found")
        category = intent.getStringExtra("category") ?: "lost"

        // Όταν πατηθεί το κουμπί αποθήκευσης
        binding.buttonSave.setOnClickListener {
            saveItem()
        }
    }

    // Αποθηκεύει την αγγελία στη βάση
    private fun saveItem() {
        // Παίρνουμε τα δεδομένα από τα πεδία εισαγωγής
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val location = binding.editTextLocation.text.toString().trim()
        val date = binding.editTextDate.text.toString().trim()
        val fullName = binding.editTextFullName.text.toString()
        val phone = binding.editTextPhone.text.toString()

        // Αν κάποια βασικά πεδία είναι άδεια, εμφανίζουμε μήνυμα και σταματάμε
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Παρακαλώ συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show()
            return
        }

        // Δημιουργούμε αντικείμενο LostItem με τα στοιχεία
        val lostItem = LostItem(
            title = title,
            description = description,
            location = location,
            date = date,
            fullName = fullName,
            phone = phone,
            category = category  // Καταχωρείται η αντίστοιχη κατηγορία
        )

        // Αποθήκευση σε background thread με χρήση coroutine
        CoroutineScope(Dispatchers.IO).launch {
            database.lostItemDao().insert(lostItem)
            runOnUiThread {
                Toast.makeText(this@AddItemActivity, "Η αγγελία αποθηκεύτηκε!", Toast.LENGTH_SHORT).show()
                finish()  // Κλείνει η activity και επιστρέφει στην προηγούμενη οθόνη
            }
        }
    }
}
