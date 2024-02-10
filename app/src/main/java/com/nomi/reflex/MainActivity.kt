package com.nomi.reflex

import android.content.res.Configuration
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.nomi.reflex.ui.theme.ReFlexTheme
import androidx.compose.runtime.remember
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlin.math.cos
import kotlin.math.sin


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

@Preview
@Composable
fun BasicCanvasUsage() {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                // this = DrawScope
            }
    )
}

@Preview
@Composable
fun CanvasSquare() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasQuadrantSize = size / 2F
        drawRect(
            color = Color.Magenta,
            size = canvasQuadrantSize
        )
    }
}

@Preview
@Composable
fun CanvasDrawDiagonalLine() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawLine(
            start = Offset(x = canvasWidth, y = 0f),
            end = Offset(x = 0f, y = canvasHeight),
            strokeWidth = 10f,
            color = Color.Red
        )
    }
}

@Preview
@Composable
fun CanvasTransformationScale() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        scale(scaleX = 10f, scaleY = 15f) {
            drawCircle(Color.DarkGray, radius = 20.dp.toPx())
        }
    }
}

@Preview
@Composable
fun CanvasTransformationTranslate() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 100f, top = -300f) {
            drawCircle(Color.LightGray, radius = 200.dp.toPx())
        }
    }
}

@Preview
@Composable
fun CanvasTransformationRotate() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        for (i in 1..20) {
            rotate(degrees = 15F * i) {
                drawRect(
                    color = Color(10*i, 10*i, 10*i),
                    topLeft = Offset(x = size.width / 3F, y = size.height / 3F),
                    size = size / 2F * i.toFloat()
                )
            }
        }
    }
}

@Preview
@Composable
fun CanvasTransformationInset() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasQuadrantSize = size / 2F
        inset(horizontal = 50f, vertical = 30f) {
            drawRect(
                color = Color(45, 90, 90),
                size = canvasQuadrantSize)
        }
    }
}

@Preview
@Composable
fun CanvasMultipleTransformations() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        withTransform({
            translate(left = size.width / 5F)
            rotate(degrees = 45F)
        }) {
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = size.width / 3F, y = size.height / 3F),
                size = size / 3F
            )
        }
    }
}

@Preview
@Composable
fun CanvasDrawText() {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawText(
            textMeasurer = textMeasurer,
            text = "Yar",
            style = TextStyle(
                color = Color.White,
                fontSize = 20.em))
    }
}

@Preview
@Composable
fun CanvasDrawImage() {
    val myImage = ImageBitmap.imageResource(id = R.drawable.profile_picture)

    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawImage(
            image = myImage)
    })
}

@Preview
@Composable
fun CanvasDrawPath() {
    val rotationState = remember { mutableStateOf(0f) }

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val centerX = size.width / 2f
                val centerY = size.height / 2f
                val outerRadius = size.width / 3f
                val innerRadius = size.width / 6f
                val starPoints = 9

                val angleIncrement = (2 * Math.PI / starPoints).toFloat()

                val path = Path()

                for (i in 0 until starPoints * 2) {
                    val radius = if (i % 2 == 0) outerRadius else innerRadius
                    val angle = i * angleIncrement + Math.toRadians(rotationState.value.toDouble()).toFloat()
                    val x = centerX + radius * cos(angle)
                    val y = centerY + radius * sin(angle)

                    if (i == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }
                path.close()
                onDrawBehind {
                    drawPath(path, Color.Magenta, style = Stroke(width = 10f))
                }
            }
            .fillMaxSize()
    )
}

@Preview
@Composable
fun CanvasMeasureText() {
    val pinkColor = Color(0xFFF48FB1)
    val longTextSample =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    val textMeasurer = rememberTextMeasurer()

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val measuredText =
                    textMeasurer.measure(
                        AnnotatedString(longTextSample),
                        constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                        style = TextStyle(fontSize = 18.sp)
                    )

                onDrawBehind {
                    drawRect(pinkColor, size = measuredText.size.toSize())
                    drawText(measuredText)
                }
            }
            .fillMaxSize()
    )
}

@Preview
@Composable
fun CanvasMeasureTextOverflow() {
    val pinkColor = Color(0xFFF48FB1)
    val longTextSample =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    val textMeasurer = rememberTextMeasurer()

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val measuredText =
                    textMeasurer.measure(
                        AnnotatedString(longTextSample),
                        constraints = Constraints.fixed(
                            width = (size.width / 3f).toInt(),
                            height = (size.height / 3f).toInt()
                        ),
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = 18.sp)
                    )

                onDrawBehind {
                    drawRect(pinkColor, size = measuredText.size.toSize())
                    drawText(measuredText)
                }
            }
            .fillMaxSize()
    )
}

@Preview
@Composable
fun CanvasDrawIntoCanvas() {
    val drawable = ShapeDrawable(OvalShape())
    Spacer(
        modifier = Modifier
            .drawWithContent {
                drawIntoCanvas { canvas ->
                    drawable.setBounds(
                        0, 0,
                        size.width.toInt(), size.height.toInt())
                    drawable.draw(canvas.nativeCanvas)
                }
            }
            .fillMaxSize()
    )
}

@Preview
@Composable
fun CanvasDrawShape() {
    val purpleColor = Color(0xFFBA68C8)
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onDraw = {
            drawCircle(purpleColor)
        }
    )
}

@Preview
@Composable
fun CanvasDrawOtherShapes() {
    val purpleColor = Color(0xFFBA68C8)
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onDraw = {
            drawPoints(
                listOf(
                    Offset(0f, 0f),
                    Offset(size.width / 3f, size.height / 2f),
                    Offset(size.width / 2f, size.height / 5f),
                    Offset(size.width, size.height)
                ),
                color = purpleColor,
                pointMode = PointMode.Points, strokeWidth = 10.dp.toPx()
            )
        }
    )
}

@Preview
@Composable
fun CanvasTransformationScaleAnim() {
    val animatable = remember {
        Animatable(1f)
    }
    LaunchedEffect(Unit) {
        animatable.animateTo(10f, animationSpec = tween(3000, 3000, easing = LinearEasing))
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        scale(scaleX = animatable.value, scaleY = animatable.value * 1.5f) {
            drawCircle(Color.Blue, radius = 20.dp.toPx())
        }
    }
}