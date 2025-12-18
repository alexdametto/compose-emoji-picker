package dev.alexdametto.sample_compose_emoji_picker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexdametto.compose_emoji_picker.presentation.EmojiPicker
import dev.alexdametto.compose_emoji_picker.presentation.EmojiPickerDefaults

internal class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SampleActivityTheme {
                SampleActivityContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SampleActivityContent() {
    val openEmojiPicker: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val selectedEmoji: MutableState<String> = remember {
        mutableStateOf("😀")
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 2.dp
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Emoji Picker Sample"
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = selectedEmoji.value,
                style = TextStyle(
                    fontSize = 50.sp
                )
            )

            Button(
                onClick = { openEmojiPicker.value = !openEmojiPicker.value },
            ) {
                Text(text = "Change emoji")
            }
        }

        EmojiPicker(
            open = openEmojiPicker.value,
            onClose = {
                openEmojiPicker.value = false
            },
            onEmojiSelected = {
                selectedEmoji.value = it.emoji
                openEmojiPicker.value = false
            },
            colors = EmojiPickerDefaults.emojiPickerColors(
                backgroundColor = Color.Black,
                searchBarBackgroundColor = Color.Red,
                searchBarIconTint = Color.Green,
                searchBarTextColor = Color.Blue,
                textColor = Color.Yellow,
                activeCategoryTint = Color.Magenta,
                inactiveCategoryTint = Color.Cyan
            )
        )
    }
}