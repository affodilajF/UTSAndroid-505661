package com.example.myapplicationuts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplicationuts.databinding.ActivityWellcomeBinding

class WellcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWellcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWellcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            buttonGetStarted.setOnClickListener(){
                val intentToGetStartedActivity = Intent(this@WellcomeActivity, GetstartedActivity::class.java)
                startActivity(intentToGetStartedActivity)
                finish()

            }
        }

    }
}
