package dev.alexdametto.compose_emoji_picker.domain.use_case

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiCategoryRepository

internal class GetEmojiCategoryByKeyUseCase(
    private val repository: EmojiCategoryRepository
) {
    operator fun invoke(key: String): EmojiCategory? {
        return repository.get(key)
    }
}