package com.example.finditgr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finditgr.data.LostDatabase
import com.example.finditgr.data.LostItem
import com.example.finditgr.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: LostDatabase
    private lateinit var adapter: LostItemAdapter
    private var category: String = "lost"  // Προεπιλεγμένη κατηγορία

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Λήψη της κατηγορίας από το Intent, με προεπιλογή "lost"
        category = intent.getStringExtra("category") ?: "lost"

        // Αρχικοποίηση της βάσης δεδομένων
        database = LostDatabase.getDatabase(this)

        // Δημιουργία adapter με κενή λίστα αρχικά
        adapter = LostItemAdapter(listOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Παρακολούθηση δεδομένων από τη βάση, φιλτραρισμένα κατά κατηγορία, μέσω LiveData
        database.lostItemDao().getItemsByCategory(category)
            .asLiveData()
            .observe(this, Observer { items ->
                updateUI(items)  // Ενημέρωση UI με τα δεδομένα
            })

        // Swipe to refresh για ανανέωση λίστας (εδώ απλώς σταματάει το refresh μετά από καθυστέρηση)
        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }

        // Κουμπί προσθήκης νέας αγγελίας
        binding.fabAddItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("category", category) // Περνάμε την τρέχουσα κατηγορία στην νέα οθόνη
            startActivity(intent)
        }

        // Εμφάνιση βοήθειας με Snackbar για το κουμπί προσθήκης
        binding.fabAddItem.postDelayed({
            Snackbar.make(binding.fabAddItem, "Πατήστε εδώ για καταχώρηση νέου αντικειμένου", Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fabAddItem)
                .show()
        }, 500)
    }

    // Απλή μέθοδος που σταματάει το animation του SwipeRefresh μετά από 1.5 δευτερόλεπτο
    private fun refreshData() {
        binding.swipeRefresh.isRefreshing = true
        binding.recyclerView.postDelayed({
            binding.swipeRefresh.isRefreshing = false
        }, 1500)
    }

    // Ενημέρωση του UI ανάλογα αν υπάρχουν ή όχι δεδομένα
    private fun updateUI(items: List<LostItem>) {
        if (items.isEmpty()) {
            binding.recyclerView.visibility = View.GONE  // Κρύψε το RecyclerView αν δεν υπάρχουν στοιχεία
            binding.emptyStateLayout.visibility = View.VISIBLE  // Δείξε μήνυμα ότι δεν υπάρχουν δεδομένα
        } else {
            binding.recyclerView.visibility = View.VISIBLE  // Δείξε το RecyclerView
            binding.emptyStateLayout.visibility = View.GONE  // Κρύψε το μήνυμα άδειας λίστας
            adapter.updateItems(items)  // Ενημέρωσε τον adapter με τη νέα λίστα
        }
    }
}
