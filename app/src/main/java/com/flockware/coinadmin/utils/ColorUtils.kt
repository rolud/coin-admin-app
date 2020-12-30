package com.flockware.coinadmin.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.flockware.coinadmin.R

object ColorUtils {
    @ColorInt const val TRASPARENT: Int = 0x00ffffff.toInt()

    @ColorInt const val WHITE: Int = 0xffffffff.toInt()
    @ColorInt const val CHIARO_DI_LUNA : Int = 0xfff6f6f6.toInt()

    @ColorInt const val BLACK       : Int = 0xff000000.toInt()
    @ColorInt const val CARBONE     : Int = 0xff121212.toInt()
    @ColorInt const val LAVA        : Int = 0xff222222.toInt()
    @ColorInt const val RISO_VENERE : Int = 0xff2c2c2c.toInt()
    @ColorInt const val NERO_INCUBO : Int = 0xff414042.toInt()

    @ColorInt const val SHADOW      : Int = 0xff616161.toInt()
    @ColorInt const val GRIGIO_FUMO : Int = 0xff898989.toInt()
    @ColorInt const val ROCCIA      : Int = 0xffababab.toInt()
    @ColorInt const val ARGENTO     : Int = 0xffd1d1d1.toInt()
    @ColorInt const val PERLA       : Int = 0xffe6e6e6.toInt()

    @ColorInt const val MANDARINO       : Int = 0xffff9d64.toInt()
    @ColorInt const val MANDARINO_DARK  : Int = 0xffff8d4b.toInt()
    @ColorInt const val MANDARINO_LIGHT : Int = 0xffffc29c.toInt()
    @ColorInt const val MANDARINO_MOON  : Int = 0xffffe0ce.toInt()

    @ColorInt const val DARTH_MAUL       : Int = 0xffc32328.toInt()
    @ColorInt const val DARTH_MAUL_LIGHT : Int = 0xffdf646a.toInt()
    @ColorInt const val DARTH_MAUL_MOON  : Int = 0xffeeacb0.toInt()

    @ColorInt const val LEMON_TREE      : Int = 0xffffc70d.toInt()
    @ColorInt const val LEMON_TREE_LIGHT : Int = 0xffffc70d.toInt()

    @ColorInt const val BROCCOLO       : Int = 0xff3bb57d.toInt()
    @ColorInt const val BROCCOLO_LIGHT : Int = 0xff9cdabd.toInt()

    @ColorInt const val BLU_PROFONDO : Int = 0xff040187.toInt()
    @ColorInt const val BLU_NOTTE    : Int = 0xff16155a.toInt()
    @ColorInt const val BLU_OCEANO   : Int = 0xff2647bf.toInt()
    @ColorInt const val BLU_LEGGERO  : Int = 0xff04b5e0.toInt()
    @ColorInt const val BLU_CHIARO   : Int = 0xffd6f7ff.toInt()

    @ColorInt const val PINK     : Int = 0xfffbc7cb.toInt()
    @ColorInt const val PINK_LIGHT: Int = 0xfffaeeef.toInt()

    @ColorInt const val NAVY    : Int = 0xff001f3f.toInt()
    @ColorInt const val BLUE    : Int = 0xff0074d9.toInt()
    @ColorInt const val AQUA    : Int = 0xff7fdbff.toInt()
    @ColorInt const val TEAL    : Int = 0xff39cccc.toInt()
    @ColorInt const val OLIVE   : Int = 0xff3d9970.toInt()
    @ColorInt const val GREEN   : Int = 0xff2ecc40.toInt()
    @ColorInt const val LIME    : Int = 0xff01ff70.toInt()
    @ColorInt const val YELLOW  : Int = 0xffffdc00.toInt()
    @ColorInt const val ORANGE  : Int = 0xffff851b.toInt()
    @ColorInt const val RED     : Int = 0xffff4136.toInt()
    @ColorInt const val MAROON  : Int = 0xff85144b.toInt()
    @ColorInt const val FUCHSIA : Int = 0xfff012be.toInt()
    @ColorInt const val PURPLE  : Int = 0xffb10dc9.toInt()

    @ColorInt fun getColorPrimary(context: Context) =  getColorFromAttribute(context, R.attr.colorPrimary)
    @ColorInt fun getColorPrimaryDark(context: Context) =  getColorFromAttribute(context, R.attr.colorPrimaryDark)
    @ColorInt fun getColorAccent(context: Context) =  getColorFromAttribute(context, R.attr.colorAccent)

    @ColorInt fun getColorBackground(context: Context) =  getColorFromAttribute(context, R.attr.colorBackground)
    @ColorInt fun getColorBackgroundLv1(context: Context) =  getColorFromAttribute(context, R.attr.colorBackground_lv1)

    @ColorInt fun getColorContent(context: Context) =  getColorFromAttribute(context, R.attr.colorContent)
    @ColorInt fun getColorContentSoft(context: Context) =  getColorFromAttribute(context, R.attr.colorContentSoft)

    /**
     * Convert color int in color string with format #AARRGGBB
     */
    fun Int.getColorString(): String = "#%06X".format(0xffffffff.toInt() and this@getColorString)

    @ColorInt
    private fun getColorFromAttribute(
        context: Context,
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ) : Int{
        context.theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }

}