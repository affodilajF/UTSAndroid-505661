package com.example.myapplicationuts

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myapplicationuts.databinding.ActivityHomeBinding
import com.example.myapplicationuts.databinding.ActivityInputdatainBinding

class InputdataActivityIn : AppCompatActivity() {
    private lateinit var binding : ActivityInputdatainBinding

    private lateinit var tipekaloriarray : Array<String>
    private var tipekaloridipilih = ""
    private var selectedTime: String? = null

    companion object{
        const val EXTRA_FOODNAME = "extra1"
        const val EXTRA_CALORIEAMOUNT = "extra2"
        const val EXTRA_CALORIETYPE = "extra3"
        const val EXTRA_WAKTU = "extra4"
        const val EXTRA_TANGGAL = "extra4"
        const val EXTRA_INOROUT = "extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputdatainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            tipekaloriarray = resources.getStringArray(R.array.tipecaloriin)
            val adapterTipeKalori = ArrayAdapter(this@InputdataActivityIn, android.R.layout.simple_spinner_item, tipekaloriarray)
            adapterTipeKalori.setDropDownViewResource(
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item
            )

            spinnerTipeKalori.adapter = adapterTipeKalori
            spinnerTipeKalori.onItemSelectedListener=
                object  : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id : Long) {
                        tipekaloridipilih = tipekaloriarray[position]
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }

            timePicker.setOnTimeChangedListener{view, hourOfDay, minute ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            }

            btnBackIn.setOnClickListener(){
                finish()
            }

            btnDoneIn.setOnClickListener(){

                val foodName = editTextFoodName.text.toString()
                val calorieAmount = editTextCal.text.toString()
                val waktu = selectedTime
                val selectedCalorieType = tipekaloridipilih

                if (foodName.isNotEmpty() && calorieAmount.isNotEmpty() && waktu != null && selectedCalorieType != null) {
                    intent.putExtra(EXTRA_FOODNAME, foodName)
                    intent.putExtra(EXTRA_CALORIEAMOUNT, calorieAmount)
                    intent.putExtra(EXTRA_CALORIETYPE, selectedCalorieType)
                    intent.putExtra(EXTRA_WAKTU, waktu)

                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this@InputdataActivityIn, "Isi dulu datanya!", Toast.LENGTH_SHORT).show()

                }


            }
        }

    }
}