package com.aminuolawale.writer.presentation.writing_canvas

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminuolawale.writer.domain.model.Line
import com.aminuolawale.writer.domain.model.WritingCanvas
import com.aminuolawale.writer.domain.repository.WritingCanvasRepository
import com.aminuolawale.writer.presentation.utils.Debouncer
import com.aminuolawale.writer.presentation.writing_canvas.utils.HistoryStack
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
    private val debouncer = Debouncer(viewModelScope, 200L, this::saveCanvas)
    private var _state = mutableStateOf(WritingCanvasState())
    val state: State<WritingCanvasState> = _state
    private var canvasId: Long? = null

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private var undoStack: MutableList<Line> = mutableListOf()
    private var historyStack: HistoryStack<Line> =
        HistoryStack { lines ->
            setLinesOnCanvas(lines)
        }

    init {
        savedStateHandle.get<Int>("canvasId")?.let { id: Int ->
            if (id != -1) {
                viewModelScope.launch {
                    val canvas = writingCanvasRepository.getCanvasById(id)
                    _state.value = _state.value.copy(writingCanvas = canvas ?: WritingCanvas())
                    canvasId = canvas?.id?.toLong()
                }
            }
        }
    }

    fun onEvent(event: WritingCanvasEvent) {
        when (event) {
            is WritingCanvasEvent.Draw -> {
                clearLineHistory()
                addNewLineToCanvas(event.line)
                debouncer.execute()
            }

            is WritingCanvasEvent.ChangeDrawMode -> {
                changeDrawMode(event.drawingMode)
            }

            is WritingCanvasEvent.SaveCanvas -> {
                viewModelScope.launch {
                    canvasId = saveCanvas()
                    _eventFlow.emit(UiEvent.CanvasSaved)
                }
            }

            is WritingCanvasEvent.Redo -> {
                historyStack.redo()
                debouncer.execute()
            }

            is WritingCanvasEvent.Undo -> {
                historyStack.undo()
                debouncer.execute()
            }
        }
    }

    private suspend fun saveCanvas(): Long {
        return writingCanvasRepository.insertCanvas(
            _state.value.writingCanvas.copy(id = canvasId?.toInt())
        )
    }

    private fun changeDrawMode(drawingMode: DrawingMode) {
        val newState = _state.value.copy(isErasing = drawingMode is DrawingMode.IsErasing)
        setViewState(newState)
    }

    private fun addNewLineToCanvas(line: Line) {
        val oldWritingCanvas = _state.value.writingCanvas
        setLinesOnCanvas(oldWritingCanvas.lines + line)
    }

    private fun setLinesOnCanvas(lines: List<Line>) {
        val oldWritingCanvas = _state.value.writingCanvas
        val writingCanvas = oldWritingCanvas.copy(lines = lines)
        val newState = _state.value.copy(writingCanvas = writingCanvas)
        setViewState(newState)
    }

    private fun setViewState(newState: WritingCanvasState) {
        _state.value = newState
        historyStack.updateSource(_state.value.writingCanvas.lines)
    }

    private fun clearLineHistory() {
        undoStack = mutableListOf()
    }

    sealed class UiEvent {
        object CanvasSaved : UiEvent()
    }
}