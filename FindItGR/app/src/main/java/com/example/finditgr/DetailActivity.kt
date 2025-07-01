package com.example.finditgr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finditgr.data.LostItem
import com.example.finditgr.data.LostDatabase
import com.example.finditgr.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: LostDatabase
    private var lostItem: LostItem? = null  // Το αντικείμενο που θα εμφανιστεί

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Συνδέουμε το layout με το binding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Παίρνουμε το instance της βάσης δεδομένων
        database = LostDatabase.getDatabase(this)

        // Παίρνουμε το αντικείμενο που στάλθηκε από την προηγούμενη activity
        lostItem = intent.getParcelableExtra("lostItem")

        lostItem?.let { item ->
            // Αν το αντικείμενο υπάρχει, γεμίζουμε τα πεδία του UI
            binding.textTitle.text = item.title
            binding.textDescription.text = item.description
            binding.textLocation.text = item.location
            binding.textDate.text = item.date
            binding.textFullName.text = item.fullName
            binding.textPhone.text = item.phone

            // TODO: Αν στο μέλλον προσθέσεις εικόνες, μπορείς εδώ να φορτώσεις την εικόνα στο ImageView

            // Όταν πατηθεί το κουμπί διαγραφής
            binding.buttonDelete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    database.lostItemDao().delete(item)  // Διαγραφή από τη βάση
                    finish()  // Κλείνει την activity και επιστρέφει πίσω
                }
            }

        } ?: run {
            // Αν δεν εστάλη αντικείμενο, κλείσε την activity
            finish()
        }
    }
}
