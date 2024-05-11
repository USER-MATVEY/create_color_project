package com.dobriyanovmp.create_color_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dobriyanovmp.create_color_app.databinding.ActivityMainBinding
import com.dobriyanovmp.create_color_app.databinding.AlertLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.CreateButton.setOnClickListener {
            showAlterDialog()
        }
    }

    private fun showAlterDialog() {
        val dialogBinding = AlertLayoutBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle("Creating color")
            .setMessage("Create color, please!")
            .setView(dialogBinding.root)
            .setPositiveButton("Create") {
                _, _ -> createColor()
            }
            .create()
        dialog.show()
    }

    private fun createColor() {

    }
}