package dev.alexdametto.compose_emoji_picker.common

import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiListItem

internal object TestTags {
    const val EMOJI_PICKER = "EMOJI_PICKER"
    const val EMPTY_EMOJI_PICKER = "EMPTY_EMOJI_PICKER"
    const val SEARCH_BAR = "SEARCH_BAR"

    fun tabButton(emojiCategory: EmojiCategory): String {
        return "${emojiCategory.key}_button"
    }

    fun get(emoji: Emoji): String {
        return emoji.name
    }

    fun get(emojiCategory: EmojiCategory): String {
        return emojiCategory.key
    }
}