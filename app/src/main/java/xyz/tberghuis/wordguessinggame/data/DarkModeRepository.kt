package xyz.tberghuis.wordguessinggame.data

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(
  name = "user_preferences",
)

class DarkModeRepository(val appContext: Context) {

  val isDarkModeFlow: Flow<Boolean> = appContext.dataStore.data.map { preferences ->
    preferences[booleanPreferencesKey("is_dark_mode")] ?: initIsDarkTheme()
  }

  suspend fun updateIsDarkMode(isDarkMode: Boolean) {
    appContext.dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("is_dark_mode")] = isDarkMode

    }
  }

  private fun initIsDarkTheme(): Boolean {
    // todo
//    https://stackoverflow.com/questions/44170028/android-how-to-detect-if-night-mode-is-on-when-using-appcompatdelegate-mode-ni

//  todo  onResume check if Configuration changed, meh user can toggle setting with button
// check nowinandroid how it handles dark/light theme

    // doing it wrong
    val nightModeFlags: Int =
      appContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
      return true
    }
    return false
  }
}