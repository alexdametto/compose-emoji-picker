package dev.alexdametto.compose_emoji_picker.domain.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.alexdametto.compose_emoji_picker.R
import dev.alexdametto.compose_emoji_picker.common.EmojiConstants
import dev.alexdametto.compose_emoji_picker.data.SharedPreferencesHelper
import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class EmojiRepository @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext private val context: Context
) {
    private val allEmojis: List<Emoji>
    private val allEmojiCategories: List<EmojiCategory>

    init {
        val json = context.resources.openRawResource(R.raw.emojis)
            .bufferedReader().use { it.readText() }

        val emojiList: List<Emoji> =
            Gson().fromJson(json, object : TypeToken<MutableList<Emoji>>() {})

        this.allEmojis = emojiList
        this.allEmojiCategories = EmojiCategory.entries.toList()
    }

    fun getEmojis(
        query: String
    ): List<Pair<EmojiCategory, List<Emoji>>> {
        val emojis = ArrayList(this.allEmojis)

        val recentEmojis = sharedPreferencesHelper.getRecentEmojis()
        recentEmojis.forEach { recentEmoji ->
                emojis.add(0, recentEmoji.copy(
                    id = "recent_${recentEmoji.id}",
                    category = EmojiCategory.RECENT.key
                )
            )
        }

        // create a filtered emoji map by category
        val emojiByCategory: Map<EmojiCategory, List<Emoji>> = emojis.filter {
            val categoryTitle = context.getString(EmojiCategory.findByKey(it.category)!!.title)

            // search in icon name or in category name
            val whereToSearch = listOf(it.name, categoryTitle)

            whereToSearch.any { searchContext ->
                searchContext.lowercase().contains(query.lowercase())
            }
        }.groupBy {
            // group by category
            it.category
        }.mapKeys {
            // get EmojiCategory object from key
            EmojiCategory.findByKey(it.key)!!
        }

        val sortedList: List<Pair<EmojiCategory, List<Emoji>>> =
            emojiByCategory.toList().sortedBy { entry ->
                EmojiConstants.categoryOrder.find { it.key == entry.first.key }
            }

        return sortedList;
    }

    fun getEmojiCategories(): List<EmojiCategory> {
        return this.allEmojiCategories;
    }

    fun addRecentEmoji(emoji: Emoji) {
        sharedPreferencesHelper.addRecentEmoji(emoji)
    }
}