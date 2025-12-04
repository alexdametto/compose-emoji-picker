package dev.alexdametto.compose_emoji_picker.data

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.google.gson.Gson
import dev.alexdametto.compose_emoji_picker.domain.model.ALL_EMOJIS
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream


@ExperimentalCoroutinesApi
internal class EmojiRepositoryImplTest {
    @MockK(relaxed = true)
    private lateinit var sharedPreferences: SharedPreferences

    @MockK
    private lateinit var context: Context

    private lateinit var emojiRepositoryImpl: EmojiRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())

        val inputStream: InputStream = ByteArrayInputStream(Gson().toJson(ALL_EMOJIS).toByteArray())
        val resources: Resources = mockk()

        coEvery { resources.openRawResource(any()) } returns inputStream
        coEvery { context.resources } returns resources

        coEvery { sharedPreferences.getString(any(), any()) } returns null
        coEvery { context.getSharedPreferences(any(), any()) } returns sharedPreferences

        coEvery { context.getString(any()) } answers {
            val id = this.arg<Int>(0)
            EmojiCategory.entries.find { it.title == id }!!.name
        }

        // initialize
        emojiRepositoryImpl = EmojiRepositoryImpl(
            context = context
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun emojiRepository_list_returns_all_emojis() {
        val list = emojiRepositoryImpl.list("")

        assertEquals(list.size, ALL_EMOJIS.size)
        assertEquals(Gson().toJson(list), Gson().toJson(ALL_EMOJIS))
    }

    @Test
    fun emojiRepository_search_by_name_returns_correct_emojis() {
        val list = emojiRepositoryImpl.list("grin")

        assertEquals(list.size, 1)
    }

    @Test
    fun emojiRepository_search_by_category_name_returns_correct_emojis() {
        val list = emojiRepositoryImpl.list("food")

        assertEquals(list.size, 2)
    }

    @Test
    fun emojiRepository_search_by_unknown_test_returns_empty_list() {
        val list = emojiRepositoryImpl.list("unknown")

        assertEquals(list.size, 0)
    }

    @Test
    fun emojiRepository_list_contains_recent_emojis() {
        val recentEmojis = listOf(ALL_EMOJIS[0], ALL_EMOJIS[1])
        coEvery { sharedPreferences.getString(any(), any()) } returns Gson().toJson(recentEmojis)

        val list = emojiRepositoryImpl.list("")

        assertEquals(list.size, ALL_EMOJIS.size + recentEmojis.size)
    }

    @Test
    fun emojiRepository_add_to_recent_emojis_calls_shared_preferences_correctly() {
        emojiRepositoryImpl.addToRecentEmojis(ALL_EMOJIS[0])

        verify(exactly = 1) { sharedPreferences.edit() }
    }
}