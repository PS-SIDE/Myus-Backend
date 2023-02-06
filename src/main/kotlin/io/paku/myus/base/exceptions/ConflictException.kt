package io.paku.myus.base.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class ConflictException(
    override val message: String? = null
): RuntimeException(message)