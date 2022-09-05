package com.example.arianamobelroom

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arianamobelroom.components.CustomTextField
import com.example.arianamobelroom.model.Mobel
import com.example.arianamobelroom.ui.theme.ArianaMobelRoomTheme
import com.example.arianamobelroom.viewmodel.MobelViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArianaMobelRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mobelViewModel = viewModel<MobelViewModel>()
                    MainScreen(mobelViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(mobelViewModel: MobelViewModel) {
    val mobelList = mobelViewModel.mobelList.collectAsState().value
    MainContent(
        mobels = mobelList,
        onAddMobel = { mobel -> mobelViewModel.addMobel(mobel) },
        onDeleteMobel = { mobel -> mobelViewModel.deleteMobel(mobel) },
        onDeleteAllMobels = { mobelViewModel.deleteAll() }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent(
    mobels: List<Mobel>,
    onAddMobel: (Mobel) -> Unit,
    onDeleteMobel: (Mobel) -> Unit,
    onDeleteAllMobels: () -> Unit
) {
    Column {
        var name by remember {
            mutableStateOf("")
        }
        var category by remember {
            mutableStateOf("")
        }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        CreateTopAppBar()

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                modifier = Modifier.padding(4.dp),
                text = name,
                label = "Name",
                isSingleLine = true,
                onTextChange = { name = it },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions( onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                iconId = R.mipmap.ic_name_foreground,
                iconModifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            CustomTextField(
                modifier = Modifier.padding(4.dp),
                text = category,
                label = "Category",
                isSingleLine = true,
                onTextChange = { category = it },
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                iconId = R.mipmap.ic_category_foreground,
                iconModifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { onAddMobel( Mobel(mobelName = name, mobelCategory = category) ) }) {
                    Text(text = "Add")
                }

                Button(onClick = { onDeleteAllMobels() }) {
                    Text(text = "Delete All")
                }
            }

            Divider(modifier = Modifier.padding(20.dp))

            if (mobels.isNotEmpty()) {
                MobelPresentation(mobels, onDeleteMobel)
                }
            }
        }
    }

@Composable
private fun CreateTopAppBar() {
    TopAppBar(
        title = {
            Text(text = "Ariana meble")
        },
        actions = {
            Icon(imageVector = Icons.Rounded.Favorite, contentDescription = "Icon favourite")
        },
        backgroundColor = Color.Cyan
    )
}

@Composable
fun MobelPresentation(mobels: List<Mobel>, onDeleteMobel: (Mobel) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        elevation = 4.dp,
        border = BorderStroke(
            1.dp,
            Color.LightGray
        )
    ){
        LazyColumn {
            itemsIndexed(mobels) { index, item ->
                MobelRow(index, item, onMobelClicked = { onDeleteMobel(it) })
            }
        }
    }
}

@Composable
fun MobelRow(index: Int, item: Mobel, onMobelClicked: (Mobel) -> Unit) {
    val simpleDate = SimpleDateFormat("dd.MM.yyyy", Locale.UK)
    val currentDate = simpleDate.format(item.entryDate)
    val color = RandomColors().getColor()
    Card(modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(topEnd = 33.dp))
        .fillMaxWidth(), elevation = 4.dp,
    backgroundColor = Color(color)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                           onMobelClicked(item)
                },
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 25.sp)) {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${index + 1}. ${item.mobelCategory} ${item.mobelName}\n")
                        }
                        append("dodane ${currentDate}r.")
                    }
                }
            )
        }
    }
}
