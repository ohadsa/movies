package gini.ohadsa.day23.ui.media

import android.net.Uri
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import gini.ohadsa.day23.R
import gini.ohadsa.day23.databinding.FragmentMediaBinding
import gini.ohadsa.day23.models.response.FavoriteGroup
import gini.ohadsa.day23.networking.POSTER_HEADER
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MediaFragment : Fragment() {


    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!
    private val args: MediaFragmentArgs by navArgs()
    private lateinit var mediaViewModel: MediaViewModel
    private lateinit var favViewModel: FavoriteGroup


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mediaViewModel = ViewModelProvider(this)[MediaViewModel::class.java]
        favViewModel = ViewModelProvider(requireActivity())[FavoriteGroup::class.java]

        mediaViewModel.media = args.media

        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.include.backButton.setOnClickListener {

            findNavController().navigate(args.backTo)
        }
        mediaViewModel.title.observe(viewLifecycleOwner) {
            binding.include.movieTitle.text = it
        }
        mediaViewModel.overView.observe(viewLifecycleOwner) {
            binding.include.movieOverView.text = it
        }
        mediaViewModel.rating.observe(viewLifecycleOwner) {
            binding.include.movieRating.rating = it.div(2)
            binding.include.movieDuration.text = it.toString()
        }

        mediaViewModel.poster_path.observe(viewLifecycleOwner) {
            Picasso
                .get()
                .load(Uri.parse("$POSTER_HEADER${it}"))
                .into(binding.include.movieImage)
        }
        mediaViewModel.isFavorite.observe(viewLifecycleOwner){
            if (it) binding.include.favorite.setImageResource(R.drawable.ic_heart_filled)
            else binding.include.favorite.setImageResource(R.drawable.ic_heart_empty)
        }

        binding.include.favorite.setOnClickListener{
            mediaViewModel.media?.let { item->
                item.isFavorite = !item.isFavorite
                favViewModel.updateFavorite(item , DEFAULT )
                if (item.isFavorite) binding.include.favorite.setImageResource(R.drawable.ic_heart_filled)
                else binding.include.favorite.setImageResource(R.drawable.ic_heart_empty)
            }

        }


        val youTubePlayerView: YouTubePlayerView = binding.include.youtube
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                mediaViewModel.getTreiler {
                    youTubePlayer.loadVideo(it, 0f)
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}