package dev.alexdametto.compose_emoji_picker.presentation

import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiListItem

data class EmojiPickerState(
    internal val emojis: List<Emoji>? = null,
    internal val emojiCategories: List<EmojiCategory>? = null,
    val emojiListItems: List<EmojiListItem>? = null,

    val categoryTitleIndexes: Map<String, Int> = emptyMap(),

    val query: String = ""
)