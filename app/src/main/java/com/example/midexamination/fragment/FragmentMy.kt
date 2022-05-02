package com.example.midexamination.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.midexamination.HonorActivity
import com.example.midexamination.R
import com.example.midexamination.TimeTravelActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileNotFoundException

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/5/1 14:20
 */
class FragmentMy(context:Context):Fragment() {
    private var mImagePath: String? = null
    private lateinit var view0:View
    private var context0 = context
    private val TAG = "123"
    private lateinit var mImageView: CircleImageView
    private lateinit var mName:TextView
    private lateinit var mBottomSheetDialog: BottomSheetDialog
    private lateinit var mTimeTravel:TextView
    private lateinit var mHonor:TextView
    private lateinit var mEdit1:EditText
    private lateinit var mEdit2:EditText
    private lateinit var mSaying:TextView
    private lateinit var mEImage:CircleImageView
    private lateinit var mBtnOk:MaterialButton
    private lateinit var mSetting:TextView
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor
    private var imageUrl: Uri? =null
    private lateinit var mBitMap:Bitmap
    private val mOpenAlum = 1
    private var flag = false
    private lateinit var file:File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view0 = inflater.inflate(R.layout.activity_main_fragment_3_my,container,false)
        return view0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initClickEvent()
    }

    private fun init(){
        mImageView = view0.findViewById(R.id.my_image)

        mName = view0.findViewById(R.id.tv_my_name)
        mTimeTravel = view0.findViewById(R.id.tv_time_travel)
        mHonor = view0.findViewById(R.id.tv_time_honor)
        mSetting = view0.findViewById(R.id.tv_time_setting)
        mSaying = view0.findViewById(R.id.tv_my_saying)

        mSharedPreferences = context0.getSharedPreferences("data2",Context.MODE_PRIVATE)
        mEditor = context0.getSharedPreferences("data2",Context.MODE_PRIVATE).edit()

        mBottomSheetDialog = BottomSheetDialog(context0)
        mBottomSheetDialog.setContentView(R.layout.bottom_fragment3_create)

        mEdit1 = mBottomSheetDialog.findViewById(R.id.edit_name)!!
        mEdit2 = mBottomSheetDialog.findViewById(R.id.edit_saying)!!
        mEImage = mBottomSheetDialog.findViewById(R.id.edit_image)!!
        mBtnOk = mBottomSheetDialog.findViewById(R.id.edit_btn_ok)!!

        mName.text = mSharedPreferences.getString("name","网名")
        mSaying.text = mSharedPreferences.getString("saying","个性签名")

        file = File(context0.externalCacheDir,"the头像.jpg")
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= 24) {
                imageUrl =
                    FileProvider.getUriForFile(context0, "com.wayeal.wateraffair.user.fileprovider", file)
            } else {
                imageUrl = Uri.fromFile(file)
            }
            var bitmap: Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeStream(context0.contentResolver.openInputStream(imageUrl!!))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            Log.d(TAG, "initimage: ")
            mImageView.setImageBitmap(bitmap)
            displayImage(mSharedPreferences.getString("imagePath","")!!)
        }
    }
    private fun initClickEvent(){
        mImageView.setOnClickListener {
            Toast.makeText(context0,"想点这里换头像?不行=-=",Toast.LENGTH_SHORT).show()
        }

        mSetting.setOnClickListener {
            mBottomSheetDialog.show()
        }

        mEImage.setOnClickListener {
            openAlum()
        }

        mBtnOk.setOnClickListener {
            if(!TextUtils.isEmpty(mEdit1.text) && !TextUtils.isEmpty(mEdit2.text)){
                mName.text = mEdit1.text.toString()
                mSaying.text = mEdit2.text.toString()
                mEditor.apply {
                    putString("name",mEdit1.text.toString())
                    putString("saying",mEdit2.text.toString())
                    if(mImagePath!=null) {
                        putString("imagePath", mImagePath)
                    }
                    apply()
                }
            }
            flag = true
            mImageView.setImageBitmap(mBitMap)
            Toast.makeText(context0,"设置成功！",Toast.LENGTH_SHORT).show()
            mBottomSheetDialog.dismiss()
        }

        mTimeTravel.setOnClickListener {
            startActivity(Intent(context0,TimeTravelActivity::class.java))
        }

        mHonor.setOnClickListener {
            startActivity(Intent(context0,HonorActivity::class.java))
        }
    }
    private fun openAlum() {
        if (ContextCompat.checkSelfPermission(
                context0,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl)
            startActivityForResult(intent, mOpenAlum)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            mOpenAlum -> {
                if(resultCode == RESULT_OK) {
                    if(Build.VERSION.SDK_INT >= 19){
                        if (data != null) {
                            handleImageOnKitKat(data)
                        }
                    }else{
                        if (data != null) {
                            handleImageBeforeKitKat(data)
                        }
                    }
                }
            }
        }
    }

    fun setImage(){
        try {
            val bitmap = BitmapFactory.decodeStream(context0.getContentResolver().openInputStream(imageUrl!!))
            mEImage.setImageBitmap(bitmap)
            mBitMap = bitmap
            if(!mBottomSheetDialog.isShowing) {
                mImageView.setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            mOpenAlum -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlum()
            } else {
                Toast.makeText(context0, "已拒绝访问相册权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //老版本<4.4
    private fun handleImageBeforeKitKat(data: Intent) {
        val uri = data.data
        val imagePath = getImagePath(uri!!, null)
        mImagePath = imagePath!!
        displayImage(imagePath!!)
    }

    //新版本>4.4
    private fun handleImageOnKitKat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        if (DocumentsContract.isDocumentUri(context0, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":").toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.download.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    docId.toLong()
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            imagePath = uri.path
        }
        mImagePath = imagePath!!
        displayImage(imagePath!!)
    }

    private fun displayImage(imagePath: String) {
        if(imagePath!="") {
            try {
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            imageUrl = if (Build.VERSION.SDK_INT >= 24) {
                FileProvider.getUriForFile(
                    context0,
                    "com.wayeal.wateraffair.user.fileprovider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }
            val uri =
                FileProvider.getUriForFile(
                    context0,
                    "com.wayeal.wateraffair.user.fileprovider",
                    File(imagePath)
                )
            imageUrl = uri
            setImage()
        }
    }

    //获得图片路径
    @SuppressLint("Range")
    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor: Cursor? = context0.getContentResolver().query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }
}