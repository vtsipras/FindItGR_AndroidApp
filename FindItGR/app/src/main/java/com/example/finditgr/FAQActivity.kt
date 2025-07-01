package com.example.finditgr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.finditgr.databinding.ActivityFaqBinding

class FAQActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Αρχικοποίηση ViewBinding για το layout της οθόνης FAQ
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Φορτώνουμε το κείμενο των Συχνών Ερωτήσεων από τα resources με μορφοποίηση HTML
        val faqHtml = getString(R.string.faq_text)
        binding.textViewFAQ.text = HtmlCompat.fromHtml(faqHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
