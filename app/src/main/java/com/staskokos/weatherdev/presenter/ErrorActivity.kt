package com.staskokos.weatherdev.presenter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.staskokos.weatherdev.databinding.ActivityErrorBinding

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra("message")
        val cause = intent.getStringExtra("cause")
        val stackTrace = intent.getStringExtra("stackTrace")

        binding.errorTextView.text = "Message: ${message}\n\nCause: ${cause}\n\nStacktrace: ${stackTrace}"

        binding.errorTextView.setOnLongClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", binding.errorTextView.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Text copied", Toast.LENGTH_SHORT).show()
            true
        }
    }
}