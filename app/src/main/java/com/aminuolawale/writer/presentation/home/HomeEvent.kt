package com.aminuolawale.writer.presentation.home

sealed class HomeEvent{
    data class DeleteCanvas(val id:Int): HomeEvent()
}
