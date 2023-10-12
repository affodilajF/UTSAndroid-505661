package com.example.myapplicationuts

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.example.myapplicationuts.databinding.ActivityGetstartedBinding


class GetstartedActivity : AppCompatActivity(){
    private lateinit var binding: ActivityGetstartedBinding
    private lateinit var dietgoalarray : Array<String>
    private var dietdipilih = ""

    private lateinit var satuanbbarray : Array<String>
    private var satuanbbdipilih = ""

    private var selectedDate: String? = null

    companion object{
        const val EXTRA_USERNAME = "extra1"
        const val EXTRA_CURRENTWEIGHT = "extra2"
        const val EXTRA_TARGETWEIGHT = "extra3"
        const val EXTRA_WEIGHTUNIT = "extra4"
        const val EXTRA_DIETGOAL = "extra5"
        const val EXTRA_DAILYCALMAX = "extra6"
        const val EXTRA_TARGETDATE = "extra7"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetstartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

            datePicker.init(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth
            ){_, year, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth/${monthOfYear+1}/$year"
            }

            btnGetStarted.setOnClickListener(){
                val username = editTextName.text.toString()
                val currentWeight = txtEditCurrentw.text.toString()
                val targetWeight = txtEditTargetw.text.toString()
                val dietGoal = dietdipilih
                val dailyCalMax = editTextMaxcalori.text.toString()
                val weightUnit = satuanbbdipilih
                val targetDate = selectedDate

                if (username.isNotEmpty() && currentWeight.isNotEmpty() && targetWeight.isNotEmpty() &&
                    dietGoal != null && dailyCalMax.isNotEmpty() && weightUnit != null && targetDate != null) {
                    val intentToHomeActivity = Intent(this@GetstartedActivity, HomeActivity::class.java)
                        .apply {
                            putExtra(EXTRA_USERNAME, username)
                            putExtra(EXTRA_CURRENTWEIGHT, currentWeight)
                            putExtra(EXTRA_TARGETWEIGHT, targetWeight)
                            putExtra(EXTRA_DIETGOAL, dietGoal)
                            putExtra(EXTRA_DAILYCALMAX, dailyCalMax)
                            putExtra(EXTRA_WEIGHTUNIT, weightUnit)
                            putExtra(EXTRA_TARGETDATE, targetDate)
                        }
                    startActivity(intentToHomeActivity)
                    finish()
                } else {

                    Toast.makeText(this@GetstartedActivity, "Harap isi semua data dengan benar", Toast.LENGTH_SHORT).show()
                }
            }

            dietgoalarray = resources.getStringArray(R.array.dietgoal)
            val adapterDiet = ArrayAdapter(this@GetstartedActivity, android.R.layout.simple_spinner_item, dietgoalarray)
            adapterDiet.setDropDownViewResource(
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item
            )

            spinnerTujuandiet.adapter = adapterDiet
            spinnerTujuandiet.onItemSelectedListener=
                object  : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id : Long) {
                        dietdipilih = dietgoalarray[position]
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }


            satuanbbarray = resources.getStringArray(R.array.beratbadan)
            val adapterBB = ArrayAdapter(this@GetstartedActivity, android.R.layout.simple_spinner_item, satuanbbarray)
            adapterBB.setDropDownViewResource(
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item
            )

            spinnerWeightUnit.adapter = adapterBB
            spinnerWeightUnit.onItemSelectedListener=
                object  : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id : Long) {
                        satuanbbdipilih = satuanbbarray[position]
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }



        }
    }




}