package com.example.hackillinoisandroidchallenge

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* Create a schedule page that displays all event details for the hackathon!
 * Make a GET API call to the HackIllinois API event endpoint
 * Recommended: use Retrofit to create HTTP requests
 * Remember to add the libraries you want to use to your build.gradle file!
*/
val TAG = "Callback"
lateinit var mRecyclerView: RecyclerView
lateinit var mEventAdapter: EventAdapter
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler_view)

        NetworkService.hackApi.getEvents().enqueue(object :
            Callback<Events> {
                override fun onResponse(call: Call<Events>, response: Response<Events>) {
                    if (response.isSuccessful) {
                        val events = response.body()
                        Log.d(TAG, "Successfully retrieved events!")
                        Log.d(TAG, "Events: $events")
                        if (events != null) {
                            mEventAdapter = EventAdapter(events.events)
                            mRecyclerView.adapter = mEventAdapter
                            mEventAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.e(TAG, "Failed to get events: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Events>, t: Throwable) {
                    Log.e("Callback", "Failed to get events: ${t.message}")
                }
            }
        )
    }
}
