package com.example.tiptime

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.setOnClickListener{
            calculateTip()
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.audio)
        playMusic()
        pauseMusic()
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        if(cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        var tip = tipPercentage * cost
        val roundUp = binding.roundUpTip.isChecked

        if(roundUp) tip = ceil(tip)
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun playMusic() {
        binding.playSong.setOnClickListener {
            mediaPlayer.start()
        }
    }

    private fun pauseMusic() {
        binding.pauseSong.setOnClickListener {
            mediaPlayer.pause()
        }
    }
}