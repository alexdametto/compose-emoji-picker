package dev.alexdametto.compose_emoji_picker.domain.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.alexdametto.compose_emoji_picker.R
import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmojiRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getEmojis(): List<Emoji> {
        val json = context.resources.openRawResource(R.raw.emojis)
            .bufferedReader().use { it.readText() }

        val emojiList: List<Emoji> = Gson().fromJson(json, object: TypeToken<MutableList<Emoji>>() {})

        return emojiList
    }

    fun getEmojisCategories(): List<EmojiCategory> {
        return EmojiCategory.entries.toList()
    }
}