package com.example.videoclub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.videoclub.R
import com.example.videoclub.ui.data.Pelicula
import com.example.videoclub.ui.viewmodels.PeliculasViewModel
import com.squareup.picasso.Picasso


class PeliculaAdapter(private var peliculasList: List<Pelicula>,
                      private val onEliminarClick: (Int) -> Unit,
                      private val onEditClick: (Pelicula) -> Unit
) : RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vista = inflater.inflate(R.layout.item_pelicula,parent,false)
        return PeliculaViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return peliculasList.size
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        holder.bind(peliculasList[position])
        val pelicula = peliculasList[position]
        holder.deleteBtn.setOnClickListener {
            pelicula.id?.let { onEliminarClick(pelicula.id) }
        }

        holder.editBtn.setOnClickListener {
            onEditClick(pelicula)
        }

    }

    fun actualizarListaPeliculas(nuevaListaPeliculas: List<Pelicula>) {
        peliculasList = nuevaListaPeliculas
        notifyDataSetChanged()
    }


    inner class PeliculaViewHolder(private val vista: View) :
        RecyclerView.ViewHolder(vista) {
        val titulo = vista.findViewById(R.id.titulo) as TextView
        val descripcion = vista.findViewById(R.id.descripcion) as TextView
        val portada = vista.findViewById(R.id.portada) as ImageView
        val deleteBtn = vista.findViewById(R.id.delete) as AppCompatButton
        val editBtn = vista.findViewById(R.id.update) as AppCompatButton

        fun bind(pelicula: Pelicula) {
           titulo.text = pelicula.title
            descripcion.text = pelicula.description
            Picasso.get().load(pelicula.portada).into(portada)

        }
    }
}

