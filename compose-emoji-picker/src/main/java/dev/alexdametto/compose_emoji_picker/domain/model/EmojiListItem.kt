package dev.alexdametto.compose_emoji_picker.domain.model

internal sealed class EmojiListItem(
    open val type: Type
) {
    open val id: String = ""

    enum class Type(
        val value: String
    ) {
        TITLE("title"),
        EMOJI("emoji")
    }
}
