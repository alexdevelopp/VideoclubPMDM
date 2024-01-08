package com.example.videoclub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoclub.R
import com.example.videoclub.databinding.FragmentHomeBinding
import com.example.videoclub.ui.adapters.PeliculaAdapter
import com.example.videoclub.ui.data.Pelicula
import com.example.videoclub.ui.viewmodels.NuevaPeliculaViewModel
import com.example.videoclub.ui.viewmodels.PeliculasViewModel

class HomeFragment : Fragment() {

    private lateinit var peliculaAdapter: PeliculaAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var peliculasViewModel: PeliculasViewModel
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.vistaReciclada.layoutManager = LinearLayoutManager(requireContext())
        peliculasViewModel = ViewModelProvider(this).get(PeliculasViewModel::class.java)

        //Inicializamos adapter
        peliculaAdapter = PeliculaAdapter(ArrayList(),
            { id -> peliculasViewModel.borrarPelicula(id) },
            { pelicula -> goToEdit(pelicula) }
        )

        //Asignamos el adapter
        binding.vistaReciclada.adapter = peliculaAdapter

        //Observa cuando hay un cambio en la lista de peliculas y actualiza el adapter
        peliculasViewModel.peliculasLiveData.observe(viewLifecycleOwner, Observer { peliculas ->
            if (peliculas != null) {
                peliculaAdapter.actualizarListaPeliculas(peliculas)
            }
        })

        //Carga las peliculas de la API
        peliculasViewModel.obtenerPeliculas()

        //Navegar hasta el formulario de crear pelicula
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_nav_nueva_pelicula)
        }

        return view
    }

    //Navega hacia la vista del formulario con los datos de la pelicula a editar
    private fun goToEdit(pelicula: Pelicula) {
        isEdit = true
        val bundle = Bundle()
        pelicula.id?.let { bundle.putInt("id", it) }
        bundle.putString("titulo", pelicula.title)
        bundle.putString("descripcion", pelicula.description)
        bundle.putString("portada", pelicula.portada)
        bundle.putBoolean("isEdit",isEdit)
        val editarPeliculaFragment = NuevaPelicula()
        editarPeliculaFragment.arguments = bundle
        findNavController().navigate(R.id.action_nav_home_to_nav_nueva_pelicula, bundle)
    }
}
