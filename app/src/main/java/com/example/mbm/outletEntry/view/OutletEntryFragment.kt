package com.example.mbm.outletEntry.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mbm.common.UploadDataWorker
import com.example.mbm.databinding.FragmentOutletEntryBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale


class OutletEntryFragment : Fragment() {
    private lateinit var binding: FragmentOutletEntryBinding
    private val key1 by lazy { arguments?.getInt(EXTRA_KEY1, 0) }
    private val key2 by lazy { arguments?.getInt(EXTRA_KEY2, 0) }

    companion object {
        const val EXTRA_KEY1 = "key1"
        const val EXTRA_KEY2 = "key2"
        //camera
        private const val TAG = "CameraXExample"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }





    private val fusedLocationClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(requireActivity()) }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Location permission granted, proceed with your logic here
                getCurrentLocation()
            } else {
                // Location permission denied or user clicked on "Never ask again"
                // Handle the scenario when the permission is denied
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOutletEntryBinding.inflate(layoutInflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //to go to previous page
        //findNavController().popBackStack()

        initListeners()

        //for library no need to take camera permissiom
//        checkCameraPermission()

        if (isLocationPermissionGranted()) {
            getCurrentLocation()
        }
        else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }

    private fun checkCameraPermission() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun initListeners() {
        binding.iv.setOnClickListener {
            takePhoto()
        }

        binding.btnSubmit.setOnClickListener {
            //saveImageToDb()
            UploadDataWorker.start(requireContext())
        }
    }


    private fun isLocationPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }


    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    binding.tvLatLong.text = "Latitude: $latitude , Longitute: $longitude"

                    // Log latitude and longitude
                    println("Latitude: $latitude, Longitude: $longitude")

                    // Get geocoded address
                    val geocoder = Geocoder(requireContext(), Locale.ENGLISH)
                    try {
                        val addresses: MutableList<Address> = geocoder.getFromLocation(
                            latitude,
                            longitude,
                            1
                        ) as MutableList<Address>

                        if (addresses.isNotEmpty()) {
                            val address: Address = addresses[0]
                            val addressLine = address.getAddressLine(0)
                            val city = address.locality
                            val state = address.adminArea
                            val country = address.countryName
                            val postalCode = address.postalCode

                            // Log geocoded address components
                            println("Address Line: $addressLine")
                            println("City: $city")
                            println("State: $state")
                            println("Country: $country")
                            println("Postal Code: $postalCode")
                            binding.tvLocation.text = "Address Line: $addressLine City: $city State: $state Country: $country Postal Code: $postalCode"


                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            .addOnFailureListener { exception: Exception ->
                // Handle location retrieval failure
                exception.printStackTrace()
            }
    }


    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }



    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            if (result?.data != null) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                binding.iv.setImageBitmap(bitmap)

//                val imageUri = result.data?.data
//                binding.iv.setImageURI(imageUri)
            }
        }
    }


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                val mProfileUri = fileUri
                binding.iv.setImageURI(fileUri)
                binding.iv.tag = mProfileUri

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    private fun takePhoto() {
        ImagePicker.with(this)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }



        //customization
        /*Pick image using Gallery

                ImagePicker.with(this)
                    .galleryOnly()	//User can only select image from Gallery
                    .start()	//Default Request Code is ImagePicker.REQUEST_CODE
        Capture image using Camera

                ImagePicker.with(this)
                    .cameraOnly()	//User can only capture image using Camera
                    .start()
        Crop image

                ImagePicker.with(this)
                    .crop()	    //Crop image and let user choose aspect ratio.
                    .start()
        Crop image with fixed Aspect Ratio

                ImagePicker.with(this)
                    .crop(16f, 9f)	//Crop image with 16:9 aspect ratio
                    .start()
        Crop square image(e.g for profile)

        ImagePicker.with(this)
            .cropSquare()	//Crop square image, its same as crop(1f, 1f)
            .start()
        Compress image size(e.g image should be maximum 1 MB)

        ImagePicker.with(this)
            .compress(1024)	//Final image size will be less than 1 MB
            .start()
        Set Resize image resolution

                ImagePicker.with(this)
                    .maxResultSize(620, 620)	//Final image resolution will be less than 620 x 620
                    .start()
        Intercept ImageProvider, Can be used for analytics

        ImagePicker.with(this)
            .setImageProviderInterceptor { imageProvider -> //Intercept ImageProvider
                Log.d("ImagePicker", "Selected ImageProvider: "+imageProvider.name)
            }
            .start()
        Intercept Dialog dismiss event

                ImagePicker.with(this)
                    .setDismissListener {
                        // Handle dismiss event
                        Log.d("ImagePicker", "onDismiss");
                    }
                    .start()*/




        //raw codes
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraLauncher.launch(cameraIntent)

    }


}