package dev.alexdametto.compose_emojis.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import dev.alexdametto.compose_emojis.common.EmojiConstants
import dev.alexdametto.compose_emojis.domain.model.Emoji
import dev.alexdametto.compose_emojis.domain.model.EmojiCategory
import dev.alexdametto.compose_emojis.domain.model.EmojiCategoryTitle
import dev.alexdametto.compose_emojis.domain.model.EmojiListItem
import dev.alexdametto.compose_emojis.domain.repository.EmojiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class EmojiBottomSheetViewModel @Inject constructor(
    private val emojiRepository: EmojiRepository,
    private val context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(EmojiBottomSheetState())
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

        // TODO: add recent emojis to emoji list

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

        val emojiListItems: List<EmojiListItem> = sortedList.flatMap {
            val categoryTitleListItem: EmojiListItem = EmojiCategoryTitle(
                id = it.first.key,
                category = it.first
            )
            val emojiListItems: Array<EmojiListItem> = it.second.toTypedArray()

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
        updateEmojis()
    }
}