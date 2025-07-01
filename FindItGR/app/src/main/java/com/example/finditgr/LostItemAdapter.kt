package com.example.finditgr

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finditgr.data.LostItem

class LostItemAdapter(private var items: List<LostItem>) :
    RecyclerView.Adapter<LostItemAdapter.LostItemViewHolder>() {

    // ViewHolder για κάθε στοιχείο της λίστας LostItem
    class LostItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Σύνδεση με τα TextViews του layout item_lost.xml
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textLocation: TextView = itemView.findViewById(R.id.textLocation)
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val textFullName: TextView = itemView.findViewById(R.id.textFullName)  // Προστέθηκε για εμφάνιση ονόματος
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LostItemViewHolder {
        // Φουσκώνουμε (inflate) το layout για κάθε στοιχείο της λίστας
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lost, parent, false)
        return LostItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: LostItemViewHolder, position: Int) {
        // Γεμίζουμε τα πεδία του ViewHolder με τα δεδομένα του LostItem στη θέση position
        val item = items[position]
        holder.textTitle.text = item.title
        holder.textDescription.text = item.description
        holder.textLocation.text = item.location
        holder.textDate.text = item.date
        holder.textFullName.text = item.fullName  // Εμφάνιση ονόματος στο αντίστοιχο TextView

        // Όταν πατηθεί ένα στοιχείο της λίστας, ανοίγει το DetailActivity με το αντικείμενο LostItem
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("lostItem", item)  // Το LostItem είναι Parcelable, άρα περνάει εύκολα
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size  // Επιστρέφει πόσα στοιχεία έχει η λίστα

    // Ενημέρωση της λίστας και ανανέωση του RecyclerView
    fun updateItems(newItems: List<LostItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
