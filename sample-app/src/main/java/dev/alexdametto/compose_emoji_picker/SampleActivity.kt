package dev.alexdametto.compose_emoji_picker

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.alexdametto.compose_emoji_picker.presentation.EmojiPicker

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SampleActivityContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SampleActivityContent() {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedEmoji by rememberSaveable { mutableStateOf("😀") }

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
                text = selectedEmoji,
                style = TextStyle(
                    fontSize = 50.sp
                )
            )

            Button(
                onClick = { openBottomSheet = !openBottomSheet },
            ) {
                Text(text = "Change emoji")
            }
        }

        EmojiPicker(
            open = openBottomSheet,
            onClose = {
                openBottomSheet = false
            },
            onSelect = {
                selectedEmoji = it.emoji
                openBottomSheet = false
            }
        )
    }
}