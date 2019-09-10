package fragmentshotel.android.dominando.fragmentshotel.interfaces

import fragmentshotel.android.dominando.fragmentshotel.model.Hotel

interface HotelRepository {
    fun save(hotel: Hotel)
    fun remove(vararg hotels: Hotel)
    fun hotelById(id:Long, callback: (Hotel?) -> Unit)
    fun search(term: String, callback:(List<Hotel>) -> Unit)
}