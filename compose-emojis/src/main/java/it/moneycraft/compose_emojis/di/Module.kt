package it.moneycraft.compose_emojis.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewScoped
import it.moneycraft.compose_emojis.domain.repository.EmojiRepository

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @ViewScoped
    @Provides
    fun provideEmojiRepository(): EmojiRepository = EmojiRepository()
}