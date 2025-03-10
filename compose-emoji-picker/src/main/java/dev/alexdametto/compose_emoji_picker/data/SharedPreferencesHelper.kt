package dev.alexdametto.compose_emoji_picker.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.alexdametto.compose_emoji_picker.common.EmojiConstants
import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiItem

private const val RECENT_EMOJIS_KEY = "emoji_picker_recent_emojis"
internal const val SHARED_PREFERENCES_KEY = "emoji_picker_shared_preferences"

internal class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {
    private val listType = object : TypeToken<List<Emoji>>() {}.type

    fun getRecentEmojis(): List<Emoji> {
        val recentEmojis = sharedPreferences.getString(RECENT_EMOJIS_KEY, null)
        return if (recentEmojis.isNullOrEmpty()) {
            mutableListOf()
        } else {
            Gson().fromJson(recentEmojis, listType)
        }
    }

    fun addRecentEmoji(emoji: Emoji) {
        val recentEmojis = ArrayList(getRecentEmojis())

        if (recentEmojis.contains(emoji)) {
            // remove item if already in the list
            recentEmojis.remove(emoji)
        }
        recentEmojis.add(0, emoji)

        // keep only last items
        recentEmojis.takeLast(EmojiConstants.RECENT_EMOJI_LIMIT)

        // update shared preferences
        sharedPreferences.edit()
            .putString(RECENT_EMOJIS_KEY, Gson().toJson(recentEmojis, listType))
            .apply()
    }
}