package com.example.searchapp.ui.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.searchapp.R
import com.example.searchapp.domain.model.characters.Character
import com.example.searchapp.ui.home.components.CharacterItem
import com.example.searchapp.ui.theme.SearchAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    context: Context
) {
    val state = viewModel.state
    val eventFlow = viewModel.eventFlow
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(scaffoldState.snackbarHostState) {
        eventFlow.collect { event ->
            when (event) {
                is HomeViewModel.UIEvent.ShowSnackBar -> event.message.let {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.asString(context)
                    )
                }
            }
        }
    }
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
        content = { innerPadding ->
            HomeContent(
                modifier = Modifier.padding(innerPadding),
                input = state.input,
                characters = state.characters,
                requireMoreCharacters = { input, position -> viewModel.requireMoreCharacters(input,position) } ,
                onEvent = { viewModel.onEvent(it) }
            )
        }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    characters: List<Character> = emptyList(),
    input: String,
    requireMoreCharacters: (String, Int) -> Unit,
    onEvent: (HomeEvent) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = input,
                    placeholder = { Text(stringResource(R.string.name)) },
                    onValueChange = { onEvent(HomeEvent.EnteredCharacter(it)) },
                    textStyle = MaterialTheme.typography.body2,
                    trailingIcon = {
                        IconButton(onClick = { onEvent(HomeEvent.GetCharacters(input)) }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    }
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(vertical = 5.dp, horizontal = 4.dp),
                content = {
                    itemsIndexed(items = characters) { index, character ->
                        requireMoreCharacters(input, index)
                        CharacterItem(item = character)
                    }
                }
            )
        }
        if (isLoading) FullScreenLoading()
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun PreviewHomeContent() {
    SearchAppTheme {
        HomeContent(
            input = " ",
            requireMoreCharacters = { _,_ -> },
            onEvent = { }
        )
    }
}