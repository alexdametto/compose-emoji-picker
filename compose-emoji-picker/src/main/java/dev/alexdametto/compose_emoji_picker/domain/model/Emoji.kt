package dev.alexdametto.compose_emoji_picker.domain.model

import com.google.gson.annotations.SerializedName

/**
 * A single emoji, as passed to [dev.alexdametto.compose_emoji_picker.presentation.EmojiPicker]'s
 * `onEmojiSelected` callback.
 *
 * @property id Unique key for the emoji.
 * @property emoji The emoji glyph itself.
 * @property name Human-readable name, resolved to the matched search language.
 * @property slug Identifier-friendly form of the name.
 * @property category The Unicode category this emoji belongs to.
 */
data class Emoji(
    @SerializedName("key") val id: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("category") val category: String
)