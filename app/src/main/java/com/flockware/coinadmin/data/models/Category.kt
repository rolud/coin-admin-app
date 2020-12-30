package com.flockware.coinadmin.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val name: String,
        val color: String?
) : Parcelable