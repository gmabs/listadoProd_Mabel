package ni.edu.uca.listadoprod_mabel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.listadoprod_mabel.dataadapter.ProductoAdapter
import ni.edu.uca.listadoprod_mabel.dataclass.Producto
import ni.edu.uca.listadoprod_mabel.databinding.ActivityMainBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listaProd =ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    private fun limpiar(){
        with(binding){
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd(){
        with(binding){
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            }catch (ex: Exception){
                Toast.makeText(this@MainActivity, "Error: ${ex.toString()}",
                Toast.LENGTH_LONG).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                {producto -> selectProd(producto)},
                {position -> deleteProd(position)},
                {position -> editProd(position)})
        }
        limpiar()

    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun selectProd(producto: Producto){
        with(binding){
            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio .toString().toEditable()
        }
    }

    private fun deleteProd(position: Int){
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("¿Desea eliminar este artículo?").setCancelable(false)
            .setPositiveButton("Si"){dialog, id ->
                with(binding){
                    listaProd.removeAt(position)
                    rcvLista.adapter?.notifyItemRemoved(position)
                    limpiar()
                }
            }.setNegativeButton("Cancelar"){dialog, id -> dialog.dismiss()}
        val alert = builder.create()
        alert.show()
    }

    private fun editProd(position: Int){
        with(binding){
            val id: Int = etID.text.toString().toInt()
            val nombre: String = etNombreProd.text.toString()
            val precio: Double = etPrecio.text.toString().toDouble()
            val prod = Producto(id, nombre, precio)
            listaProd.set(position, prod)
            rcvLista.adapter?.notifyItemRemoved(position)
        }
    }

    private fun iniciar(){
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }
    }
}