package fragmentshotel.android.dominando.fragmentshotel

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import fragmentshotel.android.dominando.fragmentshotel.model.Hotel
import fragmentshotel.android.dominando.fragmentshotel.view.hotelDetails.HotelDetailsActivity
import fragmentshotel.android.dominando.fragmentshotel.view.hotelDetails.HotelDetailsFragment
import fragmentshotel.android.dominando.fragmentshotel.view.hotelList.HotelListFragment

class HotelActivity : AppCompatActivity(),
        HotelListFragment.OnHotelClickListener,
        HotelFormFragment.OnHotelSavedListener,
        SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener {

    private var lastSearchTerm : String = ""
    private var searchView: SearchView? = null

    private val listFragment: HotelListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as HotelListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(EXTRA_SEARCH_TERM,lastSearchTerm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        lastSearchTerm = savedInstanceState?.getString(EXTRA_SEARCH_TERM)?:""
    }

    override fun onHotelClick(hotel: Hotel) {
        if (isTablet()) {
            showDetailsFragment(hotel.id)
        } else {
            showDetailsActivity(hotel.id)
        }
    }

    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    private fun showDetailsActivity(hotelId: Long) {
        HotelDetailsActivity.open(this, hotelId)
    }

    private fun showDetailsFragment(hotelId: Long){
        val fragment = HotelDetailsFragment.newInstance(hotelId)
        searchView?.setOnQueryTextListener(null)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.details,fragment, HotelDetailsFragment.TAG_DETAILS)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel,menu)
        val searchItem  = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if(lastSearchTerm.isNotEmpty()){
            Handler().post{
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query,true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId){
            R.id.action_info ->
                AboutDialogFragment().show(supportFragmentManager,"sobre")
            R.id.action_new ->
                HotelFormFragment.newInstance().open(supportFragmentManager)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(p0: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        listFragment.search(lastSearchTerm)
        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?) = true

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch() //para voltar ao normal
        return true
    }

    companion object {
        const val EXTRA_SEARCH_TERM = "lastSearch"
    }

    override fun onHotelSaved(hotel: Hotel) {
        listFragment.search(lastSearchTerm)
    }
}
