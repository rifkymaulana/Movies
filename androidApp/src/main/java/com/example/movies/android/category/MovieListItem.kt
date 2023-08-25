package com.example.movies.android.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movies.android.R
import com.example.movies.android.data.Movie
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MovieListItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (Movie) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onMovieClick(movie) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center
            ) {
                val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"

                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 2.dp, bottomEnd = 2.dp))
                )

                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier
                        .size(50.dp),
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.play_button),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.FillBounds
                    )
                }

            }

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = movie.title.take(25) + if (movie.title.length > 25) "..." else "",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val outputFormatter = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)

                val releaseDate = inputFormatter.parse(movie.release_date)
                val formattedDate = outputFormatter.format(releaseDate)

                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}