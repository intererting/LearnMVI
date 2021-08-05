package com.yly.learnmvi

import io.uniflow.core.flow.data.UIState

/**
 * @author    yiliyang
 * @date      2021/8/5 上午9:33
 * @version   1.0
 * @since     1.0
 *
 * state
 */
sealed class WeatherStates : UIState() {
    object LoadingWeather : WeatherStates()
    data class WeatherState(val day: String, val temperature: String) : WeatherStates()
}