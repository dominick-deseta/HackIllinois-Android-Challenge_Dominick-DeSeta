package com.example.hackillinoisandroidchallenge

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class EventViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    private lateinit var currentEvent: Event
    private val eventNameTextView: TextView = itemView.findViewById(R.id.textViewEventName)
    private val eventDescriptionTextView: TextView = itemView.findViewById(R.id.textViewEventDescription)
    private val eventTypeTextView: TextView = itemView.findViewById(R.id.textViewEventType)
    private val eventTimeTextView: TextView = itemView.findViewById(R.id.textViewEventTime)

    fun bindEvent(event: Event) {
        eventNameTextView.text = event.name
        eventDescriptionTextView.text = event.description
        eventTypeTextView.text = event.eventType
        if (event.startTime == event.endTime) {
            eventTimeTextView.text = convertUnixToTime(event.startTime)
        } else {
            eventTimeTextView.text = itemView.context.getString(
                R.string.eventTime,
                convertUnixToTime(event.startTime),
                convertUnixToTime(event.endTime)
            )
        }

    }

    fun convertUnixToTime(unixSeconds: Long): String {
        var date = Date(unixSeconds * 1000L)
        val sdf = SimpleDateFormat("h:mm a")
        sdf.timeZone = TimeZone.getTimeZone("CST")
        // not sure why I have to set the time 6 hours back...
        date = Date(date.time - 6 * 60 * 60 * 1000)
        return sdf.format(date)
    }
}