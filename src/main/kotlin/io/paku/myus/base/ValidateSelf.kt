package io.paku.myus.base

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator

abstract class ValidateSelf<T> {
    private val validate: Validator

    init {
        val factory = Validation.buildDefaultValidatorFactory()
        this.validate = factory.validator
    }

    @Suppress("UNCHECKED_CAST")
    protected fun validateSelf() {
        val violation: Set<ConstraintViolation<T>> = this.validate.validate(this as T)
        if(violation.isNotEmpty()) {
            throw ConstraintViolationException(violation)
        }
    }
}