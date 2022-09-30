package ni.edu.uca.listadoprod_mabel.dataadapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.listadoprod_mabel.dataclass.Producto
import ni.edu.uca.listadoprod_mabel.databinding.ItemlistaBinding

class ProductoAdapter(val listProd: List<Producto>,
                      private val onClickerView: (Producto) -> Unit,
                      private val onClickerDel: (Int) -> Unit,
                      private val onClickerEdit: (Int) -> Unit,
                        ):
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
        inner class ProductoHolder(val binding: ItemlistaBinding) :
                RecyclerView.ViewHolder(binding.root){

            fun cargar(producto: Producto, onClickListener: (Producto) -> Unit,
                onClickerDel:(Int) -> Unit, onClickerEdit: (Int) -> Unit){
                        with(binding){
                            tvCodProd.text = producto.id.toString()
                            tvNombreProd.text = producto.nombre
                            tvPrecioProd.text = producto.precio.toString()
                            itemView.setOnClickListener{onClickListener(producto)}
                            binding.btnDelete.setOnClickListener{onClickerDel(adapterPosition)}
                            binding.btnEdit.setOnClickListener{onClickerEdit(adapterPosition)}
                        }
                    }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position], onClickerView, onClickerDel, onClickerEdit)
    }

    override fun getItemCount(): Int = listProd.size

}