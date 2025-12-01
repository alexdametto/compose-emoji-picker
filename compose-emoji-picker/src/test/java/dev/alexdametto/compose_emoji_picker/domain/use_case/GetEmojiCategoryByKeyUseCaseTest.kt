package dev.alexdametto.compose_emoji_picker.domain.use_case

import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiCategoryRepository
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
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetEmojiCategoryByKeyUseCaseTest {
    @MockK
    lateinit var repository: EmojiCategoryRepository

    private lateinit var getEmojiCategoryByKeyUseCase: GetEmojiCategoryByKeyUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())

        coEvery { repository.get(EmojiCategory.FOOD_AND_DRINK.key) } returns EmojiCategory.FOOD_AND_DRINK

        // initialize
        getEmojiCategoryByKeyUseCase = GetEmojiCategoryByKeyUseCase(
            repository = repository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun addToRecentEmojiUseCase_invoke_calls_repository() {
        getEmojiCategoryByKeyUseCase(EmojiCategory.FOOD_AND_DRINK.key)

        verify(exactly = 1) { repository.get(EmojiCategory.FOOD_AND_DRINK.key) }
    }
}