package xyz.tberghuis.wordguessinggame.util

import android.util.Log
import xyz.tberghuis.wordguessinggame.BuildConfig

fun logd(s: String) {
  if (BuildConfig.DEBUG) {
    Log.d("xxx", s)
  }
}