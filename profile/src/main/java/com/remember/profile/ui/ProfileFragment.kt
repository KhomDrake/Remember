package com.remember.profile.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.widget.Avatar
import com.remember.common.widget.ErrorView
import com.remember.common.widget.RememberToolbar
import com.remember.extension.checkPermissions
import com.remember.extension.navControllerProvider
import com.remember.extension.requestPermission
import com.remember.extension.setStatusBar
import com.remember.extension.setStatusBarIconsLight
import com.remember.profile.R
import com.remember.profile.ui.picture.ChangeProfilePictureBottomSheet
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.profile_fragment_profile) {

    private val profileViewModel: ProfileViewModel by viewModel()
    private val navController: NavController by navControllerProvider()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val profileImage: Avatar by viewProvider(R.id.profile_image)
    private val nickName: AppCompatTextView by viewProvider(R.id.nickname_input)
    private val name: AppCompatTextView by viewProvider(R.id.name_input)
    private val userName: AppCompatTextView by viewProvider(R.id.username_input)
    private val nickNameTitle: AppCompatTextView by viewProvider(R.id.nickname_title)
    private val nameTitle: AppCompatTextView by viewProvider(R.id.name_title)
    private val userNameTitle: AppCompatTextView by viewProvider(R.id.username_title)
    private val changeProfile: CircleImageView by viewProvider(R.id.change_profile)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    private val errorView: ErrorView by viewProvider(R.id.error)
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val viewStateMachine = ViewStateMachine()
    private val stateData = 0
    private val stateLoading = 1
    private val stateError = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarIconsLight()
        setStatusBar(R.color.statusBarColor)
        setupListeners()
        setupObservables()
        setupToolbar()
        setupViewStateMachine()
        errorView.setOnClickListener {
            setupObservables()
        }
    }

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
                gones(errorView, nickName, profileImage, name, userName, changeProfile, nickNameTitle, userNameTitle, nameTitle)
            }

            state(stateData) {
                visibles(nickName, profileImage, name, userName, changeProfile, nickNameTitle, userNameTitle, nameTitle)
                gones(shimmer, errorView)
            }

            state(stateError) {
                visibles(errorView)
                gones(shimmer, nickName, profileImage, name, userName, changeProfile, nickNameTitle, userNameTitle, nameTitle)
            }
        }
    }

    private fun setupToolbar() {
        toolbar.setOnClickListenerBackIcon {
            requireActivity().finish()
        }
    }

    private fun setupObservables() {
        profileViewModel.profile()
            .observe(viewLifecycleOwner) {
                data {
                    userName.text = it.username
                    nickName.text = it.nickname
                    name.text = it.name
                    profileImage.setImage(it.photo)
                    profileViewModel.setProfile(it)
                    viewStateMachine.changeState(stateData)
                }
                showLoading {
                    viewStateMachine.changeState(stateLoading)
                }
                error { _ ->
                    viewStateMachine.changeState(stateError)
                }
            }
    }

    private fun setupListeners() {
        changeProfile.setOnClickListener {
            ChangeProfilePictureBottomSheet.Builder().apply {
                setChooseRemovePicture {
                    removeProfile()
                    it.dismiss()
                }
                setChooseCamera {
                    if(checkPermissions(REQUIRED_PERMISSIONS_CAMERA)) {
                        navController.navigate(
                            ProfileFragmentDirections.actionProfileFragmentToTakeProfilePictureFragment(
                                profileViewModel.profileId()
                            )
                        )
                    } else
                        requestPermission(
                            REQUIRED_PERMISSIONS_CAMERA,
                            REQUEST_CODE_PERMISSION_CAMERA
                        )
                    it.dismiss()
                }
                setChooseGallery {
                    if(checkPermissions(REQUIRED_PERMISSIONS_GALLERY)) {
                        navController.navigate(
                            ProfileFragmentDirections.actionProfileFragmentToChooseAlbumProfileFragment(
                                profileViewModel.profileId()
                            )
                        )
                    } else
                        requestPermission(
                            REQUIRED_PERMISSIONS_GALLERY,
                            REQUEST_CODE_PERMISSION_GALLERY
                        )
                    it.dismiss()
                }
            }.show(parentFragmentManager)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE_PERMISSION_CAMERA -> {
                if(checkPermissions(REQUIRED_PERMISSIONS_CAMERA)) {
                    navController.navigate(
                        ProfileFragmentDirections.actionProfileFragmentToTakeProfilePictureFragment(
                            profileViewModel.profileId()
                        )
                    )
                }
            }
            REQUEST_CODE_PERMISSION_GALLERY -> {
                if(checkPermissions(REQUIRED_PERMISSIONS_GALLERY)) {
                    navController.navigate(
                        ProfileFragmentDirections.actionProfileFragmentToChooseAlbumProfileFragment(
                            profileViewModel.profileId()
                        )
                    )
                }
            }
            else -> Unit
        }
    }

    private fun removeProfile() {
        profileViewModel.removeProfilePicture().observe(viewLifecycleOwner) {
            success {
                profileImage.setImage(null)
                showSuccess(R.string.profile_profile_picture_removed_success, root)
            }
            error { _ ->
                showError(R.string.profile_profile_picture_removed_failure, root)
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PERMISSION_CAMERA = 10
        const val REQUEST_CODE_PERMISSION_GALLERY = 11
        val REQUIRED_PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        val REQUIRED_PERMISSIONS_GALLERY = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
