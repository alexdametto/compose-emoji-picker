package dev.alexdametto.compose_emoji_picker.presentation

import dev.alexdametto.compose_emoji_picker.domain.model.ALL_EMOJIS
import dev.alexdametto.compose_emoji_picker.domain.model.ALL_EMOJI_CATEGORIES
import dev.alexdametto.compose_emoji_picker.domain.model.EMOJI_GRAPES
import dev.alexdametto.compose_emoji_picker.domain.model.EMOJI_GRINNING_FACE
import dev.alexdametto.compose_emoji_picker.domain.model.EMOJI_MELON
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.use_case.AddToRecentEmojisUseCase
import dev.alexdametto.compose_emoji_picker.domain.use_case.GetEmojiCategoriesUseCase
import dev.alexdametto.compose_emoji_picker.domain.use_case.GetEmojiCategoryByKeyUseCase
import dev.alexdametto.compose_emoji_picker.domain.use_case.GetEmojisUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class EmojiPickerViewModelTest {
    @MockK
    private lateinit var getEmojisUseCase: GetEmojisUseCase
    @MockK
    private lateinit var getEmojiCategoriesUseCase: GetEmojiCategoriesUseCase
    @MockK
    private lateinit var getEmojiCategoryByKeyUseCase: GetEmojiCategoryByKeyUseCase
    @MockK
    private lateinit var addToRecentEmojisUseCase: AddToRecentEmojisUseCase

    private lateinit var viewModel: EmojiPickerViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())

        // use case mocks
        coEvery { getEmojisUseCase(any()) } returns mutableListOf()
        coEvery { getEmojisUseCase("grinning") } returns mutableListOf(EMOJI_GRINNING_FACE)
        coEvery { getEmojisUseCase("food_and_drink") } returns mutableListOf(EMOJI_GRAPES, EMOJI_MELON)
        coEvery { getEmojisUseCase("") } returns ALL_EMOJIS
        coEvery { getEmojiCategoriesUseCase() } returns ALL_EMOJI_CATEGORIES

        EmojiCategory.entries.forEach {
            coEvery { getEmojiCategoryByKeyUseCase(it.key) } returns it
        }

        coEvery { addToRecentEmojisUseCase(any()) } returns mockk()

        // initialize viewmodel
        viewModel = EmojiPickerViewModel(
            getEmojisUseCase = getEmojisUseCase,
            getEmojiCategoriesUseCase = getEmojiCategoriesUseCase,
            getEmojiCategoryByKeyUseCase = getEmojiCategoryByKeyUseCase,
            addToRecentEmojisUseCase = addToRecentEmojisUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun emojiPickerViewModel_initialState_searchText_is_empty() = runTest {
        val state = viewModel.state.value

        assertEquals(state.searchText, "")
    }

    @Test
    fun emojiPickerViewModel_initialState_emojis_are_all() = runTest {
        val state = viewModel.state.value

        assertNotNull(state.emojiListItems)

        // calculate number of distinct categories in the emojis
        val distinctCategories = ALL_EMOJIS.map { it.category }.toSet()

        assertEquals(state.emojiListItems!!.size, ALL_EMOJIS.size + distinctCategories.size)
    }

    @Test
    fun emojiPickerViewModel_initialState_categories_are_all() = runTest {
        val state = viewModel.state.value

        assertNotNull(state.emojiCategories)
        assertEquals(state.emojiCategories!!.size, ALL_EMOJI_CATEGORIES.size)
    }

    @Test
    fun emojiPickerViewModel_initialState_categoryIndexes_are_computed_correctly() = runTest {
        val state = viewModel.state.value

        assertNotNull(state.emojiListItems)

        // check each category index is populated correctly
        ALL_EMOJI_CATEGORIES.forEach { category ->
            val categoryIndex = state.emojiListItems!!.indexOfFirst {
                it.id == category.key
            }

            assertEquals(state.categoryTitleIndexes[category.key], categoryIndex)
        }
    }

    @Test
    fun emojiPickerViewModel_searchEmoji_not_found() = runTest {
        viewModel.onSearchTextChanged("unknown")

        val state = viewModel.state.value

        assertNotNull(state.emojiListItems)
        assertTrue(state.emojiListItems!!.isEmpty())
    }

    @Test
    fun emojiPickerViewModel_searchEmoji_found() = runTest {
        viewModel.onSearchTextChanged("grinning")

        val state = viewModel.state.value

        assertNotNull(state.emojiListItems)
        assertTrue(state.emojiListItems!!.isNotEmpty())

        // GRINNING face + category title
        assertEquals(state.emojiListItems.size, 2)

        // check indexes
        ALL_EMOJI_CATEGORIES.forEach {
            if(it.key != EMOJI_GRINNING_FACE.category) {
                assertTrue(state.categoryTitleIndexes[it.key] == -1)
            } else {
                assertEquals(state.categoryTitleIndexes[it.key], 0)
            }
        }
    }

    @Test
    fun emojiPickerViewModel_searchEmojiCategory_found() = runTest {
        viewModel.onSearchTextChanged("food_and_drink")

        val state = viewModel.state.value

        assertNotNull(state.emojiListItems)
        assertTrue(state.emojiListItems!!.isNotEmpty())

        // 2 emojis + category title
        assertEquals(state.emojiListItems.size, 3)

        // check indexes
        ALL_EMOJI_CATEGORIES.forEach {
            if(it.key != EMOJI_MELON.category) {
                assertTrue(state.categoryTitleIndexes[it.key] == -1)
            } else {
                assertEquals(state.categoryTitleIndexes[it.key], 0)
            }
        }
    }

    @Test
    fun emojiPickerViewModel_addToRecent_calls_useCase() = runTest {
        viewModel.onAddToRecent(EMOJI_GRINNING_FACE)

        // check that use case has been called
        verify(exactly = 1) { addToRecentEmojisUseCase(EMOJI_GRINNING_FACE) }
    }
}