package fragmentshotel.android.dominando.fragmentshotel.view.hotelList

import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelListView
import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelRepository
import fragmentshotel.android.dominando.fragmentshotel.model.Hotel

class HotelListPresenter(
        private val view: HotelListView,
        private val repository: HotelRepository
) {
    fun searchHotels(term: String){
        repository.search(term){hotels ->
            view.showHotels(hotels)
        }
    }
    fun showHotelDetais(hotel: Hotel){
        view.showHotelDetails(hotel)
    }
}