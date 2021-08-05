package com.yly.learnmvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.yly.learnmvi.databinding.ActivityMainBinding
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import io.uniflow.core.flow.onState

class MainActivity : AppCompatActivity() {

    private val weatherFlow by viewModels<WeatherDataFlow>()
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // Observe incoming states
        onStates(weatherFlow) { state ->
            when (state) {
                // react on WeatherState update
                is WeatherStates.WeatherState -> println(state.toString())
            }
        }

        // Let's observe incoming events
        onEvents(weatherFlow) { event ->
            when (event) {
                is WeatherEvent.Success -> println(event)
                is WeatherEvent.Failed -> println(event)
            }
        }

        weatherFlow.myStream.onStates(this) {
            println(it)
        }

        mBinding.weather.setOnClickListener {
//            weatherFlow.getWeather()
//            weatherFlow.actionWithFlow()
            weatherFlow.testMyStream()
        }
        mBinding.movie.setOnClickListener {
            weatherFlow.getMovie()
        }
        mBinding.refresh.setOnClickListener {
            weatherFlow.refreshMovie()
        }
    }
}