package gini.ohadsa.day23.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.day23.R
import gini.ohadsa.day23.databinding.FragmentFavoriteBinding
import gini.ohadsa.day23.models.response.FavoriteGroup
import gini.ohadsa.day23.models.response.TmdbItem
import gini.ohadsa.day23.ui.adapters.AdapterItem
import gini.ohadsa.day23.ui.adapters.MediaDiffUtils
import gini.ohadsa.day23.ui.home.HomeFragmentDirections
import gini.ohadsa.day23.ui.home.HomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    lateinit var favoriteViewModel: FavoriteGroup
    lateinit var homeViewModel: HomeViewModel
    private val binding get() = _binding!!
    lateinit var favAdapterItem: AdapterItem

    @Inject
    lateinit var diffUtil: MediaDiffUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteGroup::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        favAdapterItem = AdapterItem(
            ::onFavClicked,
            R.layout.favorite_card,
            ::onItemClicked,
            diffUtil
        )
        val grid = GridLayoutManager(requireContext(), 1)
        grid.orientation = GridLayoutManager.VERTICAL
        binding.moviesRecycle.layoutManager = grid
        binding.moviesRecycle.adapter = favAdapterItem
        favoriteViewModel.listOfFav.forEach {
            favAdapterItem.addItem(it)
        }
        return root
    }

    private fun onFavClicked(item: TmdbItem, pos: Int) {
        homeViewModel.updateFavorite(item, pos)
        favoriteViewModel.updateFavorite(item, pos)
        if (!item.isFavorite) favAdapterItem.removeItem(item)
    }

    private fun onItemClicked(item: TmdbItem, pos: Int) {
        findNavController().navigate(
            NotificationsFragmentDirections.actionNavigationNotificationsToMediaFragment(
                item,
                R.id.navigation_notifications
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}