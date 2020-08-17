package com.example.thenewgeography

import android.util.Log
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class EvilTransform {
    private val earthR = 6378137.0
    val pi = 3.14159

    private fun outOfChina(lat: Double, lng: Double): Boolean {
        return !(lng in 72.004..137.8347 && lat in 0.8293..55.8271)
    }


    private fun transform(x:Double, y:Double):ArrayList<Double>{
        var xy = x * y
        var absX = sqrt(abs(x))

        var xPi = x * pi
        var yPi = y * pi
        var d = 20.0*sin(6.0*xPi) + 20.0*sin(2.0*xPi)

        var lat = d
        var lng = d

        lat += 20.0*sin(yPi) + 40.0*sin(yPi/3.0)
        lng += 20.0*sin(xPi) + 40.0*sin(xPi/3.0)

        lat += 160.0*sin(yPi/12.0) + 320*sin(yPi/30.0)
        lng += 150.0*sin(xPi/12.0) + 300.0*sin(xPi/30.0)

        lat *= 2.0 / 3.0
        lng *= 2.0 / 3.0

        lat += -100.0 + 2.0*x + 3.0*y + 0.2*y*y + 0.1*xy + 0.2*absX
        lng += 300.0 + x + 2.0*y + 0.1*x*x + 0.1*xy + 0.1*absX
        var newArray = ArrayList<Double>(3)
        newArray.add(lat)
        newArray.add(lng)
        return newArray
}


    private fun delta(lat:Double, lng:Double): ArrayList<Double> {
        val ee = 0.00669342162296594323
        var subCoorinates = transform(lng-105.0, lat-35.0)
        var dLat = subCoorinates[0]; var dLng = subCoorinates[1]
        var radLat = lat / 180.0 * pi
        var magic = sin(radLat)
        magic = 1 - ee * magic * magic
        var sqrtMagic = sqrt(magic)
        dLat = (dLat * 180.0) / ((earthR * (1 - ee)) / (magic * sqrtMagic) * pi)
        dLng = (dLng * 180.0) / (earthR / sqrtMagic * cos(radLat) * pi)
        var newArray = ArrayList<Double>(3)
        newArray.add(dLat)
        newArray.add(dLng)
        return newArray
    }

    private fun wgs2gcj(wgsLat:Double, wgsLng:Double): ArrayList<Double> {
        var newArray = ArrayList<Double>(3)
        newArray.add(0.0)
        newArray.add(0.0)
        return if (outOfChina(wgsLat, wgsLng)) {
            newArray[0] = wgsLat; newArray[1] = wgsLng
            newArray
        } else {
            newArray = delta(wgsLat, wgsLng)
            var dlat = newArray[0]; var dlng = newArray[1]
            newArray[0] = wgsLat + dlat; newArray[1] = wgsLng + dlng
            newArray
        }
    }


    fun gcj2wgs_exact(gcjLat:Double, gcjLng:Double): ArrayList<Double> {
        var newArray = ArrayList<Double>(3)
        Log.i("Boolean", outOfChina(gcjLat, gcjLng).toString())
        if (outOfChina(gcjLat, gcjLng)) {
            newArray.add(gcjLat); newArray.add(gcjLng)
            return newArray
        }
        newArray.add(0.0)
        newArray.add(0.0)
        val initDelta = 0.01
        val threshold = 0.000001
        var dLat = initDelta
        var dLng = initDelta
        var mLat = gcjLat - dLat
        var mLng = gcjLng - dLng
        var pLat = gcjLat + dLat
        var pLng = gcjLng + dLng
        for(i in 0 until 30) {
            var wgsLat = (mLat + pLat) / 2
            var wgsLng = (mLng + pLng) / 2
            newArray = wgs2gcj(wgsLat, wgsLng)
            var tmplat = newArray[0]
            var tmplng = newArray[1]
            dLat = tmplat - gcjLat
            dLng = tmplng - gcjLng
            if (abs(dLat) < threshold && abs(dLng) < threshold) {
                newArray[0] = wgsLat; newArray[1] = wgsLng
                return newArray
            }

            if (dLat > 0) {
                pLat = wgsLat
            } else {
                mLat = wgsLat
            }

            if (dLng > 0) {
                pLng = wgsLng
            } else {
                mLng = wgsLng
            }
            newArray[0] = wgsLat; newArray[1] = wgsLng
        }
        return newArray
    }
}