package com.remember.gallery.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
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
import com.remember.gallery.adapter.RecyclerViewAdapterAlbums
import com.remember.repository.model.Album
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseChooseAlbumsFragment : Fragment(R.layout.gallery_albums_fragment) {

    private val albumsViewModel: AlbumsViewModel by viewModel()
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val viewStateMachine = ViewStateMachine()
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_albums)
    private val root: ViewGroup by viewProvider(R.id.shimmer_albums)
    private val recyclerView: RecyclerView by viewProvider(R.id.albums)
    private val emptyText: AppCompatTextView by viewProvider(R.id.empty_text)
    private val stateLoading = 0
    private val stateData = 1
    private val stateEmpty = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setOnClickListenerBackIcon {
            onToolbarBackIcon()
        }
        setupViewStateMachine()
        loadAlbums()
    }

    protected fun stateEmpty() {
        viewStateMachine.changeState(stateEmpty)
    }

    protected fun stateData() {
        viewStateMachine.changeState(stateData)
    }

    protected fun stateLoading() {
        viewStateMachine.changeState(stateLoading)
    }

    private fun loadAlbums() {
        albumsViewModel.albums(requireContext()).observe(viewLifecycleOwner) {
            data {
                setupAlbums(it)
                if(it.isEmpty()) stateEmpty() else stateData()
            }
            showLoading {
                stateLoading()
            }
            error { t ->
                showError(t.toString(), root)
            }
        }
    }

    private fun setupAlbums(albums: List<Album>) {
        val screen = (requireActivity().window.decorView.width - 32.px(requireContext())) - 4.px(requireContext())
        val size = (screen / 2)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = RecyclerViewAdapterAlbums(albums, size, ::onClickAlbum)
    }

    abstract fun onToolbarBackIcon()

    abstract fun onClickAlbum(album: Album)

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(recyclerView, emptyText)
            }

            state(stateData) {
                visibles(recyclerView)
                gones(shimmer, emptyText)
            }

            state(stateEmpty) {
                visibles(emptyText)
                gones(recyclerView, shimmer)
            }
        }
    }
}
