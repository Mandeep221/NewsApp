package com.msarangal.newsapp.data.remote

// Not so good
//sealed class Response() {
//    object Loading : Response()
//    class Success(val data: NewsResponse) : Response()
//    class Error(val msg: String) : Response()
//}

// Good but could be made even more efficient by using generics
//sealed class Response(val data: NewsResponse? = null, val error: String? = null) {
//    object Loading : Response()
//    data class Success(val response: NewsResponse) : Response(data = response)
//    data class Error(val errorMsg: String) : Response(error = errorMsg)
//}

// generic implementation
sealed class NetworkResult<T>(val data: T? = null, val errorMsg: String? = null) {
    class Loading<T> : NetworkResult<T>()
    class Success<T>(response: T? = null) : NetworkResult<T>(data = response)
    class Error<T>(msg: String) : NetworkResult<T>(errorMsg = msg)
}