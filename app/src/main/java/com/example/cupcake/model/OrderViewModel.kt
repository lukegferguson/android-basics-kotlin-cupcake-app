package com.example.cupcake.model

import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.cupcake.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val  PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<MutableMap<String, Int>>()
    val flavor: LiveData<MutableMap<String, Int>> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {

        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions: List<String> = getPickupOptions()

    private val _userName = MutableLiveData<String>()
    val userName = _userName

    private val _userPhone = MutableLiveData<String>()
    val userPhone = _userPhone


    init {
        resetOrder()
    }


    fun setQuantity(numberCupcakes: Int){
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor(flavor: String, flavorQuantity: Int){
        if (flavorQuantity != 0) _flavor.value!![flavor] = flavorQuantity
        //TODO Need to be allow user to put value back to zero and/or if value is zero remove from map

        Log.d("flavorpls", "Flavor: $flavor.value., Quantity from map: ${_flavor.value?.get("Vanilla")}")
    }

    fun setDate(pickupDate: String){
        _date.value = pickupDate
        updatePrice()
    }


    fun setUserName(name: String){
        _userName.value = name
    }


    fun setUserPhone(phone: String){
        _userPhone.value = phone
    }

    //Handle null values for phone, else format phone number
    fun formatPhone(){
        if (_userPhone.value.isNullOrEmpty()) _userPhone.value = "None"
        else if (_userPhone.value == "None") _userPhone.value = "None"
        else _userPhone.value = PhoneNumberUtils.formatNumber(_userPhone.value, "US")
    }

    //Set user name to "none" if user does not enter a value
    fun formatName(){
        if (_userName.value.isNullOrEmpty()) _userName.value = "None"
    }

    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE

        //Check if pickup date is today and add additional charge to total price
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }

        _price.value = calculatedPrice
    }


    private fun getPickupOptions(): List<String>{
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        //Create a list of dates starting with today and include next 3 days
        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }


    fun resetOrder() {
        _quantity.value = 0
        _date.value = dateOptions[0]
        _price.value = 0.0
        _userName.value = ""
        _userPhone.value = ""
        _flavor.value = emptyMap<String, Int>().toMutableMap()

/* Potential alternate method to reset _flavor
        if (_flavor.value.isNullOrEmpty()) {
            for (flavor in _flavor.value!!.keys) {
                _flavor.value!!.remove(flavor)
            }
        }*/
    }

}