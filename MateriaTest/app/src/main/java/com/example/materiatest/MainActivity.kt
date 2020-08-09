package com.example.materiatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val fruits = mutableListOf(Fruit("Apple",R.drawable.apple),Fruit("Banana",R.drawable.banana),
    Fruit("Orange",R.drawable.orange),Fruit("Watermelon",R.drawable.watermelon),Fruit("Pear",R.drawable.pear),
    Fruit("Grape",R.drawable.grape),Fruit("Pineapple",R.drawable.pineapple),Fruit("Strawberry",R.drawable.strawberry),
    Fruit("Cherry",R.drawable.cherry), Fruit("Mango",R.drawable.mango)
    )

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        recyclerView.adapter = adapter

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        fab.setOnClickListener{
//            Toast.makeText(this,"FAB clicked",Toast.LENGTH_SHORT).show()
            Snackbar.make(it,"Data deleted",Snackbar.LENGTH_SHORT)
                .setAction("Undo"){
                    Toast.makeText(this,"Data restored",Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        navView.setCheckedItem(R.id.navCall)
        navView.setNavigationItemSelectedListener {
            //处理逻辑，当用户点击之后
            when(it.itemId){
                R.id.navFriends->Toast.makeText(this,"You clicked Friend",Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawers()
            true
        }

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }

    private fun refreshFruits(adapter: FruitAdapter){
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun initFruits(){
        fruitList.clear()
        repeat(50){
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.backup->Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show()
            R.id.delete->Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show()
            R.id.setting->Toast.makeText(this,"You clicked Settings",Toast.LENGTH_SHORT).show()
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}