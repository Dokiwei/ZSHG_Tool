package com.dokiwei.zshg.tool

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.dokiwei.zshg.tool.ui.screen.LaunchHomeScreen
import com.dokiwei.zshg.tool.ui.theme.ZSHGTheme
const val REQUEST_WRITE_EXTERNAL_STORAGE = 1
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //主内容
        setContent {
            ZSHGTheme {
                //启动屏幕
                LaunchHomeScreen()
            }
        }
        //应用权限判断
        checkPermission()
    }


    //权限申请
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"已申请权限",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"申请权限失败",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //应用权限判断
    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE)
        }else{
            Toast.makeText(this,"已申请权限",Toast.LENGTH_SHORT).show()
        }
    }
}




@Preview
@Composable
fun PreviewMyHome(){
    ZSHGTheme {
        LaunchHomeScreen()
    }
}



