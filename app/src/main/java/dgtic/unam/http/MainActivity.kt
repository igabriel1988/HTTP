package dgtic.unam.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dgtic.unam.http.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()


    override fun onCreate( savedInstanceState: Bundle? )
            {
                super.onCreate( savedInstanceState )
                binding = ActivityMainBinding.inflate( layoutInflater )
                setContentView( binding.root )
                binding.searchview.setOnQueryTextListener( this )
                initReclyView()
            }
    private fun initReclyView()
            {
                adapter = DogAdapter( dogImages )
                binding.recycleView.layoutManager = LinearLayoutManager( this )
                binding.recycleView.adapter = adapter
            }

        private fun getRetrofit() : Retrofit
            {
                return Retrofit.Builder()
                    .baseUrl( "https://dog.ceo/api/breed/" )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .build()
            }

        private fun  search( typ : String )
            {
                    CoroutineScope( Dispatchers.IO ).launch {
                        val call = getRetrofit().create( ServiceAPI::class.java ).getBreedbyDogs( "type/images" )
                        val pug : ModeloDog? = call.body()
                        runOnUiThread{
                            if( call.isSuccessful ){
                                val images : List<String> = pug?.images?: emptyList()
                                dogImages.clear()
                                dogImages.addAll( images )
                                adapter.notifyDataSetChanged()
                            }
                            else
                            {
                                msgError()
                            }
                        }
                    }
            }

    private fun msgError()
    {
       Toast.makeText( this, "Error en Conexion", Toast.LENGTH_SHORT ).show()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if( !p0.isNullOrEmpty() )
        {
            search( p0.lowercase() )
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }



}