package fragmentshotel.android.dominando.fragmentshotel.interfaces

import fragmentshotel.android.dominando.fragmentshotel.model.Hotel

interface HotelListView{
    fun showHotels(hotels: List<Hotel>)
    fun showHotelDetails(hotel : Hotel)
    fun showDeleteMode()
    fun hideDeleteMode()
    fun showSelectedHotels(hotels: List<Hotel>)
    fun updateSelectionCountText(count: Int)
    fun showMessageHotelsDeleted(count: Int)
}