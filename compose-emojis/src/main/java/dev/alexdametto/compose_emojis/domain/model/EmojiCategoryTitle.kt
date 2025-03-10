package dev.alexdametto.compose_emojis.domain.model

data class EmojiCategoryTitle(
    override val id: String,
    val category: EmojiCategory
) : EmojiListItem(Type.TITLE)
