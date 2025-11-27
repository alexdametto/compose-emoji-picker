package dev.alexdametto.compose_emoji_picker.di

import android.content.Context
import dev.alexdametto.compose_emoji_picker.data.EmojiCategoryRepositoryImpl
import dev.alexdametto.compose_emoji_picker.data.EmojiRepositoryImpl
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiCategoryRepository
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository
import dev.alexdametto.compose_emoji_picker.domain.use_case.AddToRecentEmojisUseCase
import dev.alexdametto.compose_emoji_picker.domain.use_case.GetEmojiCategoriesUseCase
import dev.alexdametto.compose_emoji_picker.domain.use_case.GetEmojiCategoryByKeyUseCase
import dev.alexdametto.compose_emoji_picker.domain.use_case.GetEmojisUseCase
import dev.alexdametto.compose_emoji_picker.presentation.EmojiPickerViewModel

internal object RepositoryModule {
    private fun provideEmojiRepository(
        context: Context,
    ): EmojiRepository = EmojiRepositoryImpl(
        context = context,
    )

    private fun provideEmojiCategoryRepository(): EmojiCategoryRepository = EmojiCategoryRepositoryImpl()

    fun provideEmojiPickerViewModel(
        context: Context
    ): EmojiPickerViewModel {
        val emojiRepository = provideEmojiRepository(context)
        val emojiCategoryRepository = provideEmojiCategoryRepository()

        return EmojiPickerViewModel(
            getEmojisUseCase = GetEmojisUseCase(
                repository = emojiRepository
            ),
            getEmojiCategoriesUseCase = GetEmojiCategoriesUseCase(
                repository = emojiCategoryRepository
            ),
            getEmojiCategoryByKeyUseCase = GetEmojiCategoryByKeyUseCase(
                repository = emojiCategoryRepository
            ),
            addToRecentEmojisUseCase = AddToRecentEmojisUseCase(
                repository = emojiRepository
            ),
        )
    }

}