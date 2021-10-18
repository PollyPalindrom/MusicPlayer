package com.example.musicplayer.res

import android.content.Context
import androidx.annotation.*
import androidx.core.content.ContextCompat
import javax.inject.Inject

class AppResourcesImpl @Inject constructor(
    private val context: Context
) : AppResources {

    private val resources = context.resources

    override fun getString(@StringRes stringId: Int): String {
        return resources.getString(stringId)
    }

    @Suppress("SpreadOperator")
    override fun getString(@StringRes stringId: Int, vararg formatArgs: Any?): String {
        return resources.getString(stringId, *formatArgs)
    }

    @ColorInt
    override fun getColor(@ColorRes colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    override fun getInteger(@IntegerRes resId: Int): Int {
        return resources.getInteger(resId)
    }

    override fun getStringByName(resourceName: String, @StringRes defaultStringRes: Int): String {
        val resId = getStringResIdByName(resourceName)
        return if (resId != 0) resources.getString(resId) else resources.getString(defaultStringRes)
    }

    override fun getStringByName(resourceName: String): String {
        return resources.getString(getStringResIdByName(resourceName))
    }

    override fun getStringByName(resourceName: String, defaultString: String): String {
        val resId = getStringResIdByName(resourceName)
        return if (resId != 0) resources.getString(resId) else defaultString
    }

    override fun getDimension(@DimenRes resId: Int): Float {
        return resources.getDimension(resId)
    }

    override fun getPlurals(@PluralsRes pluralsId: Int, quantity: Int): String {
        return resources.getQuantityString(pluralsId, quantity)
    }

    @StringRes
    override fun getStringResIdByName(resourceName: String, @StringRes defaultValue: Int): Int {
        return if (resourceName.isEmpty()) {
            defaultValue
        } else {
            val resId = resources.getIdentifier(resourceName, "string", context.packageName)
            if (resId != 0) resId else defaultValue
        }
    }

    @RawRes
    override fun getRawResIdByName(resourceName: String): Int {
        return resources.getIdentifier(resourceName, "raw", context.packageName)
    }
}