package mx.inigofrabasa.specialmovies.utils

sealed class State {
    object InProgress : State()
    data class Success(val data: List<String>) : State()
}