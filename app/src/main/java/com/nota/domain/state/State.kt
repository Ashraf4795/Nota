package com.nota.domain.state

sealed class State

object Loading: State()
data class Success <T> (val data: T): State()
data class Error(val exception: Exception): State()
object Empty: State()
