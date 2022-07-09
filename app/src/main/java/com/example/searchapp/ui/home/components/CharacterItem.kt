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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.searchapp.R
import com.example.searchapp.domain.model.characters.Character

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    item: Character,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            CharacterImageContainer(modifier = Modifier.size(64.dp)) {
                CharacterImage(item.image)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier.fillMaxWidth()) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "${item.status} + ${item.species}",
                    style = MaterialTheme.typography.body2
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = stringResource(R.string.first_seen_in))
                }
                Text(
                    text = item.origin.name,
                    style = MaterialTheme.typography.body2
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = stringResource(R.string.last_known_location))
                }
                Text(
                    text = item.location.name,
                    style = MaterialTheme.typography.body2
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