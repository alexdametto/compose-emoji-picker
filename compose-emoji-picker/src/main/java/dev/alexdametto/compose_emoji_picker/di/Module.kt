package dev.alexdametto.compose_emoji_picker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewScoped
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelModule {
    @ViewScoped
    @Provides
    fun provideEmojiRepository(
        context: Context
    ): EmojiRepository = EmojiRepository(
        context = context
    )
}