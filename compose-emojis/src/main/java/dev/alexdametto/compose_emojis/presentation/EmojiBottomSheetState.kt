package dev.alexdametto.compose_emojis.presentation

import dev.alexdametto.compose_emojis.domain.model.Emoji
import dev.alexdametto.compose_emojis.domain.model.EmojiCategory
import dev.alexdametto.compose_emojis.domain.model.EmojiListItem

data class EmojiBottomSheetState(
    val emojis: List<Emoji>? = null,
    val emojiCategories: List<EmojiCategory>? = null,
    val emojiListItems: List<EmojiListItem>? = null,

    val categoryTitleIndexes: Map<String, Int> = emptyMap(),

    val query: String = ""
)