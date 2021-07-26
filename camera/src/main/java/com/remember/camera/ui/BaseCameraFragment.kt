package com.remember.camera.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.camera.R
import com.remember.common.extension.showError
import com.remember.extension.compress
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class BaseCameraFragment(
    private val beginWithFrontCamera: Boolean = false,
    private val shouldShowGallery: Boolean = false
) : Fragment(R.layout.camera_fragment_camera) {

    private var imageCapture: ImageCapture? = null
    private val previewView: PreviewView by viewProvider(R.id.preview)
    private val takePicture: AppCompatImageView by viewProvider(R.id.take_picture)
    private val changeCamera: AppCompatImageView by viewProvider(R.id.change_camera)
    private val gallery: AppCompatImageView by viewProvider(R.id.gallery)
    private val root: ViewGroup by viewProvider(R.id.root)
    private lateinit var outputDirectory: File
    private var cameraExecutor: ExecutorService? = null
    private var defaultCamera = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupCameraFragment()
    }

    protected fun setupStartCamera() {
        gallery.isVisible = shouldShowGallery
        if(beginWithFrontCamera) {
            defaultCamera = CameraSelector.DEFAULT_FRONT_CAMERA
        }
        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setupClickListener() {
        takePicture.setOnClickListener {
            takePhoto()
        }

        changeCamera.setOnClickListener {
            defaultCamera = if(defaultCamera == CameraSelector.DEFAULT_FRONT_CAMERA) CameraSelector.DEFAULT_BACK_CAMERA
            else CameraSelector.DEFAULT_FRONT_CAMERA

            startCamera()
        }

        gallery.setOnClickListener {
            clickGallery()
        }
    }

    protected open fun clickGallery() = Unit

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
            ).format(System.currentTimeMillis()) + ".jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    showError(R.string.camera_take_picture_failed, root)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    onTakePicture(photoFile.compress(requireContext()))
                }
            })
    }

    abstract fun onTakePicture(compressedFile: File)

    open fun setupCameraFragment() {
        startCamera()
    }

    protected fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            imageCapture = ImageCapture.Builder()
                .apply {
                    view?.let {
                        setTargetRotation(it.display.rotation)
                    }
                }
                .build()

            // Preview
            val preview = Preview.Builder()
                .build()

            preview.setSurfaceProvider(previewView.createSurfaceProvider())

            val cameraSelector = defaultCamera

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                showError(R.string.camera_open_camera_failed, root)
                requireActivity().finish()
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.common_remember_title)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

    override fun onDestroy() {
        cameraExecutor?.shutdown()
        super.onDestroy()
    }
}
