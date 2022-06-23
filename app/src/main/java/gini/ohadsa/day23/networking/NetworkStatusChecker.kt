package gini.ohadsa.day23.networking

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStatusChecker(private val connectivityManager: ConnectivityManager) {

    // inline method are copied to the user class
    inline fun performIfConnectedToInternet(action : () -> Unit ){
        if(hasInternetConnection()){
            action()
        }
    }

    fun hasInternetConnection():Boolean{
        val network = connectivityManager?.activeNetwork?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        //if capabilities is WIFI/CELLULAR/VPN true
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
}