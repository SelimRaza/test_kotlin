package com.example.mbm.outletEntry.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mbm.databinding.FragmentOutletEntryBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

class OutletEntryFragment : Fragment() {
    private lateinit var binding: FragmentOutletEntryBinding
    private val key1 by lazy { arguments?.getInt(EXTRA_KEY1, 0) }
    private val key2 by lazy { arguments?.getInt(EXTRA_KEY2, 0) }

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

        if (isLocationPermissionGranted()) {
            getCurrentLocation()
        }
        else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }


    }

    companion object {
        const val EXTRA_KEY1 = "key1"
        const val EXTRA_KEY2 = "key2"
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

}