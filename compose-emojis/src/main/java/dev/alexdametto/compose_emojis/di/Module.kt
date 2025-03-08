package dev.alexdametto.compose_emojis.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewScoped
import dev.alexdametto.compose_emojis.domain.repository.EmojiRepository

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @ViewScoped
    @Provides
    fun provideEmojiRepository(
        context: Context
    ): EmojiRepository = EmojiRepository(
        context = context
    )
}