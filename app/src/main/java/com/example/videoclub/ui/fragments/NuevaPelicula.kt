package com.example.videoclub.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.videoclub.databinding.FragmentNuevaPeliculaBinding
import com.example.videoclub.ui.data.Pelicula
import com.example.videoclub.ui.viewmodels.NuevaPeliculaViewModel

class NuevaPelicula : Fragment() {

    private lateinit var viewModel: NuevaPeliculaViewModel
    private lateinit var binding: FragmentNuevaPeliculaBinding
    private var isEdit = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNuevaPeliculaBinding.inflate(inflater, container, false)
        // Obtener los datos del Bundle enviado desde el fragmento de Peliculas
        val titulo = arguments?.getString("titulo")
        val descripcion = arguments?.getString("descripcion")
        val portada = arguments?.getString("portada")
        isEdit = arguments?.getBoolean("isEdit") ?: false

        //Modificamos componentes segun sea Edit o no
        if(isEdit){
            binding.submit.text = "Guardar cambios"
            // Asignar los datos a los campos del formulario si no son nulos
            binding.nameForm.setText(titulo)
            binding.descriptionForm.setText(descripcion)
            binding.portadaForm.setText(portada)

        } else {
            binding.submit.text = "Crear"
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NuevaPeliculaViewModel::class.java)
         binding.submit.setOnClickListener {
            //Campos del formulario
            val nombreForm = binding.nameForm.text
            val descripcionForm = binding.descriptionForm.text
            val portadaForm = binding.portadaForm.text
            //Valores introducidos
            val nombre: String = binding.nameForm.text.toString()
            val descripcion: String = binding.descriptionForm.text.toString()
            val portada: String = binding.portadaForm.text.toString()

             //Diferenciamos si es una pelicula editada o no para saber a que metodo llamar
             if (isEdit){
                 var id = arguments?.getInt("id")
                 val peliculaEditada = Pelicula(id, nombre, descripcion, portada)
                 viewModel.editarPelicula(peliculaEditada)
             } else {
                 val nuevaPelicula = Pelicula(null,nombre,descripcion,portada)
                 viewModel.añadirPelicula(nuevaPelicula)
             }

             //Vaciamos los campos
             nombreForm.clear()
             descripcionForm.clear()
             portadaForm.clear()

        }
    }

}