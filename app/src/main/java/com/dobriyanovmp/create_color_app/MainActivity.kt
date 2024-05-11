package com.dobriyanovmp.create_color_app

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dobriyanovmp.create_color_app.databinding.ActivityMainBinding
import com.dobriyanovmp.create_color_app.databinding.AlertLayoutBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var state: activityState

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

        state = savedInstanceState?.getParcelable("activityState")?: activityState(
            viewColor = Color.rgb(255,255,255)
        )

        binding.ColorView.setBackgroundColor(state.viewColor)

        binding.CreateButton.setOnClickListener {
            showAlterDialog()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("activityState", state)

    }

    private fun showAlterDialog() {
        val dialogBinding = AlertLayoutBinding.inflate(layoutInflater)

        val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (seekBar?.id) {
                    R.id.RedProgress -> {
                        dialogBinding.RedInput.setText(
                            dialogBinding.RedProgress.progress.toString())
                    }
                    R.id.GreenProgress -> {
                        dialogBinding.GreenInput.setText(
                            dialogBinding.GreenProgress.progress.toString()
                        )
                    }
                    R.id.BlueProgress -> {
                        dialogBinding.BlueInput.setText(
                            dialogBinding.BlueProgress.progress.toString()
                        )
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
        }

        fun textListener(editSeekbar: SeekBar): TextWatcher {
            return object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    editSeekbar.progress =
                        s?.toString()?.toIntOrNull()?.coerceIn(0, editSeekbar.max) ?: 0
                }

                override fun afterTextChanged(s: Editable?) { }
            }
        }

        dialogBinding.RedInput.addTextChangedListener(textListener(dialogBinding.RedProgress))
        dialogBinding.GreenInput.addTextChangedListener(textListener(dialogBinding.GreenProgress))
        dialogBinding.BlueInput.addTextChangedListener(textListener(dialogBinding.BlueProgress))

        dialogBinding.RedProgress.setOnSeekBarChangeListener(seekBarChangeListener)
        dialogBinding.GreenProgress.setOnSeekBarChangeListener(seekBarChangeListener)
        dialogBinding.BlueProgress.setOnSeekBarChangeListener(seekBarChangeListener)

        val dialog = AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle("Creating color")
            .setMessage("Create color, please!")
            .setView(dialogBinding.root)
            .setPositiveButton("Create") {
                _, _ ->
                run {
                    val color = Color.rgb(
                        dialogBinding.RedProgress.progress,
                        dialogBinding.GreenProgress.progress,
                        dialogBinding.BlueProgress.progress
                    )
                    binding.ColorView.setBackgroundColor(color)
                    state.viewColor = color
                }
            }
            .create()
        dialog.show()
    }
}