package com.aminuolawale.writer.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminuolawale.writer.domain.model.WritingCanvas
import com.aminuolawale.writer.domain.repository.WritingCanvasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val writingCanvasRepository: WritingCanvasRepository) : ViewModel() {
    private var _state = mutableStateOf<List<WritingCanvas>>(emptyList())
    var state: State<List<WritingCanvas>> = _state

    private val _eventFlow  = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            writingCanvasRepository.getCanvases().collect{
                _state.value = it
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.DeleteCanvas -> {
                viewModelScope.launch {
                    writingCanvasRepository.deleteCanvas(event.id)
                    _eventFlow.emit(UiEvent.CanvasDeleted)
                }
            }
        }
    }
}

sealed class UiEvent {
    object CanvasDeleted: UiEvent()
}