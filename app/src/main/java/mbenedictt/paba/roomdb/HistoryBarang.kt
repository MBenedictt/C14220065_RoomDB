package mbenedictt.paba.roomdb

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import mbenedictt.paba.roomdb.database.daftarBelanja
import mbenedictt.paba.roomdb.database.historyBarangDB

class HistoryBarang : AppCompatActivity() {
    private lateinit var DB: historyBarangDB
    private lateinit var adapterHistory: adapterHistory
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_barang)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = historyBarangDB.getDatabase(this)

        adapterHistory = adapterHistory(arDaftar)
        var _rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val historyBarang = DB.funhistoryBarangDAO().selectAll()
            Log.d("data ROOM", historyBarang.toString())
            adapterHistory.isiData(historyBarang)
        }
    }
}