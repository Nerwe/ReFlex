package com.nomi.reflex

import android.os.Bundle
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.nomi.reflex.ui.theme.ReFlexTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReFlexTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier
        .padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier
            .width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surface,
            label = ""
        )

        Column(modifier = Modifier
            .clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                fontSize = TextUnit(4f, TextUnitType.Em)
            )
            Spacer(modifier = Modifier
                .height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = false,
    name = "Dark mode"
)
@Composable
fun PreviewMessageCard() {
    ReFlexTheme {
        Surface {
            MessageCard(msg = Message("Android", "Super cool message"))
        }
    }
}

@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ReFlexTheme {
        Conversation(SampleData.conversationSample)
    }
}

/**
 * SampleData for Jetpack Compose Tutorial
 */
object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Yar",
            "Test...Test...Test..."
        ),
        Message(
            "Yar",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        Message(
            "Yar",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        Message(
            "Yar",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Yar",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
        ),
        Message(
            "Yar",
            "It's available from API 21+ :)"
        ),
        Message(
            "Yar",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Yar",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Yar",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Yar",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Yar",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Yar",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Yar",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}
