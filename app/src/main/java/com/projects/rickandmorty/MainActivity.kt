package com.projects.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.rickandmorty.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val call = RetrofitHelper.getInstance().create(RickAndMortyService::class.java)

        call.getCharacters(1).enqueue(object: Callback<CharactersResponse>{
            override fun onResponse(
                p0: Call<CharactersResponse>,
                p1: Response<CharactersResponse>
            ) {
                binding.rvList.layoutManager = LinearLayoutManager(this@MainActivity)
                if(p1.isSuccessful){
                        val adapter = p1.body()?.let { CharacterAdapter(it.results) }
                        binding.rvList.adapter = adapter
                }

            }

            override fun onFailure(p0: Call<CharactersResponse>, p1: Throwable) {
                Log.i("msg","ERROR")
            }

        })


//        //coroutine to launch it on background thread
//        GlobalScope.launch {
//            val result = call.getCharacters(1)
//            if(result!=null){
//                Log.i("msg",result.body().toString())
////                val adapter = ResultAdapter(result.body()!!)
////                binding.rvList.layoutManager = LinearLayoutManager(this@MainActivity)
////                binding.rvList.adapter = adapter
//            }
//        }


    }
}