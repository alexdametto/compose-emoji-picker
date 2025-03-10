package dev.alexdametto.compose_emojis.domain.model

import com.google.gson.annotations.SerializedName

data class Emoji(
    @SerializedName("key") override val id: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("category") val category: String
): EmojiListItem(Type.EMOJI)

val EMOJI_GRINNING_FACE = Emoji(
    id = "grinning_face",
    emoji = "😀",
    name = "grinning face",
    slug = "grinning_face",
    category = EmojiCategory.SMILEYS_AND_PEOPLE.key
)