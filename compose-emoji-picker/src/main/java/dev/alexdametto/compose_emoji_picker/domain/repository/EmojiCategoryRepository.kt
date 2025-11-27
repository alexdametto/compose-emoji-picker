package dev.alexdametto.compose_emoji_picker.domain.repository

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory

internal interface EmojiCategoryRepository {
    fun list(): List<EmojiCategory>
    fun get(key: String): EmojiCategory?
}