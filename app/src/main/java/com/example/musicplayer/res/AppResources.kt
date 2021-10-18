package com.example.musicplayer.res

import androidx.annotation.*

interface AppResources {

    fun getColor(@ColorRes colorId: Int): Int

    fun getDimension(@DimenRes resId: Int): Float

    fun getInteger(@IntegerRes resId: Int): Int

    fun getString(@StringRes stringId: Int): String

    fun getString(@StringRes stringId: Int, vararg formatArgs: Any?): String

    fun getPlurals(@PluralsRes pluralsId: Int, quantity: Int): String

    fun getStringByName(resourceName: String): String

    fun getStringByName(resourceName: String, @StringRes defaultStringRes: Int): String

    fun getStringByName(resourceName: String, defaultString: String): String

    @StringRes
    fun getStringResIdByName(resourceName: String, @StringRes defaultValue: Int = 0): Int

    @RawRes
    fun getRawResIdByName(resourceName: String): Int
}