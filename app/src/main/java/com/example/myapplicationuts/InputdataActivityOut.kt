package com.example.myapplicationuts

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplicationuts.databinding.ActivityInputdataOutBinding
import com.example.myapplicationuts.databinding.ActivityInputdatainBinding

class InputdataActivityOut : AppCompatActivity() {

    private lateinit var binding : ActivityInputdataOutBinding

    private var selectedTime: String? = null
    private var selectedTime2: String? = null

    private var selectedTime1Minutes: Int = 0
    private var selectedTime2Minutes: Int = 0

    private var durasi = ""


    companion object{
        const val EXTRA_WORKOUTNAME = "extra1"
        const val EXTRA_WORKOUTSTARTED = "extra2"
        const val EXTRA_WORKOUTDURATION = "extra3"
        const val EXTRA_CALLMIN = "extra4"
        const val EXTRA_WORKOUTENDED = "extra5"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputdataOutBinding.inflate(layoutInflater)
        setContentView(binding.root)



        with(binding){


            // Set up TimePicker1
            timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                selectedTime1Minutes = hourOfDay * 60 + minute
                updateTimeDifference()
            }

            // Set up TimePicker2
            timePicker2.setOnTimeChangedListener { view, hourOfDay, minute ->
                selectedTime2 = String.format("%02d:%02d", hourOfDay, minute)
                selectedTime2Minutes = hourOfDay * 60 + minute
                updateTimeDifference()
            }

            btnBackOut.setOnClickListener(){
                finish()
            }
            btnDoneOut.setOnClickListener(){

                val workoutName = editTextWorkoutName.text.toString()
                val calMin = editTextCal.text.toString()
                val workoutDuration = durasi
                val workoutStarted = selectedTime
                val workoutEnded = selectedTime2

                if (workoutName.isNotEmpty() && calMin.isNotEmpty() && workoutDuration != null && workoutStarted != null && workoutEnded != null) {
                    intent.putExtra(EXTRA_WORKOUTNAME, workoutName)
                    intent.putExtra(EXTRA_CALLMIN, calMin)
                    intent.putExtra(EXTRA_WORKOUTDURATION, workoutDuration)
                    intent.putExtra(EXTRA_WORKOUTSTARTED, workoutStarted)
                    intent.putExtra(EXTRA_WORKOUTENDED, workoutEnded)

                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this@InputdataActivityOut, "Isi data dulu!", Toast.LENGTH_SHORT).show()
                }


            }
        }


    }

    fun updateTimeDifference() {
        val timeDifferenceMinutes = selectedTime2Minutes - selectedTime1Minutes

        if (timeDifferenceMinutes < 0) {

        } else {
            val hours = timeDifferenceMinutes / 60
            val minutes = timeDifferenceMinutes % 60
            durasi = "$hours h $minutes m"
        }
    }
}