package com.daking.lottery.api

import com.daking.lottery.model.BankModel
import com.daking.lottery.model.Root
import com.daking.lottery.model.UserModel
import com.daking.lottery.model.VersionModel
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiStore {

    /**
     * 注册
     */
    @POST("user/register")
    fun register(@Body params: HashMap<String, Any?>): Flowable<Root<UserModel>>

    /**
     * 登录
     */
    @POST("user/login")
    fun login(@Body params: HashMap<String, Any?>): Flowable<Root<UserModel>>

    /**
     * 试玩(游客)登录
     */
    @POST("user/textuserlogin")
    fun visitorLogin(): Flowable<Root<UserModel>>

    /**
     * 获取用户信息
     */
    @POST("user/personalInformation")
    fun refreshAccount(@Body params: HashMap<String, Any?>): Call<Root<UserModel>>

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

    /**
     * 检查apk版本更新
     */
    @POST("android/version")
    fun checkUpdate(): Flowable<Root<VersionModel>>

    /**
     * 下载apk
     */
    @Streaming//注明为流文件，防止retrofit将大文件读入内存
    @GET
    fun download(@Url url: String): Flowable<ResponseBody>
}