package com.example.hackillinoisandroidchallenge

// I input the example data from https://hackillinois.github.io/adonix-api-docs/#api-Event-Event
// into https://app.quicktype.io/ to generate this data class!

data class Events (
    val events: List<Event>
)

data class Event (
    val id: String,
    val name: String,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val locations: List<Location>,
    val sponsor: String,
    val eventType: String
)

data class Location (
    val description: String,
    val tags: List<String>,
    val latitude: Double,
    val longitude: Double
)
