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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movies.android.Red
import com.example.movies.android.home.HomeScreenState
import com.example.movies.android.home.MovieListItem
import com.example.movies.domain.model.Movie

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenState,
    loadNextMovies: (Boolean) -> Unit,
    navigateToDetail: (Movie) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.refreshing,
        onRefresh = { loadNextMovies(true) }
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .pullRefresh(state = pullRefreshState)
    ) {
        LazyColumn {
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Welcome to Movies, User!",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        uiState.movies,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = { navigateToDetail(movie) })
                    }

                    if (uiState.loading && uiState.movies.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(color = Red)
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Welcome to Movies, User!",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        uiState.movies,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = { navigateToDetail(movie) })
                    }

                    if (uiState.loading && uiState.movies.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(color = Red)
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Welcome to Movies, User!",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        uiState.movies,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = { navigateToDetail(movie) })
                    }

                    if (uiState.loading && uiState.movies.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(color = Red)
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Welcome to Movies, User!",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        uiState.movies,
                        key = { _, movie -> movie.id }
                    ) { _, movie ->
                        MovieListItem(movie = movie, onMovieClick = { navigateToDetail(movie) })
                    }

                    if (uiState.loading && uiState.movies.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(color = Red)
                            }
                        }
                    }
                }
            }

        }

        PullRefreshIndicator(
            refreshing = uiState.refreshing,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter)
        )
    }
}

