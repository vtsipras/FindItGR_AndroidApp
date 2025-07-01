package com.example.finditgr.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Αντικείμενο που αναπαριστά μια αγγελία (χαμένο ή βρεθέν αντικείμενο)
// Parcelable για να μπορούμε να το στέλνουμε εύκολα μεταξύ Activities
@Parcelize
@Entity(tableName = "lost_items")
data class LostItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,          // Μοναδικό ID της αγγελίας, αυτόματη αύξηση
    val title: String,        // Τίτλος αγγελίας (π.χ. "Χάθηκε πορτοφόλι")
    val description: String,  // Περιγραφή του αντικειμένου ή περιστατικού
    val location: String,     // Τοποθεσία που χάθηκε ή βρέθηκε
    val date: String,         // Ημερομηνία που χάθηκε/βρέθηκε
    val fullName: String,     // Όνομα ατόμου που έκανε την αγγελία
    val phone: String,        // Τηλέφωνο επικοινωνίας
    val category: String      // Κατηγορία ("Χάθηκε" ή "Βρέθηκε")
) : Parcelable
