package com.aminuolawale.writer.presentation.writing_canvas

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminuolawale.writer.domain.model.WritingCanvas
import com.aminuolawale.writer.domain.repository.WritingCanvasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class WritingCanvasViewModel @Inject constructor(
    private val writingCanvasRepository: WritingCanvasRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private var _state = mutableStateOf(WritingCanvasState())
    val state: State<WritingCanvasState> = _state
    private var canvasId: Long? = null

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("canvasId")?.let { it ->
            if (it != -1){
                viewModelScope.launch {
                   val canvas= writingCanvasRepository.getCanvasById(it)
                    _state.value = _state.value.copy(lines= canvas?.lines?:emptyList())
                    canvasId  = canvas?.id?.toLong()
                }
            }
        }
    }
    fun onEvent(event: WritingCanvasEvent) {
        when (event) {
            is WritingCanvasEvent.Draw -> {
                _state.value = _state.value.copy(lines = _state.value.lines + event.line)
            }

            is WritingCanvasEvent.ChangeDrawMode -> {
                _state.value =
                    _state.value.copy(isErasing = event.drawingMode is DrawingMode.IsErasing)
            }

            is WritingCanvasEvent.SaveCanvas -> {
                viewModelScope.launch {
                    canvasId = writingCanvasRepository.insertCanvas(
                        WritingCanvas(
                            id = canvasId?.toInt(),
                            lines = _state.value.lines,
                            dateCreated = System.currentTimeMillis(),
                            lastUpdated = System.currentTimeMillis()
                        )
                    )
                    _eventFlow.emit(UiEvent.CanvasSaved)
                }
            }
        }
    }

    sealed class UiEvent {
        object CanvasSaved : UiEvent()
    }
}