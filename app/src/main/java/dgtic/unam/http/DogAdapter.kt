package dgtic.unam.http


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

public class DogAdapter( val images:List<String> ) : RecyclerView.Adapter<DogViewHolder>(){
    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder{
            val layoutInflater = LayoutInflater.from( parent.context )
            return DogViewHolder( layoutInflater.inflate( R.id.imgView, parent, false ) )
        }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item : String = images[ position ]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return images.size
    }
}