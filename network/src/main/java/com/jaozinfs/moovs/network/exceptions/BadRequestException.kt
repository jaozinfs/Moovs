package com.jaozinfs.moovs.network.exceptions

import java.lang.Exception

class BadRequestException(
    var code: Int = -1,
    var error: String?,
    cause: Throwable? = null
) :
    Exception(error)