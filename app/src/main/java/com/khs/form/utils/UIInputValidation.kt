package com.khs.form.utils

import androidx.annotation.StringRes

sealed class UIInputValidation {

    object ValueRequired : UIInputValidation()

    class Invalid(
        @StringRes val msg: Int
    ) : UIInputValidation()

    object ValidationPassed : UIInputValidation()
}