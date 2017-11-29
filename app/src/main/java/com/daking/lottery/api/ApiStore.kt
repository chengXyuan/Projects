package com.daking.lottery.api

import com.daking.lottery.model.BankModel
import com.daking.lottery.model.Root
import com.daking.lottery.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiStore {

    /**
     * 注册
     */
    @POST("user/register")
    fun register(@Body params: HashMap<String, Any?>): Call<Root<UserModel>>

    /**
     * 登录
     */
    @POST("user/login")
    fun login(@Body params: HashMap<String, Any?>): Call<Root<UserModel>>

    /**
     * 试玩登录
     */
    @POST("user/textuserlogin")
    fun loginDemo(): Call<Root<UserModel>>

    /**
     * 获取用户信息
     */
    @POST("user/personalInformation")
    fun getUserInfo(@Body params: HashMap<String, Any?>): Call<Root<UserModel>>

    /**
     * 修改密码
     */
    @POST("user/updatepwd")
    fun updatePassword(@Body params: HashMap<String, Any?>): Call<Root<Unit>>

    /**
     * 修改取款密码
     */
    fun updatePayPassword(@Body params: HashMap<String, Any?>): Call<Root<Unit>>

    /**
     * 获取银行卡信息
     */
    @POST("userdeposit/userBank")
    fun getBankInfo(@Body params: HashMap<String, Any?>): Call<Root<BankModel>>
}