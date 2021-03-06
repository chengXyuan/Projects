package com.daking.lottery.api

import com.daking.lottery.model.*
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiStore {

    /**
     * 注册
     */
    @POST("user/register")
    fun register(@Body params: HashMap<String, Any?>): Flowable<Root<Unit>>

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
    fun refreshAccount(@Body params: HashMap<String, Any?>): Flowable<Root<UserModel>>

    /**
     * 修改密码
     */
    @POST("user/updatepwd")
    fun updatePassword(@Body params: HashMap<String, Any?>): Flowable<Root<Unit>>

    /**
     * 修改取款密码
     */
    @POST("user/updateplypwd")
    fun updatePayPassword(@Body params: HashMap<String, Any?>): Flowable<Root<Unit>>

    /**
     * 获取银行卡信息
     */
    @POST("userdeposit/userBank")
    fun getBankInfo(@Body params: HashMap<String, Any?>): Flowable<Root<BankModel>>

    /**
     * 添加/修改银行卡
     */
    @POST("userdeposit/bindBank")
    fun bindOrModifyBankcard(@Body params: HashMap<String, Any?>): Flowable<Root<Unit>>

    /**
     * 获取银行卡信息
     */
    @POST("userdeposit/withdrawAmount")
    fun withdraw(@Body params: HashMap<String, Any?>): Flowable<Root<Unit>>

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

    /**
     * 获取轮播图和客服链接
     */
    @POST("imageupload/carousel")
    fun getBanner(): Flowable<Root<BannerModel>>

    /**
     * 获取支持的支付方式
     */
    @POST("onlinePay/payType")
    fun getPayWays(@Body params: HashMap<String, Any?>): Flowable<Root<PayWaysModel>>

    /**
     * 在线支付
     */
    @POST("onlinePay/pay")
    fun onlinePayment(@Body params: HashMap<String, Any?>): Flowable<Root<OnlinePayUrl>>

    /**
     * 线下支付
     */
    @POST("offlinePay/TransactionRecord")
    fun offlinePayment(@Body params: HashMap<String, Any?>): Flowable<Root<Unit>>

    /**
     * 存取款记录
     */
    @POST("userdeposit/withdrawalsAndSaveDetail")
    fun getMoneyRecord(@Body params: HashMap<String, Any?>): Flowable<Root<FundingModel>>

    /**
     * 开奖历史
     */
    @POST("lotterOpen/openselet")
    fun getLotteryHistory(@Body params: HashMap<String, Any?>): Flowable<Root<OpenModel>>

    /**
     * 期数查询接口(下注页面获取开奖信息)
     */
    @POST("lotterOpen/getinfo/periods")
    fun getLotteryInfo(@Body params: HashMap<String, Any?>): Flowable<Root<LotteryInfo>>

    /**
     * 获取赔率
     */
    @POST("betting/bettingQuery")
    fun getGameOdds(@Body params: HashMap<String, Any?>): Flowable<Root<OddsModel>>

    /**
     * 下注
     */
    @POST("betting/betting")
    fun betting(@Body body: BetRequest): Flowable<Root<Unit>>

    /**
     * 汇总
     */
    @POST("lotterybill/summary")
    fun getAllBetRecord(@Body params: HashMap<String, Any?>): Flowable<Root<RecordSummary>>

    /**
     * 今日已结
     */
    @POST("lotterybill/todayend")
    fun getSettledOrders(@Body params: HashMap<String, Any?>): Flowable<Root<RecordDetail>>

    /**
     * 未结注单
     */
    @POST("lotterybill/findlotteyBill")
    fun getUnsettledOrders(@Body params: HashMap<String, Any?>): Flowable<Root<RecordDetail>>

    /**
     * 最新消息
     */
    @POST("announcement/newmessage")
    fun getNewMessage(@Body params: HashMap<String, Any?>): Flowable<Root<MsgModel>>

    /**
     * 所有消息
     */
    @POST("announcement/findall")
    fun getAllMessage(@Body params: HashMap<String, Any?>): Flowable<Root<MsgModel>>

    /**
     * 消息详情
     */
    @POST("announcement/details")
    fun getMessageDetail(@Body params: HashMap<String, Any?>): Flowable<Root<MsgModel>>

    /**
     * 优惠活动
     */
    @POST("activityUrl/activityQuery")
    fun getPromotions(@Body params: HashMap<String, Any?>): Flowable<Root<Promotion>>
}