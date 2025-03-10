package dev.alexdametto.compose_emoji_picker.domain.model

import com.google.gson.annotations.SerializedName

data class Emoji(
    @SerializedName("key") val id: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("category") val category: String
)

internal val EMOJI_GRINNING_FACE = Emoji(
    id = "grinning_face",
    emoji = "😀",
    name = "grinning face",
    slug = "grinning_face",
    category = EmojiCategory.SMILEYS_AND_PEOPLE.key
)