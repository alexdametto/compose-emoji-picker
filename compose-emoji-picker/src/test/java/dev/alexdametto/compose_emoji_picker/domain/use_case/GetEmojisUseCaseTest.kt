package dev.alexdametto.compose_emoji_picker.domain.use_case

import dev.alexdametto.compose_emoji_picker.domain.model.EMOJI_GRINNING_FACE
import dev.alexdametto.compose_emoji_picker.domain.repository.EmojiRepository
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
internal class GetEmojisUseCaseTest {
    @MockK
    lateinit var repository: EmojiRepository

    private lateinit var getEmojisUseCase: GetEmojisUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())

        coEvery { repository.list(any()) } returns mockk()

        // initialize
        getEmojisUseCase = GetEmojisUseCase(
            repository = repository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun addToRecentEmojiUseCase_invoke_calls_repository() {
        getEmojisUseCase("search_emoji")

        verify(exactly = 1) { repository.list("search_emoji") }
    }
}