package fragmentshotel.android.dominando.fragmentshotel.view.hotelList

import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelListView
import fragmentshotel.android.dominando.fragmentshotel.interfaces.HotelRepository
import fragmentshotel.android.dominando.fragmentshotel.model.Hotel
import javax.security.auth.callback.Callback

class HotelListPresenter(
        private val view: HotelListView,
        private val repository: HotelRepository
) {
    private val deletedItems = mutableListOf<Hotel>()
    private var lastTerm = "" //último termo pesquisado
    private var inDeleteMode = false //Para descobrir se a tela se encontra em modo de exclusão
    private val selectedItems = mutableListOf<Hotel>() //Hoteis selecionados para serem excluidos


    fun searchHotels(term: String){
        lastTerm = term //ultimo termo pesquisado é atualizado a cada nova pesquisa
        repository.search(term){hotels ->
            view.showHotels(hotels)
        }
    }

    fun selectHotel(hotel: Hotel){
        if(inDeleteMode){
            toggleHotelSelected(hotel)
            if(selectedItems.size == 0){
                view.hideDeleteMode()
            } else {
                view.updateSelectionCountText(selectedItems.size)
                view.showSelectedHotels(selectedItems)
            }
        } else{
         view.showHotelDetails(hotel)
        }
    }


    private fun toggleHotelSelected(hotel: Hotel){
        val existing = selectedItems.find{ it.id == hotel.id}
        if(existing == null){
            selectedItems.add(hotel)
        } else{
            selectedItems.removeAll { it.id == hotel.id }
        }
    }

    fun showDeleteMode(){
        inDeleteMode = true
        view.showDeleteMode()
    }

    fun hideDeleteMode(){
        inDeleteMode = false
        selectedItems.clear()
        view.hideDeleteMode()
    }

    fun refresh(){
        searchHotels(lastTerm)
    }

    fun showHotelDetais(hotel: Hotel){
        view.showHotelDetails(hotel)
    }

    fun init(){
        if(inDeleteMode){
            showDeleteMode()
            view.updateSelectionCountText(selectedItems.size)
            view.showSelectedHotels(selectedItems)
        }else{
            refresh()
        }
    }

    fun deleteSelected(callback: (List<Hotel>) -> Unit){
        repository.remove(*selectedItems.toTypedArray())
        deletedItems.clear()
        deletedItems.addAll(selectedItems)
        refresh()
        callback(selectedItems)
        hideDeleteMode()
        view.showMessageHotelsDeleted(deletedItems.size)
    }

    fun undoDelete(){
        if(deletedItems.isNotEmpty()){
            for(hotel in deletedItems){
                repository.save(hotel)
            }
            searchHotels(lastTerm)
        }
    }
}