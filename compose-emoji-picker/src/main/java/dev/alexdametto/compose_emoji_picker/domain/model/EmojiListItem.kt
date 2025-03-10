package dev.alexdametto.compose_emoji_picker.domain.model

sealed class EmojiListItem(
    open val type: Type
) {
    open val id: String = ""

    enum class Type(
        val value: String
    ) {
        TITLE("title"),
        EMOJI("emoji"),
        UNKNOWN("unknown");

        companion object {
            fun find(ordinal: Int) = entries.find { it.ordinal == ordinal } ?: UNKNOWN
        }
    }
}
