package dev.alexdametto.compose_emoji_picker.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Colors used by [EmojiPicker]. Build one with [EmojiPickerDefaults.emojiPickerColors].
 *
 * @property backgroundColor Background of the picker's container.
 * @property searchBarBackgroundColor Fill color of the search field.
 * @property searchBarIconTint Color of the search icon.
 * @property searchBarTextColor Text and placeholder color inside the search field.
 * @property textColor Color for category titles and the empty-state message.
 * @property activeCategoryTint Icon tint for the selected category tab.
 * @property inactiveCategoryTint Icon tint for unselected category tabs.
 */
@Immutable
class EmojiPickerColors(
    val backgroundColor: Color,
    val searchBarBackgroundColor: Color,
    val searchBarIconTint: Color,
    val searchBarTextColor: Color,
    val textColor: Color,
    val activeCategoryTint: Color,
    val inactiveCategoryTint: Color
)

/**
 * Default values and factories for [EmojiPickerColors].
 */
object EmojiPickerDefaults {
    /**
     * The default [EmojiPickerColors], read entirely from the current
     * [androidx.compose.material3.MaterialTheme.colorScheme].
     */
    @Composable
    fun emojiPickerColors(): EmojiPickerColors {
        return EmojiPickerColors(
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerLow,
            searchBarBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            searchBarIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
            searchBarTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
            activeCategoryTint = MaterialTheme.colorScheme.primary,
            inactiveCategoryTint = MaterialTheme.colorScheme.onSurface,
        )
    }

    /**
     * Builds [EmojiPickerColors], overriding only the fields you pass — every parameter
     * defaults to a [androidx.compose.material3.MaterialTheme.colorScheme] token.
     *
     * @param backgroundColor Background of the picker's container.
     * @param searchBarBackgroundColor Fill color of the search field.
     * @param searchBarIconTint Color of the search icon.
     * @param searchBarTextColor Text and placeholder color inside the search field.
     * @param textColor Color for category titles and the empty-state message.
     * @param activeCategoryTint Icon tint for the selected category tab.
     * @param inactiveCategoryTint Icon tint for unselected category tabs.
     */
    @Composable
    fun emojiPickerColors(
        backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
        searchBarBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        searchBarIconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        searchBarTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        activeCategoryTint: Color = MaterialTheme.colorScheme.primary,
        inactiveCategoryTint: Color = MaterialTheme.colorScheme.onSurface,
    ): EmojiPickerColors {
        return EmojiPickerColors(
            backgroundColor = backgroundColor,
            searchBarBackgroundColor = searchBarBackgroundColor,
            searchBarIconTint = searchBarIconTint,
            searchBarTextColor = searchBarTextColor,
            textColor = textColor,
            activeCategoryTint = activeCategoryTint,
            inactiveCategoryTint = inactiveCategoryTint
        )
    }
}