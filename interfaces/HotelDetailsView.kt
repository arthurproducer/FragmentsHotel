package fragmentshotel.android.dominando.fragmentshotel.interfaces

import fragmentshotel.android.dominando.fragmentshotel.model.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelNotFound()
}