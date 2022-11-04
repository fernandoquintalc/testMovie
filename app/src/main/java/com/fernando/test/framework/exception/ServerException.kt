package com.fernando.test.framework.exception

class ServerException(val code : Int, val error : String) : Exception(error) {
}