package com.example.hackillinoisandroidchallenge

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class EventAdapter(val events: List<Event>) : RecyclerView.Adapter<EventViewHolder>() {
    private val eventTypeToColor = mutableMapOf<String, Int>()
    private var nextColorIndex = 0
    val pastels = arrayOf(
        R.color.pastel_red,
        R.color.pastel_purple,
        R.color.pastel_green,
        R.color.pastel_turquoise,
        R.color.pastel_orange,
        R.color.pastel_blue,
        R.color.pastel_pink,
        R.color.pastel_brown,
        R.color.pastel_magenta,
        R.color.pastel_lavender,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.list_item_layout, parent, false)
        return EventViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        val context = holder.itemView.context

        val color = getColorForEventType(event.eventType, context)
        val darkenedColor = darkenColor(color, 0.85f)  // Darken by 20%

        val drawable = ContextCompat.getDrawable(context, R.drawable.circle_frame)
        if (drawable is GradientDrawable) {
            drawable.setColor(color)
            drawable.setStroke(20, darkenedColor)
        }

        val textView : TextView = holder.itemView.findViewById(R.id.textViewEventName)
        // val container : ConstraintLayout = holder.itemView.findViewById(R.id.eventContainer)
        // textView.maxWidth = container.width * 9/10
        val darkDrawable = ContextCompat.getDrawable(context, R.drawable.circle_frame_dark)
        if (darkDrawable is GradientDrawable) {
            darkDrawable.setColor(darkenedColor)
            textView.background = darkDrawable
        }

        holder.itemView.background = drawable
        holder.bindEvent(event)

        holder.itemView.setOnClickListener {
            if (event.locations.size > 0) {
                val location = event.locations[0]
                val dialog = EventLocationDialogFragment.newInstance(location.latitude, location.longitude, location.description)
                var fragmentManager = (holder.itemView.context as FragmentActivity).supportFragmentManager
                dialog.show(fragmentManager, "event_location_dialog")
            }
        }
    }

    private fun getColorForEventType(eventType: String, context: Context): Int {
        return eventTypeToColor.getOrPut(eventType) {
            val colorResource = pastels[nextColorIndex]
            val color = ContextCompat.getColor(context, colorResource)
            // Increment the nextColorIndex, wrap around if we reach the end of the array
            nextColorIndex = (nextColorIndex + 1) % pastels.size
            color
        }
    }

    private fun darkenColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt().coerceIn(0, 255)
        val g = (Color.green(color) * factor).roundToInt().coerceIn(0, 255)
        val b = (Color.blue(color) * factor).roundToInt().coerceIn(0, 255)
        return Color.argb(a, r, g, b)
    }
}