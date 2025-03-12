package dev.alexdametto.compose_emoji_picker.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexdametto.compose_emoji_picker.R
import dev.alexdametto.compose_emoji_picker.common.EmojiConstants
import dev.alexdametto.compose_emoji_picker.di.ViewModelModule
import dev.alexdametto.compose_emoji_picker.domain.model.EMOJI_GRINNING_FACE
import dev.alexdametto.compose_emoji_picker.domain.model.Emoji
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategory
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiCategoryTitle
import dev.alexdametto.compose_emoji_picker.domain.model.EmojiItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiPicker(
    open: Boolean,
    onClose: () -> Unit,
    onEmojiSelected: (emoji: Emoji) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val gridState = rememberLazyGridState()
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val viewModel: EmojiPickerViewModel by lazy {
        EmojiPickerViewModel(
            emojiRepository = ViewModelModule.provideEmojiRepository(
                context = context
            ),
            sharedPreferencesHelper = ViewModelModule.provideSharedPreferencesHelper(
                context = context
            ),
            context = context
        )
    }

    LaunchedEffect(open) {
        if (!open) {
            // if picker is being closed,

            // just clear the search text
            viewModel.onSearchTextChanged("")

            // scroll at beginning
            gridState.scrollToItem(0)
        }
    }

    if (open) {
        ModalBottomSheet(
            onDismissRequest = onClose,
            sheetState = sheetState,
            modifier = Modifier
                .nestedScroll(rememberNestedScrollInteropConnection())
                .systemBarsPadding()
        ) {
            EmojiPickerContent(
                state = viewModel.state.collectAsState().value,
                gridState = gridState,
                onCategoryTabClick = { categoryTitleIndex ->
                    coroutineScope.launch {
                        // Animate scroll to the 10th item
                        gridState.animateScrollToItem(
                            index = categoryTitleIndex
                        )
                    }
                },
                onSearchTextChange = viewModel::onSearchTextChanged,
                onEmojiSelected = onEmojiSelected,
                onAddToRecent = viewModel::onAddToRecent
            )
        }
    }
}

@Composable
private fun EmojiPickerContent(
    state: EmojiPickerState,
    gridState: LazyGridState,
    onCategoryTabClick: (categoryTitleIndex: Int) -> Unit,
    onSearchTextChange: (searchText: String) -> Unit,
    onEmojiSelected: (emoji: Emoji) -> Unit,
    onAddToRecent: (emoji: Emoji) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        EmojiSearchBar(
            value = state.query,
            onValueChange = onSearchTextChange
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            EmojiConstants.categoryOrder.forEach { category ->
                val categoryTitleIndex = state.categoryTitleIndexes.getOrDefault(category.key, -1)
                val isEnabled = categoryTitleIndex != -1
                val isSelected = isEnabled && state.categoryTitleIndexes.keys
                    .filter { categoryKey ->
                        // filter out categories that are not in the result set
                        state.categoryTitleIndexes.getOrDefault(categoryKey, -1) != -1
                    }.last { categoryKey ->
                        // calculate all distances from current visible item and category titles
                        // < 0 if item is not visible (still to see)
                        // >= 0 if item has already been viewed
                        gridState.firstVisibleItemIndex - state.categoryTitleIndexes.getOrDefault(
                            categoryKey,
                            0
                        ) >= 0

                        // last item remaining after the filter is the selected on
                    } == category.key

                CategoryTabButton(
                    category = category,
                    selected = isSelected,
                    enabled = isEnabled,
                    onClick = {
                        onCategoryTabClick(categoryTitleIndex)
                    },
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(
                vertical = 5.dp
            )
        )

        if (state.emojiListItems!!.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.no_emoji_found),
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                    color = Color.Gray
                )
            }
        } else {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(EmojiConstants.EMOJI_PER_ROW),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.emojiListItems, span = {
                    if (it is EmojiItem) {
                        GridItemSpan(1)
                    } else {
                        GridItemSpan(6)
                    }
                }, key = { it.id }) {
                    if (it is EmojiItem) {
                        EmojiButton(
                            emoji = it.emoji,
                            onEmojiSelected = onEmojiSelected,
                            onAddToRecent = onAddToRecent
                        )
                    } else if (it is EmojiCategoryTitle) {
                        CategoryTitle(
                            category = it.category
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmojiSearchBar(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val height = 40.dp
    val cornerShape = RoundedCornerShape(8.dp)

    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(color = colorResource(R.color.search_bar_bg_color), shape = cornerShape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = modifier
                .weight(5f)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = value,
            onValueChange = onValueChange,
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart,
                            content = {
                                if (value.isEmpty()) {
                                    Text(
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = stringResource(R.string.search),
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            singleLine = true
        )
    }
}

@Composable
private fun RowScope.CategoryTabButton(
    category: EmojiCategory,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        enabled = enabled
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(category.icon),
            contentDescription = stringResource(category.title),
            tint = if (selected) MaterialTheme.colorScheme.primary else LocalContentColor.current
        )
    }
}

@Composable
private fun CategoryTitle(
    category: EmojiCategory
) {
    Text(
        text = stringResource(category.title),
        style = TextStyle(
            fontSize = 16.sp
        ),
        color = Color.Gray,
    )
}

@Composable
private fun EmojiButton(
    emoji: Emoji,
    onEmojiSelected: (emoji: Emoji) -> Unit,
    onAddToRecent: (emoji: Emoji) -> Unit,
) {
    TextButton(
        onClick = {
            // add to recent
            onAddToRecent(emoji)

            // select emoji
            onEmojiSelected(emoji)
        }
    ) {
        Text(
            text = emoji.emoji,
            style = TextStyle(
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun EmojiBottomSheetContentPreview() {
    Scaffold {
        EmojiPickerContent(
            state = EmojiPickerState(
                emojiListItems = listOf(
                    EmojiCategoryTitle(
                        category = EmojiCategory.OBJECTS,
                        id = EmojiCategory.OBJECTS.key
                    ),
                    EmojiItem(
                        id = EMOJI_GRINNING_FACE.id,
                        emoji = EMOJI_GRINNING_FACE
                    )
                ),
                categoryTitleIndexes = mapOf(
                    EmojiCategory.RECENT.key to 0
                )
            ),
            gridState = LazyGridState(),
            onCategoryTabClick = { },
            onSearchTextChange = { },
            onEmojiSelected = { },
            onAddToRecent = { }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun EmojiBottomSheetContentEmptyPreview() {
    Scaffold {
        EmojiPickerContent(
            state = EmojiPickerState(
                emojiListItems = listOf(),
                categoryTitleIndexes = mapOf()
            ),
            gridState = LazyGridState(),
            onCategoryTabClick = { },
            onSearchTextChange = { },
            onEmojiSelected = { },
            onAddToRecent = { }
        )
    }
}