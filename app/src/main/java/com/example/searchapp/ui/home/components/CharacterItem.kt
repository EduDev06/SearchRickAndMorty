package com.example.searchapp.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.searchapp.R
import com.example.searchapp.domain.model.characters.Character
import com.example.searchapp.domain.model.characters.Location
import com.example.searchapp.domain.model.characters.Origin
import com.example.searchapp.ui.theme.SearchAppTheme

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    item: Character,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Row {
            CharacterImageContainer(modifier = Modifier.size(150.dp)) {
                CharacterImage(item.image)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier.fillMaxWidth()) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = "${item.status} - ${item.species}",
                    style = MaterialTheme.typography.body2
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.first_seen_in),
                        style = MaterialTheme.typography.body2
                    )
                }
                Text(
                    text = item.origin.name,
                    style = MaterialTheme.typography.overline
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.last_known_location),
                        style = MaterialTheme.typography.body2
                    )
                }
                Text(
                    text = item.location.name,
                    style = MaterialTheme.typography.overline
                )
            }
        }
    }
}

@Composable
fun CharacterImage(image: String) {
    Box {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(Size.ORIGINAL)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CharacterImageContainer(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Surface(modifier.aspectRatio(1f), RoundedCornerShape(4.dp)) {
        content()
    }
}

@Preview
@Composable
fun PreviewCharacterItem() {
    SearchAppTheme {
        CharacterItem(
            item = Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "",
                origin = Origin(name = "Earth (C-137)"),
                location = Location(name = "Citadel of Ricks")
            )
        )
    }
}