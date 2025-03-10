package dev.alexdametto.compose_emoji_picker.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.alexdametto.compose_emoji_picker.common.EmojiConstants
import dev.alexdametto.compose_emoji_picker.data.SharedPreferencesHelper
import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategoryTitle
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiItem
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiListItem
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class EmojiPickerViewModel @Inject constructor(
    private val emojiRepository: EmojiRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(EmojiPickerState())
    val state = _state.asStateFlow()

    init {
        loadEmojisAndCategories()
    }

    private fun loadEmojisAndCategories() {
        _state.update {
            it.copy(
                emojis = emojiRepository.getEmojis(),
                emojiCategories = emojiRepository.getEmojisCategories()
            )
        }
        updateEmojis()
    }

    private fun updateEmojis() {
        val emojis = state.value.emojis
        val emojiCategories = state.value.emojiCategories
        val query = state.value.query

        if (emojis == null || emojiCategories == null) {
            // do nothing
            return
        }

        val allEmojis = ArrayList(emojis)

        // add recent emojis to emoji list
        val recentEmojis = sharedPreferencesHelper.getRecentEmojis()
        recentEmojis.forEach { recentEmoji ->
            allEmojis.add(Emoji(
                    id = "recent_${recentEmoji.id}",
                    emoji = recentEmoji.emoji,
                    name = recentEmoji.name,
                    slug = recentEmoji.slug,

                    // this emoji is part of RECENT category
                    category = EmojiCategory.RECENT.key
                )
            )
        }

        // create a filtered emoji map by category
        val emojiByCategory: Map<EmojiCategory, List<Emoji>> = allEmojis.filter {
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

        val emojiListItems: List<EmojiListItem> = sortedList.flatMap {
            val categoryTitleListItem: EmojiListItem = EmojiCategoryTitle(
                id = it.first.key,
                category = it.first
            )
            val emojiListItems: Array<EmojiListItem> = it.second.map { emoji ->
                EmojiItem(
                    id = emoji.id,
                    emoji = emoji
                )
            }.toTypedArray()

            listOf(categoryTitleListItem, *emojiListItems)
        }.toList()

        // populate indexes map
        val categoryTitleIndexes: MutableMap<String, Int> = mutableMapOf()
        EmojiConstants.categoryOrder.forEach { category ->
            // set index in map
            categoryTitleIndexes[category.key] = emojiListItems.indexOfFirst {
                it.id == category.key
            }
        }

        _state.update {
            it.copy(
                emojiListItems = emojiListItems,
                categoryTitleIndexes = categoryTitleIndexes
            )
        }
    }

    fun onSearchTextChanged(searchText: String) {
        _state.update {
            it.copy(
                query = searchText
            )
        }

        // recompute the emojis
        updateEmojis()
    }

    fun onAddToRecent(emoji: Emoji) {
        sharedPreferencesHelper.addRecentEmoji(emoji)
    }
}