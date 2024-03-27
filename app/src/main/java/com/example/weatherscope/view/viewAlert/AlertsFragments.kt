package com.example.weatherscope.view.viewAlert

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherscope.R
import com.example.weatherscope.databinding.FragmentAlertsFragmentsBinding
import java.util.Calendar

class AlertsFragments : Fragment() {
    // TODO will do it with worker i think or can be with alarm manager
    private lateinit var binding: FragmentAlertsFragmentsBinding
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlertsFragmentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Code will be here <><><><><><><><>

        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        binding.imgAddAlert.setOnClickListener {
            showDateTimePicker()
        }
    }
    private fun showDateTimePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute)
                setupAlarm(calendar)
            }, hour, minute, true)

            timePickerDialog.show()
        }, year, month, day)

        datePickerDialog.show()
    }


    private fun setupAlarm(calendar: Calendar) {
        val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(requireContext(), "Congrats your Alert saved successfully", Toast.LENGTH_SHORT).show()
    }
}