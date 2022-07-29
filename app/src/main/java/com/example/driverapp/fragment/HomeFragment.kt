package com.example.driverapp.fragment

import android.Manifest
import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.driverapp.R
import com.example.driverapp.viewModel.HomeViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class HomeFragment : Fragment() , OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var rootView:View
    private lateinit var homeViewModel: HomeViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.

    private lateinit var mapFragment: SupportMapFragment
    //location:

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        initComponent()
        return rootView
    }

    private fun initComponent(){
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationRequest = LocationRequest()
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        locationRequest.setFastestInterval(3000)
        locationRequest.interval = 5000
        locationRequest.setSmallestDisplacement(10f)

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val newPos = LatLng(locationResult.lastLocation!!.latitude, locationResult.lastLocation!!.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPos, 18f))


                mMap.addCircle(CircleOptions()
                    .center(newPos)
                )
            }
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,Looper.myLooper())

    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    mMap.isMyLocationEnabled = true
                    mMap.uiSettings.isMyLocationButtonEnabled = true
                    mMap.setOnMyLocationButtonClickListener {
                        Toast.makeText(requireContext(), "Btn click", Toast.LENGTH_SHORT).show()
                        fusedLocationProviderClient.lastLocation
                            .addOnFailureListener { e ->
                                Toast.makeText(requireContext(), "Permission " + p0!!.permissionName + "was denied", Toast.LENGTH_SHORT).show()
                            }
                            .addOnSuccessListener { location ->
                                val userLatLng = LatLng(location.latitude, location.longitude)
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 18f))
                            }
                        true
                    }

                    val locationBtn = (mapFragment.requireView()
                        .findViewById<View>("1".toInt())
                        .parent as View).findViewById<View>("2".toInt())
                    val params = locationBtn.layoutParams as RelativeLayout.LayoutParams
                    params.addRule(RelativeLayout.ALIGN_TOP, 0)
                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE)
                    params.bottomMargin = 50
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(requireContext(), "Permission " + p0!!.permissionName + "was denied", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

            })
            .check()

        try{
            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.uber_maps_style))
            if(!success){
                Log.e("MapError", "Style parse error")
            }
        } catch (e: Resources.NotFoundException){
            Log.e("MapError", e.message!!)
        }

    }

    override fun onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }
}