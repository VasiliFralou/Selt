package by.vfdev.selt.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.R
import by.vfdev.selt.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySignUpBinding::bind)
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        binding.btnUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {

        val email = binding.emailSignUpEditText.text.toString()
        val password = binding.passwordSignUpEditText.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Ошибка регистрации!", Toast.LENGTH_LONG).show()
            }
    }
}