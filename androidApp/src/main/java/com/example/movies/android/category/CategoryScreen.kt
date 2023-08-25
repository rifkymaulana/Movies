package com.example.movies.android.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movies.android.Red
import com.example.movies.android.data.AUTHORIZATION_HEADER
import com.example.movies.android.data.Movie
import com.example.movies.android.data.Movies
import com.example.movies.android.data.movieApiService
import com.example.movies.android.home.HomeScreenState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryScreen(navigateToDetail: (com.example.movies.domain.model.Movie) -> Unit) {
    var movieNowPlaying by remember { mutableStateOf(emptyList<Movie>()) }
    var moviePopular by remember { mutableStateOf(emptyList<Movie>()) }
    var movieTopRated by remember { mutableStateOf(emptyList<Movie>()) }
    var movieUpcoming by remember { mutableStateOf(emptyList<Movie>()) }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                val response: Movies = movieApiService.getNowPlaying(
                    AUTHORIZATION_HEADER
                )
                movieNowPlaying = response.results ?: emptyList()

                val response2: Movies = movieApiService.getPopular(
                    AUTHORIZATION_HEADER
                )
                moviePopular = response2.results ?: emptyList()

                val response3: Movies = movieApiService.getTopRated(
                    AUTHORIZATION_HEADER
                )
                movieTopRated = response3.results ?: emptyList()

                val response4: Movies = movieApiService.getUpcoming(
                    AUTHORIZATION_HEADER
                )
                movieUpcoming = response4.results ?: emptyList()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Now Playing",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        movieNowPlaying,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = {
                            // convert movie data class to movie domain model
                            val movieDomainModel = com.example.movies.domain.model.Movie(
                                id = movie.id,
                                title = movie.title,
                                imageUrl = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                                description = movie.overview,
                                releaseDate = movie.release_date
                            )
                            navigateToDetail(movieDomainModel)
                        })
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Popular",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        moviePopular,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = {
                            // convert movie data class to movie domain model
                            val movieDomainModel = com.example.movies.domain.model.Movie(
                                id = movie.id,
                                title = movie.title,
                                imageUrl = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                                description = movie.overview,
                                releaseDate = movie.release_date
                            )
                            navigateToDetail(movieDomainModel)
                        })
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Top Rated",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        movieTopRated,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = {
                            // convert movie data class to movie domain model
                            val movieDomainModel = com.example.movies.domain.model.Movie(
                                id = movie.id,
                                title = movie.title,
                                imageUrl = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                                description = movie.overview,
                                releaseDate = movie.release_date
                            )
                            navigateToDetail(movieDomainModel)
                        })
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upcoming",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        movieUpcoming,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = {
                            // convert movie data class to movie domain model
                            val movieDomainModel = com.example.movies.domain.model.Movie(
                                id = movie.id,
                                title = movie.title,
                                imageUrl = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                                description = movie.overview,
                                releaseDate = movie.release_date
                            )
                            navigateToDetail(movieDomainModel)
                        })
                    }
                }
            }
        }
    }
}
