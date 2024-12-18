package com.nooro.weatherapp.presentation.ui.common

import android.annotation.SuppressLint
import android.view.KeyEvent
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nooro.weatherapp.R
import com.nooro.weatherapp.WeatherApp
import com.nooro.weatherapp.entities.ui_models.FullWeatherUIModel
import com.nooro.weatherapp.entities.ui_models.WeatherResultsUIModel
import com.nooro.weatherapp.presentation.TempStyle
import com.nooro.weatherapp.presentation.ui.theme.Dimens
import com.nooro.weatherapp.presentation.ui.theme.WeatherTypography
import com.nooro.weatherapp.presentation.ui.theme.text_color_faint
import com.nooro.weatherapp.presentation.ui.theme.text_color_light
import com.nooro.weatherapp.presentation.ui.theme.text_color_normal
import com.nooro.weatherapp.presentation.ui.theme.tile_color

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.onClick(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = {
            WeatherApp.debounceClicks {
                onClick.invoke()
            }
        },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}

@Composable
fun V_MultiStyleText(
    text1: String,
    text2: String,
    showIcon1: Boolean = false,
    icon1: Int = R.drawable.ic_direction,
    color1: Color = text_color_normal,
    color2: Color = text_color_normal,
    modifier: Modifier,
    textDecoration: TextDecoration? = null,
    fontSize1: TextUnit = 30.sp,
    fontSize2: TextUnit = 60.sp,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Center
) {

    val my1 = "upperInlineContent"

    val inlineContent = mapOf(
        Pair(
            // This tells the [CoreText] to replace the placeholder string "[icon]" by
            // the composable given in the [InlineTextContent] object.
            my1,
            InlineTextContent(
                // Placeholder tells text layout the expected size and vertical alignment of
                // children composable.
                Placeholder(
                    width = 21.sp,
                    height = 21.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                // This Icon will fill maximum size, which is specified by the [Placeholder]
                // above. Notice the width and height in [Placeholder] are specified in TextUnit,
                // and are converted into pixel by text layout.

                Icon(
                    painter = painterResource(icon1), "",
                    tint = text_color_normal
                )
            }
        ),
    )

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = color1, fontSize = fontSize1)) {
                append(text1)
            }
            if (showIcon1) {
                append(" ")
                appendInlineContent(my1, "[icon]")
            }
            withStyle(style = SpanStyle(color = Color.Transparent)) {
                append("\n")
            }
            withStyle(style = SpanStyle(color = color2, fontSize = fontSize2)) {
                append(text2)
            }
        },
        modifier = modifier,
        textDecoration = textDecoration,
        inlineContent = inlineContent,
        style = style.copy(
            textAlign = textAlign,
            lineHeight = TextUnit.Unspecified
        )
    )
}


@Preview
@Composable
fun CheckThis() {

    V_MultiStyleText(
        text1 = "checking",
        text2 = stringResource(R.string.degrees, 90),
        showIcon1 = true,
        modifier = Modifier.wrapContentSize(),
        style = WeatherTypography.displayMedium
    )

}

@Composable
fun Dp.V_Space() = Spacer(
    modifier = Modifier
        .height(this)
)

@Composable
fun Dp.H_Space() = Spacer(
    modifier = Modifier
        .width(this)
)

@Composable
fun WeatherResults(
    model: FullWeatherUIModel,
    tempStyle: TempStyle = TempStyle.CELSIUS,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.grid_14_5)
            .background(
                color = tile_color,
                shape = RoundedCornerShape(Dimens.grid_2)
            )
            .onClick {
                if (model.cityName != null && model.region !=null){
                    onClick("${model.cityName}, ${ model.region }")
                }
            }
            .padding(vertical = Dimens.grid_1),
        horizontalArrangement = Arrangement.Center
    ) {
        V_MultiStyleText(
            text1 = model.cityName ?: "",
            text2 = stringResource(R.string.degrees, model.tempC),
            modifier = Modifier.wrapContentSize(),
            style = WeatherTypography.displayMedium,
            fontSize1 = 20.sp,
        )

        Dimens.grid_12.H_Space()

        AsyncImage(
            model = "https:${model.weatherIcon}",
            contentDescription = "weather_icon",
            modifier = Modifier
                .width(Dimens.grid_15_5)
                .height(Dimens.grid_14)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    hint: String,
    fieldValue: MutableState<String>,
    action: (txt: String) -> Unit,
) {

    val focusManager = LocalFocusManager.current
    TextField(
        maxLines = 1,
        value = fieldValue.value ,
        onValueChange = {
            fieldValue.value = it
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                action(fieldValue.value)
                focusManager.clearFocus()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(Dimens.grid_1)
            .onKeyEvent {
                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                    //focusRequester.requestFocus()
                    action(fieldValue.value)
                    true
                }
                false
            },
        label = {
            Text(
                text = hint,
                fontSize = 16.sp,
            )
        },
        shape = RoundedCornerShape(Dimens.grid_2),
        trailingIcon = {
            if (fieldValue.value.isNotEmpty()) {
                IconButton(onClick = { fieldValue.value = "" }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = text_color_light
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = text_color_light,
                )
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = text_color_faint,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
    )
}