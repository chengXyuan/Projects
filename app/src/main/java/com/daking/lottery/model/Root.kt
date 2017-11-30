package com.daking.lottery.model

data class Root<out T>(val httpCode: Int, val response: List<T>?) {

    fun getMessage() = when (httpCode) {
        200 -> "请求成功"
        201 -> "更新成功"
        500 -> "空指针异常"
        501 -> "授权失败"
        502 -> "参数错误"
        503 -> "更新失败"
        504 -> "SQL异常"
        505 -> "新增成功"
        506 -> "新增失败"
        507 -> "删除成功"
        508 -> "删除失败"
        509 -> "登出成功"
        510 -> "密码不匹配"
        511 -> "没有绑定银行卡"
        512 -> "旧密码错误"
        514 -> "获取数据失败"
        515 -> "登陆成功"
        516 -> "登陆失败"
        517 -> "用户余额不足"
        518 -> "系统内部错误"
        519 -> "银行卡号已经存在"
        520 -> "查找不到数据"
        521 -> "该账号已存在"
        523 -> "登陆失败,用户已在线"
        526 -> "投注成功"
        527 -> "投注失败"
        528 -> "添加成功"
        529 -> "添加失败"
        530 -> "提现成功"
        531 -> "提现失败"
        532 -> "注册成功"
        533 -> "注册失败"
        534 -> "账号可用"
        535 -> "sessionId不能为空"
        536 -> "usersId不能为空"
        537 -> "充值失败"
        538 -> "用户已被禁用"
        539 -> "游戏维护中"
        2001 -> "登陆失败,账号或密码错误"
        2002 -> "登陆失败,用户不存在"
        2004 -> "该账号已存在"
        2007 -> "支付密码错误"
        4001 -> "您的账号已失效，请重新登录"
        else -> ""
    }

}
