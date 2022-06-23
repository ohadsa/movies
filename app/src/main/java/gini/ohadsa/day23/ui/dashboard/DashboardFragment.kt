package gini.ohadsa.day23.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.day23.R
import gini.ohadsa.day23.databinding.FragmentDashboardBinding
import gini.ohadsa.day23.models.response.FavoriteGroup
import gini.ohadsa.day23.models.response.TmdbItem
import gini.ohadsa.day23.ui.adapters.AdapterItem
import gini.ohadsa.day23.ui.adapters.MediaDiffUtils
import gini.ohadsa.day23.ui.home.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : SearchView.OnQueryTextListener , Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : AdapterItem

    private lateinit var dashboardViewModel : DashboardViewModel
    private lateinit var favViewModel : FavoriteGroup
    private lateinit var homeViewModel : HomeViewModel

    @Inject
    lateinit var diffUtil : MediaDiffUtils
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        favViewModel = ViewModelProvider(requireActivity()).get(FavoriteGroup::class.java)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)


        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val grid = GridLayoutManager(requireActivity(), 3)
        grid.orientation =  GridLayoutManager.VERTICAL
        binding.serchRecycler.layoutManager = grid

        adapter = AdapterItem( ::updateFavModel , R.layout.movie_card_search, ::itemCallback ,diffUtil )
        binding.serchRecycler.adapter = adapter

        dashboardViewModel.listLiveData.observe(viewLifecycleOwner){
            favViewModel.updateListIfInFavorite(it)
            adapter.updateList(it)
        }
        binding.editTextTextPersonName.isSubmitButtonEnabled = true
        binding.editTextTextPersonName.setOnQueryTextListener(this)
        return root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchForItem(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchForItem(query)
            lifecycleScope.launch {
                delay(1000)
            }
        }
        return true
    }

    private fun searchForItem(query: String) {
        dashboardViewModel.getMoviesByQuery(query)
        //dashboardViewModel.getTVShowByQuery(query)
    }

    private fun updateFavModel(item: TmdbItem, pos: Int) {
        homeViewModel.updateFavorite(item, pos )
        favViewModel.updateFavorite(item , pos)
    }

    private fun itemCallback(item: TmdbItem, pos: Int) {
        findNavController().navigate(
            DashboardFragmentDirections.actionNavigationDashboardToMediaFragment(
                item,
                R.id.navigation_dashboard
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}