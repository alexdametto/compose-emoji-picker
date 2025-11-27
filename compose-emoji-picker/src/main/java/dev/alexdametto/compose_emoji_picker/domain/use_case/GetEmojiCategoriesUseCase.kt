package dev.alexdametto.compose_emoji_picker.domain.use_case

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiCategoryRepository

internal class GetEmojiCategoriesUseCase(
    private val repository: EmojiCategoryRepository
) {
    operator fun invoke(): List<EmojiCategory> {
        return repository.list()
    }
}