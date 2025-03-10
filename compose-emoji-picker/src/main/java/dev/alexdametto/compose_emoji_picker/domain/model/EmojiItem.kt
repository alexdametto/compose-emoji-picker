package dev.alexdametto.compose_emoji_picker.domain.model

internal data class EmojiItem(
    override val id: String,
    val emoji: Emoji
) : EmojiListItem(Type.EMOJI)