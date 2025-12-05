package dev.alexdametto.compose_emoji_picker.presentation

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.alexdametto.compose_emoji_picker.common.TestTags
import dev.alexdametto.compose_emoji_picker.domain.model.EMOJI_GRINNING_FACE
import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmojiPickerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setup(
        open: Boolean = true,
        onClose: () -> Unit = { },
        onEmojiSelected: (emoji: Emoji) -> Unit = { }
    ) {
        MockKAnnotations.init()
        composeTestRule.setContent {
            EmojiPicker(
                open = open,
                onClose = onClose,
                onEmojiSelected = onEmojiSelected
            )
        }
    }

    @Test
    fun when_open_is_false_is_closed() {
        setup(open = false)

        composeTestRule.onNodeWithTag(TestTags.EMOJI_PICKER).assertDoesNotExist()
    }

    @Test
    fun when_open_is_true_is_open() {
        setup()

        composeTestRule.onNodeWithTag(TestTags.EMOJI_PICKER).assertExists()
        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR).assertExists()
    }

    @Test
    fun typing_into_search_bar_shows_results() {
        setup()

        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR).performTextInput("UNKNOWN_EMOJI")
        composeTestRule.onNodeWithTag(TestTags.EMPTY_EMOJI_PICKER).assertExists()
    }

    @Test
    fun typing_into_search_bar_disables_unavailable_categories() {
        setup()

        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR).performTextInput("SMILE")

        EmojiCategory.entries.forEach {
            if(it === EmojiCategory.SMILEYS_AND_PEOPLE) {
                composeTestRule.onNodeWithTag(TestTags.tabButton(it)).assertIsEnabled()
            } else {
                composeTestRule.onNodeWithTag(TestTags.tabButton(it)).assertIsNotEnabled()
            }
        }
    }

    @Test
    fun clicking_on_emoji_calls_on_emoji_selected() {
        val onEmojiSelected: (emoji: Emoji) -> Unit = mockk(relaxed = true)
        setup(
            onEmojiSelected = onEmojiSelected
        )

        composeTestRule.onNodeWithTag(TestTags.get(EMOJI_GRINNING_FACE)).performClick()
        verify(exactly = 1) { onEmojiSelected(EMOJI_GRINNING_FACE) }
    }
}