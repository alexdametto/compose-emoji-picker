package it.moneycraft.compose_emojis.domain.model

import androidx.annotation.StringRes

data class Emoji(
    val id: String,
    val emoji: String,

    @StringRes
    val name: Int,

    val slug: String,
    val category: EmojiCategory
)