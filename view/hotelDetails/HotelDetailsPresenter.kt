package fragmentshotel.android.dominando.fragmentshotel.view.hotelDetails

import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelDetailsView
import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelRepository

class HotelDetailsPresenter(
        private val view: HotelDetailsView,
        private val repository: HotelRepository
) {
    fun loadHotelDetails(id: Long){
        repository.hotelById(id){ hotel ->
            if(hotel != null){
                view.showHotelDetails(hotel)
            } else{
                view.errorHotelNotFound()
            }
        }
    }
}