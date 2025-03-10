package dev.alexdametto.compose_emoji_picker.common

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory

object EmojiConstants {
    val categoryOrder = listOf(
        EmojiCategory.RECENT,
        EmojiCategory.SMILEYS_AND_PEOPLE,
        EmojiCategory.ANIMALS_AND_NATURE,
        EmojiCategory.FOOD_AND_DRINK,
        EmojiCategory.ACTIVITY,
        EmojiCategory.TRAVEL_AND_PLACES,
        EmojiCategory.OBJECTS,
        EmojiCategory.SYMBOLS,
        EmojiCategory.FLAGS
    )

    const val EMOJI_PER_ROW = 6
}