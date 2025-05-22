package com.example.seriesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesapp.model.data.TvShow
import com.example.seriesapp.model.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val repository: SeriesRepository
) : ViewModel() {

    // UI state for the series list
    data class SeriesUiState(
        val series: List<TvShow> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val currentPage: Int = 1,
        val isLoadingMore: Boolean = false
    )

    private val _uiState = MutableStateFlow(SeriesUiState())
    val uiState: StateFlow<SeriesUiState> = _uiState.asStateFlow()

    init {
        loadSeries()
    }

    fun loadSeries() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val result = repository.getMostPopularSeries(1)
                result.fold(
                    onSuccess = { response ->
                        _uiState.update {
                            it.copy(
                                series = response.tvShows,
                                isLoading = false,
                                currentPage = 1
                            )
                        }
                    },
                    onFailure = { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = e.message ?: "Unknown error"
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun loadMoreSeries() {
        val currentState = _uiState.value
        if (currentState.isLoadingMore) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }

            val nextPage = currentState.currentPage + 1
            try {
                val result = repository.getMostPopularSeries(nextPage)
                result.fold(
                    onSuccess = { response ->
                        _uiState.update {
                            it.copy(
                                series = it.series + response.tvShows,
                                isLoadingMore = false,
                                currentPage = nextPage
                            )
                        }
                    },
                    onFailure = { e ->
                        _uiState.update {
                            it.copy(
                                isLoadingMore = false,
                                error = e.message ?: "Unknown error"
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingMore = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}