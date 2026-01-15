package io.github.emmiii.randommealplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.emmiii.randommealplanner.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.emmiii.randommealplanner.model.DayMeal
import io.github.emmiii.randommealplanner.ui.DayMealAdapter
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DayMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val days = mutableListOf(
            DayMeal("Monday"),
            DayMeal("Tuesday"),
            DayMeal("Wednesday"),
            DayMeal("Thursday"),
            DayMeal("Friday"),
            DayMeal("Saturday"),
            DayMeal("Sunday")
        )

        // Skapa adapter
        adapter = DayMealAdapter(days) { position ->
            val day = days[position]
            days[position] = day.copy(isPinned = !day.isPinned)
            adapter.notifyItemChanged(position)
        }


        binding.weekRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.weekRecyclerView.adapter = adapter

        val meals = loadMealsFromJson()

        binding.randomizeButton.setOnClickListener {
            val randomizedDays = days.map { dayMeal ->
                if (dayMeal.isPinned) {
                    dayMeal // behåll
                } else {
                    dayMeal.copy(meal = meals.random())
                }
            }
            adapter.updateItems(randomizedDays)
        }
    }

    private fun loadMealsFromJson(): List<String> {
        val jsonString = assets.open("meals.json").bufferedReader(Charset.forName("UTF-8")).use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("meals")

        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}
