package com.daking.lottery.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.daking.lottery.R
import com.daking.lottery.widget.PasswordView
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        passwordView.setPasswordListener(object : PasswordView.PasswordListener {
            override fun passwordComplete(password: String) {
                tvPassword.text = password
            }
        })
    }
}
