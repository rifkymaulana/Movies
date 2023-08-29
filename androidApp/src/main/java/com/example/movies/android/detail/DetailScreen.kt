package com.example.movies.android.detail

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier, uiState: DetailScreenState
) {
    val context = LocalContext.current
    val application = context.applicationContext as Movie
    val database = application.database
    val movieFavDao = database.movieFavDao()

    var movieList by remember { mutableStateOf<List<com.example.movies.domain.model.Movie>?>(null) }

    // Show the automatic pop-up message for 3 seconds
    val scope = rememberCoroutineScope()
    var showMessage by remember { mutableStateOf(false) }

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
            // check if movie is in favorite list
            val isMovieInFavoriteList = movieList?.any { movieFav ->
                movieFav.id == movie.id
            } ?: false

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

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = movie.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxSize(0.5f)
                    )

                    if (showMessage) {
                        Surface(
                            color = Color.Transparent, modifier = Modifier.size(50.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = if (isMovieInFavoriteList) Color.Red else Color.White,
                            )
                        }
                    }
                }

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


                    if (isMovieInFavoriteList) {
                        Button(
                            onClick = {
                                val database = (context.applicationContext as Movie).database
                                val movieFavDao = database.movieFavDao()
                                val accountDao = database.accountDao()
                                showMessage = true
                                if (showMessage) {
                                    scope.launch {
                                        delay(3000) // Delay for 3 seconds
                                        showMessage = false
                                    }
                                }

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

                                        movieFavDao.deleteMovieFav(movie.id)

                                        withContext(Dispatchers.Main) {
                                            val movieFavList =
                                                movieFavDao.getAllByUserId(accountLogin!!.id)

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
                                }

                                Toast.makeText(
                                    context, "Movie deleted from favorite", Toast.LENGTH_SHORT
                                ).show()
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
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = Color.White,
                            )
                            Spacer(modifier = modifier.width(8.dp))

                            Text(text = "Delete from Favorite", color = Color.White)
                        }
                    } else {
                        Button(
                            onClick = {
                                val database = (context.applicationContext as Movie).database
                                val movieFavDao = database.movieFavDao()
                                val accountDao = database.accountDao()
                                showMessage = true
                                if (showMessage) {
                                    scope.launch {
                                        delay(3000) // Delay for 3 seconds
                                        showMessage = false
                                    }
                                }

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

                                        withContext(Dispatchers.Main) {
                                            val movieFavList =
                                                movieFavDao.getAllByUserId(accountLogin!!.id)

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
                                }

                                Toast.makeText(
                                    context, "Movie added to favorite", Toast.LENGTH_SHORT
                                ).show()
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
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.White,
                            )
                            Spacer(modifier = modifier.width(8.dp))

                            Text(text = "Add to Favorite", color = Color.White)
                        }
                    }

                    Spacer(modifier = modifier.height(16.dp))

                    val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    val outputFormatter = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)

                    val releaseDate = inputFormatter.parse(movie.releaseDate)
                    val formattedDate = outputFormatter.format(releaseDate)

                    Text(
                        text = "Released in $formattedDate",
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
