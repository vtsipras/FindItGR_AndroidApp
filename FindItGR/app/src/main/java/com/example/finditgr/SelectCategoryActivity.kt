package com.example.finditgr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finditgr.databinding.ActivitySelectCategoryBinding

class SelectCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Φορτώνουμε το layout μέσω ViewBinding
        binding = ActivitySelectCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Όταν πατηθεί το κουμπί "Χάθηκε", ξεκινάμε το MainActivity με κατηγορία "lost"
        binding.btnLost.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("category", "lost")
            startActivity(intent)
        }

        // Όταν πατηθεί το κουμπί "Βρέθηκε", ξεκινάμε το MainActivity με κατηγορία "found"
        binding.btnFound.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("category", "found")
            startActivity(intent)
        }

        // Όταν πατηθεί το κουμπί FAQ, ανοίγει η δραστηριότητα FAQActivity
        binding.buttonFAQ.setOnClickListener {
            val intent = Intent(this, FAQActivity::class.java)
            startActivity(intent)
        }

    }
}
