package pl.ptprogramming.rxexampleapplication

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import pl.ptprogramming.rxexampleapplication.di.PostsViewModelFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "rxexample-mainactivity"
    private val permissionsRequestCode = 101

    private val postsViewModel by viewModels<PostsViewModel> {
        PostsViewModelFactory(AndroidSchedulers.mainThread())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
        posts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostsViewAdapter(emptyList())
        }

        val visibilityObserver = Observer<Int> {
            Log.i(TAG, "Visibility set to $it .")
            posts.visibility = it
        }
        postsViewModel.listVisibility.observe(this, visibilityObserver)

        val postsObserver = Observer<List<Post>> { newList ->
            Log.i(TAG, "New posts list received.")
            posts.adapter = PostsViewAdapter(newList)
        }
        postsViewModel.listOfPosts.observe(this, postsObserver)
    }

    private fun requestPermissions() {
        val permissions = arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
        ActivityCompat.requestPermissions(this, permissions, permissionsRequestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == permissionsRequestCode) {
            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    Log.e(TAG, "Permissions $permission denied. Application is unable to work properly.")
                    finish()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
