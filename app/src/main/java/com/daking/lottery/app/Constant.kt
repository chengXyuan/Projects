package com.daking.lottery.app

interface Constant {
    companion object {
        val REMEMBER_ACCOUNT = "remember_account"
        val PASSWORD = "password"

        val REGEX_USERNAME = "^[a-zA-Z0-9]{6,15}$"
        val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,15}$"
        val REGEX_REAL_NAME = "^[\\u4e00-\\u9fa5]{2,8}$"
    }
}