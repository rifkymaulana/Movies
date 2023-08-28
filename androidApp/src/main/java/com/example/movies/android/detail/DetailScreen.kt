package com.example.movies.android.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movies.android.Movie
import com.example.movies.android.R
import com.example.movies.android.Red
import com.example.movies.android.database.MovieFavEntity
import com.example.movies.android.login.accountLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier, uiState: DetailScreenState
) {
    val context = LocalContext.current
    val application = context.applicationContext as com.example.movies.android.Movie
    val database = application.database
    val movieFavDao = database.movieFavDao()

    var movieList by remember { mutableStateOf<List<com.example.movies.domain.model.Movie>?>(null) }

    LaunchedEffect(key1 = true) {
        CoroutineScope(Dispatchers.IO).launch {
            val movieFavList = movieFavDao.getAllByUserId(accountLogin!!.id)

            val mappedMovieList = movieFavList?.map { movieFav ->
                com.example.movies.domain.model.Movie(
                    id = movieFav.movie_id,
                    title = movieFav.title,
                    description = movieFav.overview,
                    imageUrl = movieFav.poster_path,
                    releaseDate = movieFav.release_date
                )
            }

            withContext(Dispatchers.Main) {
                movieList = mappedMovieList
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        uiState.movie?.let { movie ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.movie_detail),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                AsyncImage(
                    model = movie.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxSize(0.5f)
                )

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(20.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(8.dp))

                    // check if movie is in favorite list
                    val isMovieInFavoriteList = movieList?.any { movieFav ->
                        movieFav.id == movie.id
                    } ?: false

                    if (isMovieInFavoriteList) {
                        Text(
                            text = "This movie is in your favorite list",
                            style = MaterialTheme.typography.body2,
                            color = Red
                        )
                    } else {
                        Button(
                            onClick = {
                                val application = context.applicationContext as Movie
                                val database = (application as Movie).database
                                val movieFavDao = database.movieFavDao()
                                val accountDao = database.accountDao()

                                CoroutineScope(Dispatchers.IO).launch {
                                    val account = accountDao.getAccountByIsLogin(true)

                                    account?.let { nonNullAccount ->
                                        val movieFavEntity = MovieFavEntity(
                                            movie_id = movie.id,
                                            title = movie.title,
                                            poster_path = movie.imageUrl,
                                            overview = movie.description,
                                            release_date = movie.releaseDate,
                                            user_id = nonNullAccount.id
                                        )

                                        movieFavDao.insertMovieFav(movieFavEntity)

                                        val movieFavList = movieFavDao.getAllByUserId(accountLogin!!.id)

                                        val mappedMovieList = movieFavList?.map { movieFav ->
                                            com.example.movies.domain.model.Movie(
                                                id = movieFav.movie_id,
                                                title = movieFav.title,
                                                description = movieFav.overview,
                                                imageUrl = movieFav.poster_path,
                                                releaseDate = movieFav.release_date
                                            )
                                        }

                                        withContext(Dispatchers.Main) {
                                            movieList = mappedMovieList
                                        }
                                    }
                                }
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Red
                            ),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.play_button),
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = modifier.width(8.dp))

                            Text(text = "Add to Favorite", color = Color.White)
                        }
                    }

                    Spacer(modifier = modifier.height(16.dp))

                    Text(
                        text = "Released in ${movie.releaseDate}".uppercase(),
                        style = MaterialTheme.typography.overline
                    )

                    Spacer(modifier = modifier.height(4.dp))

                    Text(text = movie.description, style = MaterialTheme.typography.body2)
                }
            }
        }

        if (uiState.loading) {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = Red
                )
            }
        }
    }
}
