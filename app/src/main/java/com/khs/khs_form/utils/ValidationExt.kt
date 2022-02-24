package com.khs.khs_form.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khs.khs_form.R
import com.khs.khs_form.utils.UIInputValidation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

fun String?.validate(
    extraCondition: (() -> UIInputValidation)? = null
): UIInputValidation = when {
    isNullOrBlank() -> ValueRequired
    else -> extraCondition?.invoke() ?: ValidationPassed
}

fun String?.validateWithPattern(
    @StringRes msg: Int,
    pattern: Pattern
): UIInputValidation = validate(
    extraCondition = {
        if (pattern.matcher(this.toString()).matches())
            ValidationPassed else Invalid(msg)
    }
)

@RequiresApi(Build.VERSION_CODES.O)
fun String?.validateDate(): UIInputValidation = when {
    isNullOrBlank() -> ValueRequired
    else -> try {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        LocalDate.parse(this, dateFormatter)
        ValidationPassed
    } catch (e: Exception) {
        Invalid(R.string.error_date)
    }
}

fun validateValues(
    vararg params: Pair<LiveData<UIInputValidation>, MutableLiveData<*>>
): Boolean {
    var result = true

    params.onEach { param ->
        with(param.first) {
            value?.let {
                result = result && (it is ValidationPassed)
            } ?: param.second.apply {
                value = null
                result = false
            }
        }
    }

    return result
}