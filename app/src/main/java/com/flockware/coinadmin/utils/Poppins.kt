package com.flockware.coinadmin.utils

import android.content.Context
import android.graphics.Typeface

object Poppins {
    fun extrabold(context: Context) = Typeface.createFromAsset(context.assets, "poppins_extrabold.ttf")
    fun black(context: Context) = Typeface.createFromAsset(context.assets, "poppins_black.ttf")
    fun bold(context: Context) = Typeface.createFromAsset(context.assets, "poppins_bold.ttf")
    fun semibold(context: Context) = Typeface.createFromAsset(context.assets, "poppins_semibold.ttf")
    fun medium(context: Context) = Typeface.createFromAsset(context.assets, "poppins_medium.ttf")
    fun regular(context: Context) = Typeface.createFromAsset(context.assets, "poppins_regular.ttf")
    fun italic(context: Context) = Typeface.createFromAsset(context.assets, "poppins_italic.ttf")
    fun light(context: Context) = Typeface.createFromAsset(context.assets, "poppins_light.ttf")
    fun thin(context: Context) = Typeface.createFromAsset(context.assets, "poppins_thin.ttf")
}