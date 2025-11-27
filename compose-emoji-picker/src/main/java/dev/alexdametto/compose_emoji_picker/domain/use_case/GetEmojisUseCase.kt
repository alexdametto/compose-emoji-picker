package dev.alexdametto.compose_emoji_picker.domain.use_case

import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository

internal class GetEmojisUseCase(
    private val repository: EmojiRepository
) {
    operator fun invoke(searchText: String): List<Emoji> {
        return repository.list(searchText)
    }
}