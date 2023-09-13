package com.example.hackillinoisandroidchallenge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventLocationDialogFragment : DialogFragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var locationDescription: String

    companion object {
        fun newInstance(latitude: Double, longitude: Double, locationDescription: String): EventLocationDialogFragment {
            val args = Bundle()
            args.putDouble("latitude", latitude)
            args.putDouble("longitude", longitude)
            args.putString("locationDescription", locationDescription)
            val fragment = EventLocationDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble("latitude")
            longitude = it.getDouble("longitude")
            locationDescription = it.getString("locationDescription")!!
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_event_location, null)
        val customTitle = inflater.inflate(R.layout.custom_title, null) as TextView
        customTitle.text = locationDescription

        builder.setView(view)
            .setCustomTitle(customTitle)
            .setPositiveButton("Close") { _, _ -> dismiss() }

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction().add(R.id.map_container, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val location = LatLng(latitude, longitude)
        map.addMarker(MarkerOptions().position(location).title(locationDescription))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}
