package com.example.myapplicationuts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplicationuts.GetstartedActivity.Companion.EXTRA_CURRENTWEIGHT
import com.example.myapplicationuts.GetstartedActivity.Companion.EXTRA_DAILYCALMAX
import com.example.myapplicationuts.GetstartedActivity.Companion.EXTRA_DIETGOAL
import com.example.myapplicationuts.GetstartedActivity.Companion.EXTRA_TARGETDATE
import com.example.myapplicationuts.GetstartedActivity.Companion.EXTRA_TARGETWEIGHT
import com.example.myapplicationuts.GetstartedActivity.Companion.EXTRA_USERNAME
import com.example.myapplicationuts.GetstartedActivity.Companion.EXTRA_WEIGHTUNIT
import com.example.myapplicationuts.InputdataActivityIn.Companion.EXTRA_CALORIEAMOUNT
import com.example.myapplicationuts.InputdataActivityIn.Companion.EXTRA_CALORIETYPE
import com.example.myapplicationuts.InputdataActivityIn.Companion.EXTRA_FOODNAME
import com.example.myapplicationuts.InputdataActivityIn.Companion.EXTRA_WAKTU
import com.example.myapplicationuts.InputdataActivityOut.Companion.EXTRA_CALLMIN
import com.example.myapplicationuts.InputdataActivityOut.Companion.EXTRA_WORKOUTDURATION
import com.example.myapplicationuts.InputdataActivityOut.Companion.EXTRA_WORKOUTENDED
import com.example.myapplicationuts.InputdataActivityOut.Companion.EXTRA_WORKOUTNAME
import com.example.myapplicationuts.InputdataActivityOut.Companion.EXTRA_WORKOUTSTARTED
import com.example.myapplicationuts.databinding.ActivityHomeBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale


class HomeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHomeBinding
    var cardtodisplay = 1
    private var kalorisekarang = 0.0
    private var kaloritarget = 0.0
    private var totalkaloritarget = 0.0


//    LAUNCHER INPUT CALORIE IN
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data

                val foodname = data?.getStringExtra(EXTRA_FOODNAME)
                val tipekalori = data?.getStringExtra(EXTRA_CALORIETYPE)
                val waktu = data?.getStringExtra(EXTRA_WAKTU)
                val calorieamount = data?.getStringExtra(EXTRA_CALORIEAMOUNT)

                showCard(foodname, tipekalori, waktu, calorieamount, "IN")

//                ngubah informasi dashboard
                with(binding){
                    kalorisekarang += calorieamount?.toDouble()!!
                    textView.text = kalorisekarang.toString()
                }

                hitungprogress(kalorisekarang, "IN")
                if (calorieamount != null) {
                    hitungremainingcal(calorieamount.toDouble()!!, "IN")
                }

            }
        }
    //    LAUNCHER INPUT CALORIE OUT
    private val launcher2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data

                val workoutname = data?.getStringExtra(EXTRA_WORKOUTNAME)
                val workoutstarted = data?.getStringExtra(EXTRA_WORKOUTSTARTED)
                val woduration = data?.getStringExtra(EXTRA_WORKOUTDURATION)
                val calorieamount = data?.getStringExtra(EXTRA_CALLMIN)
                val workoutended = data?.getStringExtra(EXTRA_WORKOUTENDED)

                val wojamberapa = "$workoutstarted - $workoutended"

                showCard(workoutname, wojamberapa, woduration, calorieamount, "OUT")

