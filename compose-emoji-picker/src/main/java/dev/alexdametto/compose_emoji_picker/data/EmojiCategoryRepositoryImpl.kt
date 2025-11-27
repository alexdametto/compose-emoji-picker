package dev.alexdametto.compose_emoji_picker.data

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiCategoryRepository

internal class EmojiCategoryRepositoryImpl: EmojiCategoryRepository {
    private val emojiCategories: List<EmojiCategory> = EmojiCategory.entries.toList()

    override fun list(): List<EmojiCategory> {
        return this.emojiCategories
    }

    override fun get(key: String): EmojiCategory? {
        return EmojiCategory.findByKey(key)
    }
}