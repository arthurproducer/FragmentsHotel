package fragmentshotel.android.dominando.fragmentshotel

import fragmentshotel.android.dominando.fragmentshotel.HotelValidator
import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelFormView
import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelRepository
import fragmentshotel.android.dominando.fragmentshotel.model.Hotel

class HotelFormPresenter (
    private val view: HotelFormView,
            private val repository: HotelRepository

){
    private val validator = HotelValidator()

   fun loadHotel(id: Long){
        repository.hotelById(id){hotel ->
            if(hotel != null){
                view.showHotel(hotel)
            }
        }
    }
    fun saveHotel(hotel: Hotel): Boolean{
        return if (validator.validate(hotel)) {

            repository.save(hotel)
            true

        }else{
             view.errorInvalidHotel()
            false
            }
    }

}