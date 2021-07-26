package com.remember.gallery.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.extension.showError
import com.remember.common.widget.RememberToolbar
import com.remember.gallery.R
import com.remember.gallery.adapter.RecyclerViewAdapterPhoto
import com.remember.gallery.model.Image
import com.remember.repository.model.Album
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseChoosePhotoFragment : Fragment(R.layout.gallery_photos_fragment) {

    private val viewModel: PhotosViewModel by viewModel()
    protected val root: ViewGroup by viewProvider(R.id.root)
    private val recyclerView: RecyclerView by viewProvider(R.id.photos)
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_photos)
    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    abstract val album: Album

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        album.name?.let {
            toolbar.setTitle(if(it.length >= 15) "${it.take(15)}..." else it)
        }
        toolbar.setOnClickListenerBackIcon {
            onToolbarBackIcon()
        }
        setupViewStateMachine()
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModel.photosByAlbum(requireContext(), album).observe(viewLifecycleOwner) {
            data {
                setupPhotos(it)
                stateData()
            }
            showLoading(withData = false) {
                stateLoading()
            }
            error { _ ->
                requireActivity().showError(R.string.gallery_photos_empty_text, root)
            }
        }
    }

    abstract fun onToolbarBackIcon()

    abstract fun onClickImage(image: Image)

    protected fun stateData() {
        viewStateMachine.changeState(stateData)
    }

    protected fun stateLoading() {
        viewStateMachine.changeState(stateLoading)
    }

    private fun setupPhotos(photos: List<Image>) {
        val screen = requireActivity().window.decorView.width - 32.px(requireContext()) - 8.px(requireContext())
        val size = (screen / 3)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = RecyclerViewAdapterPhoto(photos, size, ::onClickImage)
        viewStateMachine.changeState(stateData)
    }

    protected fun notPossibleToSelectPhoto() {
        showError(R.string.gallery_photo_no_possible_to_select_photo, root)
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(recyclerView)
            }

            state(stateData) {
                visibles(recyclerView)
                gones(shimmer)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }
}

fun Int.px(context: Context) = (this * context.resources.displayMetrics.density).toInt()
