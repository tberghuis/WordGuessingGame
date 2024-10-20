package xyz.tberghuis.wordguessinggame

import android.app.Application
import android.content.Context
import xyz.tberghuis.wordguessinggame.data.DarkModeRepository

class WggApplication : Application() {
  lateinit var darkModeRepository: DarkModeRepository

  override fun onCreate() {
    super.onCreate()
    createDarkModeRepository()
  }

  private fun createDarkModeRepository() {
    darkModeRepository = DarkModeRepository(this)
  }
}

fun Context.provideDarkModeRepository(): DarkModeRepository {
  return (applicationContext as WggApplication).darkModeRepository
}