package dev.alexdametto.compose_emoji_picker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewScoped
import dev.alexdametto.compose_emoji_picker.data.SHARED_PREFERENCES_KEY
import dev.alexdametto.compose_emoji_picker.data.SharedPreferencesHelper
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelModule {
    @ViewScoped
    @Provides
    fun provideSharedPreferencesHelper(context: Context): SharedPreferencesHelper {
        return SharedPreferencesHelper(
            context.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            )
        )
    }

    @ViewScoped
    @Provides
    fun provideEmojiRepository(
        context: Context,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): EmojiRepository = EmojiRepository(
        context = context,
        sharedPreferencesHelper = sharedPreferencesHelper
    )
}