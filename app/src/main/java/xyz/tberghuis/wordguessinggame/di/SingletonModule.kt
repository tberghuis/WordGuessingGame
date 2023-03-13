package xyz.tberghuis.wordguessinggame.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import xyz.tberghuis.wordguessinggame.data.DarkModeRepository



@InstallIn(SingletonComponent::class)
@Module
object SingletonModule {
  @Provides
  @Singleton
  fun provideDataStore(@ApplicationContext appContext: Context): DarkModeRepository {
    return DarkModeRepository(appContext)
  }


}