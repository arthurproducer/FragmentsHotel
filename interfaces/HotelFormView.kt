package fragmentshotel.android.dominando.fragmentshotel.interfaces

import fragmentshotel.android.dominando.fragmentshotel.model.Hotel

interface HotelFormView{
    fun showHotel(hotel: Hotel)
    fun errorInvalidHotel()
    fun errorSaveHotel()
    }