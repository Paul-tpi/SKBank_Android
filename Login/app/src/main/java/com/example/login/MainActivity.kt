package com.example.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * 注意:請全程使用MVVM的架構，需要 ViewModel, Repository
         * 最重要的是可以Build出來，不論功能有沒有全部完成。
         */
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        AppSharedPreference.create(this)

        binding.btnLogin.setOnClickListener {
            /** 題目 1
             * 寫一個簡單登入流程，此流程不會需要呼叫API
             * 畫面已經建好EditText和Button, et_username, et_password, btn_login
             *
             * 按下登錄按鈕後(btn_login)，進行登入檢核
             * 登入檢核有下面的條件
             * 1. 帳號和密碼不能一樣
             * 2. 密碼需要英數混合，不用分大小寫
             * 3. 密碼最少要六個字
             * 登入成功後保存帳密到SharedPreferences並加密
             * 登入成功用Toast顯示<登入成功>文字，登入失敗Toast則顯示<登入失敗>文字
             * */
            viewModel.loginCheck(
                binding.etUsername.text.trim().toString(),
                binding.etPassword.text.trim().toString()
            )
            viewModel.loginResultLiveData?.observe(this, Observer {
                if (it) {
                    AppSharedPreference.getInstance().userName =
                        binding.etUsername.text.trim().toString()
                    AppSharedPreference.getInstance().pwd =
                        binding.etPassword.text.trim().toString()

                    Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.btnGetUserInfo.setOnClickListener {
            /**
             * 題目 2
             * 畫面上已經建好TextView和Button, btn_get_user_info, tv_user_info
             * 按下顯示用戶資料的按鈕後(btn_get_user_info)，使用下面的Github網址取得Github用戶資料
             * https://api.github.com/users
             * 把取得的第一筆資料顯示在tv_user_info裡面，顯示json格式就行了
             * */
            viewModel.getUsers()
            viewModel.postModelListLiveData?.observe(this, Observer {
                if (it != null) {
                    binding.tvUserInfo.text = it.first().toString()
                }
            })
        }
    }
}