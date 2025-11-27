package dev.alexdametto.compose_emoji_picker.presentation

import androidx.lifecycle.ViewModel
import dev.alexdametto.compose_emoji_picker.common.EmojiConstants
import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategoryTitle
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiItem
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiListItem
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

internal class EmojiPickerViewModel @Inject constructor(
    private val emojiRepository: EmojiRepository
) : ViewModel() {
    private val _state = MutableStateFlow(EmojiPickerState())
    val state = _state.asStateFlow()

    init {
        loadEmojisAndCategories()
    }

    private fun loadEmojisAndCategories() {
        _state.update {
            it.copy(
                emojiCategories = emojiRepository.getEmojiCategories()
            )
        }
        updateEmojis()
    }

    private fun updateEmojis() {
        val query = state.value.query
        val emojis = emojiRepository.getEmojis(query)

        val emojiListItems: List<EmojiListItem> = emojis.flatMap {
            val category = it.first
            val categoryEmojis = it.second

            val categoryTitleListItem: EmojiListItem = EmojiCategoryTitle(
                id = category.key,
                category = category
            )
            val emojiListItems: Array<EmojiListItem> = categoryEmojis.map { emoji ->
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
        emojiRepository.addRecentEmoji(emoji)
    }
}