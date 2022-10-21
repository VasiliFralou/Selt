package by.vfdev.selt.UI

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.R
import by.vfdev.selt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val binding by viewBinding(ActivityLoginBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}