package com.staskokos.weatherdev.presenter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.staskokos.weatherdev.R
import com.staskokos.weatherdev.databinding.ActivityMainBinding
import com.onesignal.OneSignal
import com.staskokos.weatherdev.data.models.Hour
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WeatherAdapter
    private val vm: MainViewModel by viewModels()
    private var day = "today"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableCrashApp()
        initOneSignalPush()
        initRecyclerView()

        vm.getWeather(q = "Кривой Рог")

        vm.weatherLiveData.observe(this) {
            binding.textInput.hint = it.location
            binding.textInput.text?.clear()
            clearFocusAndHideKeyboard()
            when(day) {
                "today" -> adapter.submitList(getListStartsWithCurrentHour(it.today.hours))
                "tomorrow" -> adapter.submitList(it.tomorrow.hours)
                "afterday" -> adapter.submitList(it.afterday.hours)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            vm.getWeather(q = binding.textInput.hint.toString())
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.textInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty() && s[s.length - 1] == '\n' && s[0] != ' ' && s[0] != '\n') {
                    val searchText = binding.textInput.text.toString()
                    binding.textInput.text?.clear()
                    vm.getWeather(q = searchText)
                } else if(s.isNotEmpty() && (s[0] == ' ' || s[0] == '\n')) {
                    binding.textInput.text?.clear()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.buttonSearch.setOnClickListener {
            if(binding.textInput.text.toString().isNotEmpty()) {
                val searchText = binding.textInput.text.toString()
                binding.textInput.text?.clear()
                vm.getWeather(q = searchText)
            }
        }

        binding.todayButton.setOnClickListener {
            day = "today"
            adapter.submitList(getListStartsWithCurrentHour(
                vm.weatherLiveData.value?.today?.hours as List<Hour>)
            )
        }

        binding.tomorrowButton.setOnClickListener {
            day = "tomorrow"
            adapter.submitList(vm.weatherLiveData.value?.tomorrow?.hours)
        }

        binding.afterdayButton.setOnClickListener {
            day = "afterday"
            adapter.submitList(vm.weatherLiveData.value?.afterday?.hours)
        }
    }

    private fun disableCrashApp() {
        val exceptionHandler =
            Thread.UncaughtExceptionHandler { _: Thread, appError: Throwable ->
                openBugActivity(appError)
            }
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
    }

    private fun openBugActivity(appError: Throwable) {
        val intent = Intent(this, ErrorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("message", appError.message.toString())
        intent.putExtra("cause", appError.cause.toString())
        intent.putExtra("stackTrace", appError.stackTraceToString())
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    }

    private fun getListStartsWithCurrentHour(fullHoursList: List<Hour>): List<Hour> {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val hours = fullHoursList.subList(currentHour, fullHoursList.size)
        return hours
    }

    private fun initRecyclerView() {
        recyclerView = binding.recyclerView
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
    }

    private fun clearFocusAndHideKeyboard() {
        binding.textInput.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.textInput.windowToken, 0)
    }

    private fun initOneSignalPush() {
        val onesignalAppId = "0839b2b7-2880-4ba2-a585-b78d424f6553"
        OneSignal.initWithContext(this, onesignalAppId)
    }
}
