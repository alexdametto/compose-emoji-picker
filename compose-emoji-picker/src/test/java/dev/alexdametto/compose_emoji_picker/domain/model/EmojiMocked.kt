package dev.alexdametto.compose_emoji_picker.domain.model

internal val EMOJI_GRINNING_FACE = Emoji(
    id = "grinning_face",
    emoji = "😀",
    name = "grinning face",
    slug = "grinning_face",
    category = EmojiCategory.SMILEYS_AND_PEOPLE.key
)

internal val EMOJI_MONKEY_FACE = Emoji(
    id = "monkey_face",
    emoji = "🐵",
    name = "monkey face",
    slug = "monkey_face",
    category = EmojiCategory.ANIMALS_AND_NATURE.key
)

internal val EMOJI_GRAPES = Emoji(
    id = "grapes",
    emoji = "🍇",
    name = "grapes",
    slug = "grapes",
    category = EmojiCategory.FOOD_AND_DRINK.key
)

internal val EMOJI_MELON = Emoji(
    id = "melon",
    emoji = "🍈",
    name = "melon",
    slug = "melon",
    category = EmojiCategory.FOOD_AND_DRINK.key
)

internal val ALL_EMOJIS = listOf(
    EMOJI_GRINNING_FACE,
    EMOJI_MONKEY_FACE,
    EMOJI_GRAPES,
    EMOJI_MELON
)