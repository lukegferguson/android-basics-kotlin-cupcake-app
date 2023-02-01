package com.example.cupcake.model

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val  PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {

    //Total quantity of cupcakes ordered, all flavors combined
    private val _quantity = MutableLiveData(0)
    val quantity: LiveData<Int> = _quantity

    //Map including flavor and quantity of each flavor
    private val _flavor = MutableLiveData<MutableMap<String, Int>>()
    val flavor: LiveData<MutableMap<String, Int>> = _flavor

    //Date cupcakes will be picked up
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    //Total price of cupcake order
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {

        NumberFormat.getCurrencyInstance().format(it)
    }

    //List of dates that cupcakes can be picked up
    val dateOptions: List<String> = getPickupOptions()

    //Name of person ordering cupcakes
    private val _userName = MutableLiveData<String>()
    val userName = _userName

    //Phone number of user ordering cupcakes
    private val _userPhone = MutableLiveData<String>()
    val userPhone = _userPhone



    init {
        resetOrder()
    }


    //Set total quantity of cupcakes to be ordered
    private fun setQuantity(){
        var tempQuantity: Int = 0

            for ((flavors, q) in flavor.value!!){
                tempQuantity += flavor.value!![flavors]!!
            }
            _quantity.value = tempQuantity
            updatePrice()
    }

    fun setFlavorAndQuantity(flavor: String, flavorQuantity: Int){

        _flavor.value!![flavor] = flavorQuantity

        setQuantity()

    }

    //Returns a list of cupcake flavors ordered and the quantity of each
    fun orderedFlavors(): List<String>{
        val orderedFlavors = mutableListOf<String>()

            for (flavors in flavor.value!!) {
                if (flavors.value > 0) {
                    orderedFlavors.add(flavors.toString().replace("="," - "))
                }
            }
        return orderedFlavors
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


    //Returns a list of 4 dates, starting with today, that cupcakes can be picked up
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


    //Blanks out all data fields so user can start a new order
    fun resetOrder() {
        _quantity.value = 0
        _date.value = dateOptions[0]
        _price.value = 0.0
        _userName.value = ""
        _userPhone.value = ""
        _flavor.value = emptyMap<String, Int>().toMutableMap()

    }

}