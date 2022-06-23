package gini.ohadsa.day23.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.day23.R
import gini.ohadsa.day23.databinding.FragmentHomeBinding
import gini.ohadsa.day23.models.response.FavoriteGroup
import gini.ohadsa.day23.models.response.MediaGroupList
import gini.ohadsa.day23.models.response.TmdbItem
import gini.ohadsa.day23.ui.adapters.AdapterRecycler
import gini.ohadsa.day23.ui.adapters.MediaDiffUtils
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var homeViewModel: HomeViewModel
    lateinit var favoriteViewModel: FavoriteGroup

    @Inject
    lateinit var adapterRecycler: AdapterRecycler

    @Inject
    lateinit var diffUtil: MediaDiffUtils


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel =
            ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        favoriteViewModel =
            ViewModelProvider(requireActivity())[FavoriteGroup::class.java]



        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.initApi()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.moviesRecycle.layoutManager = GridLayoutManager(requireActivity(), 1)
        binding.moviesRecycle.itemAnimator = DefaultItemAnimator()

        homeViewModel.listLiveData.observe(viewLifecycleOwner) {


            binding.moviesRecycle.adapter = getAdapterRecycler(it)
        }


    }

    private fun getAdapterRecycler(mediaList: MediaGroupList): AdapterRecycler {

        adapterRecycler.onFavClicked = AdapterRecycler.OnFavClicked(::updateFavModel)
        adapterRecycler.listener = AdapterRecycler.SetRecyclersOnBindViewHolder(::makeGrid)
        adapterRecycler.onItemClickedListener = AdapterRecycler.OnItemClicked(::itemCallback)
        adapterRecycler.diffUtil = diffUtil
        mediaList.list.forEach {
            adapterRecycler.addItem(it)
        }
        return adapterRecycler
    }

    private fun makeGrid(): GridLayoutManager {
        val grid = GridLayoutManager(requireActivity(), 1)
        grid.orientation = GridLayoutManager.HORIZONTAL
        return grid
    }

    private fun updateFavModel(item: TmdbItem, pos: Int) {
        homeViewModel.updateFavorite(item, pos)
        favoriteViewModel.updateFavorite(item, pos)
    }

    private fun itemCallback(item: TmdbItem, pos: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToMediaFragment3(
                item,
                R.id.navigation_home
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}