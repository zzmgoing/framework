package com.zzming.core.utils

import android.os.Build

/**
 * @author ZhongZiMing
 * @time 2020/8/5 14:37
 * @description
 **/
class BuildUtils {

    companion object {
        /**
         * Android 10.0
         */
        fun isAtLeast29Api(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        }

        /**
         * Android 9.0
         */
        fun isAtLeast28Api(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
        }

        /**
         * Android 8.0
         */
        fun isAtLeast26Api(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        }

        /**
         * Android 7.0
         */
        fun isAtLeast24Api(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        }

        /**
         * Android 6.0
         */
        fun isAtLeast23Api(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        /**
         * Android 5.0
         */
        fun isAtLeast21Api(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        /**
         * Android 4.2
         */
        fun isAtLeast17Api(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
        }
    }

}