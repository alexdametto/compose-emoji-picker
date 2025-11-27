package dev.alexdametto.compose_emoji_picker.presentation

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiListItem

internal data class EmojiPickerState(
    val emojiCategories: List<EmojiCategory>? = null,
    val emojiListItems: List<EmojiListItem>? = null,

    val categoryTitleIndexes: Map<String, Int> = emptyMap(),

    val query: String = ""
)