package hr.trailovic.notesqkeeper.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Note(
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="body") var body: String,
    @PrimaryKey var id: Long = System.currentTimeMillis(),
): Parcelable