//                ngubah informasi dashboard
                with(binding){
                    kalorisekarang -= calorieamount?.toDouble()!!
                    textView.text = kalorisekarang.toString()
                }
                hitungprogress(kalorisekarang, "OUT")
                if (calorieamount != null) {
                    hitungremainingcal(calorieamount.toDouble()!!, "OUT")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

//        tanggal hari ini
        val calendar = Calendar.getInstance()
        // Mendapatkan tanggal saat ini
        val tahun = calendar.get(Calendar.YEAR)
        val bulan = calendar.get(Calendar.MONTH) + 1 // Ingat bahwa bulan dimulai dari 0
        val tanggal = calendar.get(Calendar.DAY_OF_MONTH)


        val hariSekarang = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        var tanggalSekarang = "$hariSekarang, $tahun-$bulan-$tanggal"


        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

            txtViewDateNow.text = tanggalSekarang

            val cweight = intent.getStringExtra(EXTRA_CURRENTWEIGHT)
            val tweight = intent.getStringExtra(EXTRA_TARGETWEIGHT)
            val name = intent.getStringExtra(EXTRA_USERNAME)
            val satuanbb = intent.getStringExtra(EXTRA_WEIGHTUNIT)
            val targetdate = intent.getStringExtra(EXTRA_TARGETDATE)

            txtUsername.text = "Hello $name"
            txtCweight.text = "Your Current Weight Is $cweight $satuanbb"
            txtTweight.text = "Your Target Weight Is $tweight $satuanbb "
            txtDatetarget.text = "Your Target Date Is $targetdate"


            val dietgoal = intent.getStringExtra(EXTRA_DIETGOAL)
            txtDietgoal.text = "Your Diet Target is $dietgoal"

            var maxcall = intent.getStringExtra(EXTRA_DAILYCALMAX).toString()
            textView2.text = "of $maxcall"
            txtRemainingCall.text = "$maxcall"
            totalkaloritarget = maxcall.toDouble()
            kaloritarget = maxcall.toDouble()

            btnCalIn.setOnClickListener(){
                val intentToInActivity =
                    Intent(this@HomeActivity, InputdataActivityIn::class.java)
                launcher.launch(intentToInActivity)
            }

            btnCalOut.setOnClickListener(){
                val intentToOutActivity =
                    Intent(this@HomeActivity, InputdataActivityOut::class.java)
                launcher2.launch(intentToOutActivity)

            }
        }
    }



    private fun hitungremainingcal(argA: Double, inout: String){
        with(binding){

            if(inout == "IN"){
                kaloritarget = kaloritarget - argA
                txtRemainingCall.text = kaloritarget.toString()

            } else if(inout == "OUT") {
                kaloritarget = kaloritarget + argA
                txtRemainingCall.text = kaloritarget.toString()
            }
        }

    }

    private fun hitungprogress(argA: Double, inout: String){
        var progresnilai: Double = (argA/totalkaloritarget * 100)

        var circularProgressIndicator = findViewById<CircularProgressIndicator>(R.id.progress_circular_indicator)
        circularProgressIndicator.progress = progresnilai.toInt()
    }

    fun showCard(argA: String?, argB: String?, argC: String?, argD: String?, inout: String){
        with(binding) {

            var cardView: View? = null
            var argumenA: TextView? = null
            var argumenB: TextView? = null
            var argumenC: TextView? = null
            var argumenD: TextView? = null

            when (cardtodisplay) {
                1 -> {
                    cardView = card1
                    argumenA = idArg1a
                    argumenB = idArg1b
                    argumenC = idArg1c
                    argumenD = idArg1d
                }
                2 -> {
                    cardView = card2
                    argumenA = idArg2a
                    argumenB = idArg2b
                    argumenC = idArg2c
                    argumenD = idArg2d
                }
                3 -> {
                    cardView = card3
                    argumenA = idArg3a
                    argumenB = idArg3b
                    argumenC = idArg3c
                    argumenD = idArg3d
                }
                4 -> {
                    cardView = card4
                    argumenA = idArg4a
                    argumenB = idArg4b
                    argumenC = idArg4c
                    argumenD = idArg4d
                }
                else -> {}
            }

//            MEMBBERI NILAI SETIAP PROPERTI DIDALAM CARDVIEW DAN MERUBAH WARNA CARD SESUAI KALORI IN/OUT
            if (cardView != null) {
                cardView.visibility = View.VISIBLE
                if (argumenA != null && argumenB != null && argumenC != null && argumenD != null) {
                    argumenA.text = argA
                    argumenB.text = argB
                    argumenC.text = argC
                }
                cardtodisplay += 1

                if(inout == "IN"){
                    cardView.setBackgroundResource(R.drawable.cardstylein)
                    argumenD?.text = "+ $argD"
                } else {
                    cardView.setBackgroundResource(R.drawable.cardstyleout)
                    argumenD?.text = "- $argD"
                }
            }
        }

    }
}