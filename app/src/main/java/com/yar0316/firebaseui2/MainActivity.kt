package com.yar0316.firebaseui2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        //ログインユーザのチェック
        chkUser()

        signOutButton.setOnClickListener { signOut() }

    }

    /**
     * すでにログインしているユーザがいるかどうかを確認する
     * いなかった場合、ログイン用のアクティビティに移動
     */
    private fun chkUser() {
        //ログイン済みか
        val isSigned = ( mAuth.currentUser != null)

        //ログインしているユーザがいなければログイン画面に遷移
        if (!isSigned){
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()
                , RC_SIGN_IN
            )
        }
    }

    /**
     * ログアウトしてサインアウト後の画面へ移動
     */
    private fun signOut() {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    val intent = Intent(this, SignedOutActivity::class.java)
                    startActivity(intent)
                }
    }
    companion object{
        const val RC_SIGN_IN = 9001
    }

}
