package com.khs.khs_form.binding

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.khs.khs_form.R
import com.khs.khs_form.utils.UIInputValidation
import com.khs.khs_form.utils.UIInputValidation.*

@BindingAdapter(
    value = ["imageUrl", "image", "resImage", "imageUri"],
    requireAll = false
)
fun setImage(
    imageView: AppCompatImageView,
    imageUrl: String?,
    image: Drawable?,
    @DrawableRes resImage: Int?,
    imageUri: Uri?
) {

    Glide.with(imageView.context)
        .load(image ?: (resImage ?: (imageUrl ?: imageUri)))
        .into(imageView)
}

@BindingAdapter("validate")
fun validateInputField(
    textInputLayout: TextInputLayout,
    validation: UIInputValidation?
) {
    validation?.let {
        with(textInputLayout) {
            when (it) {
                is ValueRequired -> error = context.getString(R.string.error_value_required)
                is Invalid -> error = context.getString(it.msg)
                is ValidationPassed -> {
                    error = null
                    isErrorEnabled = false
                }
            }
        }
    }
}

