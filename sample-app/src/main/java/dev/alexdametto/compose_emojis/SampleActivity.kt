package dev.alexdametto.compose_emojis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alexdametto.compose_emojis.presentation.EmojiBottomSheet

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SampleActivityContent()
        }
    }
}

@Preview
@Composable
fun SampleActivityContent() {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            Button(
                onClick = { openBottomSheet = !openBottomSheet },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Open emoji picker")
            }
        }

        EmojiBottomSheet(
            open = openBottomSheet,
            onClose = {
                openBottomSheet = false
            }
        )
    }
}