package com.yly.learnmvi

import androidx.lifecycle.viewModelScope
import io.uniflow.android.AndroidDataFlow
import io.uniflow.android.livedata.liveDataPublisher
import io.uniflow.core.coroutines.onFlow
import io.uniflow.core.flow.actionOn
import io.uniflow.core.flow.data.UIState
import io.uniflow.core.flow.onState
import io.uniflow.core.threading.onMain
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * @author    yiliyang
 * @date      2021/8/5 上午9:34
 * @version   1.0
 * @since     1.0
 *
 * AndroidDataFlow只能保存一中UIState
 */
class WeatherDataFlow() : AndroidDataFlow() {

    //自定义Stream，默认是LiveData
    val myStream = liveDataPublisher(UIState.Empty, "other")

    override suspend fun onError(error: Exception, currentState: UIState) {
//        super.onError(error, currentState)
        println(error.message)
    }

    // getWeather action
    fun getWeather() = action {

        onState<WeatherStates.WeatherState> {
            println(it.toString())
        }

        setState(WeatherStates.WeatherState("today", "22"))

//        onMain {
//            println("main thread")
//        }

//        sendEvent(WeatherEvent.Success("zh"))
    }

    fun getMovie() {
        action {
            onState<MovieStates.MovieState> {
                println(it.toString())
            }

            setState(MovieStates.MovieState("transformer"))
        }
    }

    fun refreshMovie() = actionOn<MovieStates.MovieState> {
        println(it)
    }

    fun combineStateAndEvent() = action {
        notifyStateUpdate(WeatherStates.WeatherState("tomorrow", "22"), WeatherEvent.Success("en"))
    }

    fun actionWithException() = action(
        onAction = {

        },
        onError = { err, state ->

        }
    )

    fun actionWithFlow() {
        // Launch a job
        viewModelScope.launch {
            // get our Flow
            val flow = flow {
                emit(1)
                emit(2)
                emit(3)
            }

            // Collect from Flow and emit actions
            onFlow(
                flow = { flow },
                doAction = { value ->
                    setState { WeatherStates.WeatherState("today", value.toString()) }
                },
                onError = { error, state ->
                }
            )
        }
    }

    fun testMyStream() {
        action {
            myStream.setState(WeatherStates.WeatherState("other", "10"))
        }
    }
}