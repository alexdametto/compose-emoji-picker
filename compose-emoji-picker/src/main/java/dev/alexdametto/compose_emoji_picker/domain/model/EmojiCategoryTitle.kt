package dev.alexdametto.compose_emoji_picker.domain.model

data class EmojiCategoryTitle(
    override val id: String,
    val category: EmojiCategory
) : EmojiListItem(Type.TITLE)
