package it.moneycraft.compose_emojis.domain.model

import androidx.annotation.StringRes
import it.moneycraft.compose_emojis.R

enum class EmojiCategory(
    val key: String,
    @StringRes
    val title: Int
) {
    SMILEYS_AND_PEOPLE(key = "smileys_and_people", title = R.string.category_smileys_and_people),
    ANIMALS_AND_NATURE(key = "animals_and_nature", title = R.string.category_animals_and_nature),
    FOOD_AND_DRINK(key = "food_and_drink", title = R.string.category_food_and_drink),
    TRAVEL_AND_PLACES(key = "travel_and_places", title = R.string.category_travel_and_places),
    ACTIVITY(key = "activity", title = R.string.category_activity),
    OBJECTS(key = "objects", title = R.string.category_objects),
    SYMBOLS(key = "symbols", title = R.string.category_symbols),
    FLAGS(key = "flags", title = R.string.category_flags)
}