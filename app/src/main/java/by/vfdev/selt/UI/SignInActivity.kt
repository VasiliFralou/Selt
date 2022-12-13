package by.vfdev.selt.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.R
import by.vfdev.selt.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity(R.layout.activity_sign_in) {

    private val binding by viewBinding(ActivitySignInBinding::bind)

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnIn.setOnClickListener {
            login()
        }

        binding.SignUpTV.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {

        val email = binding.emailSignInEditText.text.toString()
        val password = binding.passwordSignInEditText.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Ошибка авторизации! Неверно указан логин или пароль", Toast.LENGTH_LONG).show()
            }
    }
}