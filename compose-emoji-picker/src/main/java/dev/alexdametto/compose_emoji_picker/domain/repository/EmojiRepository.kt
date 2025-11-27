package dev.alexdametto.compose_emoji_picker.domain.repository

import dev.alexdametto.compose_emoji_picker.domain.model.Emoji

internal interface EmojiRepository {
    fun list(searchText: String): List<Emoji>
    fun addToRecentEmojis(emoji: Emoji)
}