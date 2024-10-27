package com.knulphe.todomobile.data.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ServerTimestamp
import com.google.type.DateTime
import java.util.Date

@Keep
data class Task constructor(
    var taskId: String = "",
    var task: String = "",
    @ServerTimestamp
    var createdAt: Date = Date()
) {

    var completedAt: Date? = null
    var isCompleted: Boolean = false
}