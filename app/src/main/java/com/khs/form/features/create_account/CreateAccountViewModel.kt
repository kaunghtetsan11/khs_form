package com.khs.form.features.create_account

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khs.form.R
import com.khs.form.utils.*

class CreateAccountViewModel : ViewModel() {

    val nationalities = listOf(
        "Burmese", "Thai"
    )
    val countries = listOf(
        "Myanmar", "Thailand"
    )

    private val phoneCodes = mapOf(
        "Myanmar" to "+95", "Thailand" to "+66"
    )

    val firstName = MutableLiveData<String?>()
    val lastName = MutableLiveData<String?>()
    val emailAddress = MutableLiveData<String?>()
    val dob = MutableLiveData<String?>()
    val gender = MutableLiveData(0)
    val nationality = MutableLiveData<String?>()
    val country = MutableLiveData<String?>()
    val phoneCode = MutableLiveData<String?>()
    val mobileNo = MutableLiveData<String?>()

    val firstNameValidation = firstName.mapDistinct {
        it.validate()
    }
    val lastNameValidation = lastName.mapDistinct {
        it.validate()
    }
    val emailAddressValidation = emailAddress.mapDistinct {
        it.validateWithPattern(R.string.error_email, Patterns.EMAIL_ADDRESS)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val dobValidation = dob.mapDistinct {
        it.validateDate()
    }
    val nationalityValidation = nationality.mapDistinct {
        it.validate()
    }
    val countryValidation = country.mapDistinct {
        it.validate()
    }

    private val validationParams by lazy {
        arrayOf(
            firstNameValidation to firstName,
            lastNameValidation to lastName,
            emailAddressValidation to emailAddress,
            dobValidation to dob,
            nationalityValidation to nationality,
            countryValidation to country
        )
    }

    fun setPhoneCode(country : String){
        phoneCode.value = phoneCodes[country]
    }

    fun validate(): Boolean = validateValues(*validationParams)
}