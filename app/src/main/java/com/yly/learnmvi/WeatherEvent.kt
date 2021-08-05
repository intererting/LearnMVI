package com.yly.learnmvi

import io.uniflow.core.flow.data.UIEvent

/**
 * @author    yiliyang
 * @date      2021/8/5 上午10:36
 * @version   1.0
 * @since     1.0
 */
// Events definition
sealed class WeatherEvent : UIEvent() {
    data class Success(val location: String) : WeatherEvent()
    data class Failed(val location: String, val error: Throwable? = null) : WeatherEvent()
}