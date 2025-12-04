package dev.alexdametto.compose_emoji_picker.data

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class EmojiCategoryRepositoryImplTest {
    private lateinit var emojiCategoryRepository: EmojiCategoryRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())

        // initialize
        emojiCategoryRepository = EmojiCategoryRepositoryImpl()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun emojiCategoryRepository_list_returns_all_categories() {
        val categories = emojiCategoryRepository.list()

        assertEquals(categories.size, EmojiCategory.entries.size)
    }

    @Test
    fun emojiCategoryRepository_get_returns_correct_category() {
        val category = emojiCategoryRepository.get(EmojiCategory.SMILEYS_AND_PEOPLE.key)

        assertNotNull(category)
        assertEquals(category!!.key, EmojiCategory.SMILEYS_AND_PEOPLE.key)
    }
}