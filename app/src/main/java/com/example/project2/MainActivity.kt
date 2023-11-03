package com.example.project2
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button
    private lateinit var timerTextView: TextView

    private var running: Boolean = false
    private var elapsedTime: Long = 0L
    private var startTime: Long = 0L

    private val handler = Handler()

    private val updateTimeTask = object : Runnable {
        override fun run() {
            val currentTime = SystemClock.uptimeMillis()
            val updatedTime = elapsedTime + currentTime - startTime
            val seconds = (updatedTime / 1000).toInt()
            val minutes = seconds / 60
            val hours = minutes / 60
            val formattedTime = String.format("%02d:%02d:%02d", hours % 24, minutes % 60, seconds % 60)

            timerTextView.text = formattedTime

            handler.postDelayed(this, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)
        timerTextView = findViewById(R.id.timerTextView)

        startButton.setOnClickListener {
            start()
        }

        pauseButton.setOnClickListener {
            pause()
        }

        resetButton.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        if (!running) {
            startTime = SystemClock.uptimeMillis()
            handler.postDelayed(updateTimeTask, 0)
            running = true
        }
    }

    private fun pause() {
        if (running) {
            elapsedTime += SystemClock.uptimeMillis() - startTime
            handler.removeCallbacks(updateTimeTask)
            running = false
        }
    }

    private fun reset() {
        if (!running) {
            elapsedTime = 0L
            timerTextView.text = "00:00:00"
        }
    }
}
