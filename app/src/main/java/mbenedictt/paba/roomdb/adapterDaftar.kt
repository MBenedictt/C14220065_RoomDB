package mbenedictt.paba.roomdb

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mbenedictt.paba.roomdb.database.daftarBelanja

class adapterDaftar (private val daftarBelanja: MutableList<daftarBelanja>) : RecyclerView
.Adapter<adapterDaftar.ListViewHolder> () {

    private lateinit var onItemClickCallback : OnItemClickCallback

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.tvItemBarang)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.tvJumlahBarang)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)

        var _btnEdit = itemView.findViewById<FloatingActionButton>(R.id.fabEdit)
        var _btnDelete = itemView.findViewById<FloatingActionButton>(R.id.fabDelete)
        var _btnFinish = itemView.findViewById<FloatingActionButton>(R.id.fabFinish)
    }

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
        fun finishData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter_daftar, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]
        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }

        holder._btnFinish.setOnClickListener {
            onItemClickCallback.finishData(daftar)
        }
    }

    fun isiData(daftar: List<daftarBelanja>){
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }


}
