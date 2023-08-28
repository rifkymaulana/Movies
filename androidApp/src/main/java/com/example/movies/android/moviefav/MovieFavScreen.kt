package com.example.movies.android.moviefav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movies.android.home.MovieListItem
import com.example.movies.android.login.accountLogin
import com.example.movies.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MovieFavScreen(
    modifier: Modifier = Modifier, navigateToDetail: (Movie) -> Unit
) {
    val context = LocalContext.current
    val application = context.applicationContext as com.example.movies.android.Movie
    val database = application.database
    val movieFavDao = database.movieFavDao()

    var movieList by remember { mutableStateOf<List<Movie>?>(null) }

    LaunchedEffect(key1 = true) {
        CoroutineScope(Dispatchers.IO).launch {
            val movieFavList = movieFavDao.getAllByUserId(accountLogin!!.id)

            val mappedMovieList = movieFavList?.map { movieFav ->
                Movie(
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
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Favorite Movies",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            if (movieList != null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        movieList!!,
                        key = { _, movie -> movie.id + Math.random() }) { _, movie ->

                        MovieListItem(movie = movie, onMovieClick = { navigateToDetail(movie) })
                    }
                }
            }
        }
    }
}