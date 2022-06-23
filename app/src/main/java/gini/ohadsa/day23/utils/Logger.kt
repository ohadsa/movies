package gini.ohadsa.day23.utils

import android.util.Log

class Logger {
    companion object{
        fun log(vararg data:String) {
            data.forEach {
               Log.d("Logger_ohadsa"  , it)
            }
        }


    }
}