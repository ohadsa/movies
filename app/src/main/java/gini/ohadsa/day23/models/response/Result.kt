package gini.ohadsa.day23.models.response

sealed class Result<out T:Any>

data class Success<out T:Any>(val data:T ):Result<T>()
data class Failure(val exc : Throwable? ):Result<Nothing>()