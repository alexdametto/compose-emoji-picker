package dev.alexdametto.compose_emoji_picker.domain.use_case

import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository

internal class AddToRecentEmojisUseCase(
    private val repository: EmojiRepository
) {
    operator fun invoke(emoji: Emoji) {
        repository.addToRecentEmojis(emoji)
    }
}