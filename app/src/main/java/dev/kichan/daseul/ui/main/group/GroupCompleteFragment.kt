package dev.kichan.daseul.ui.main.group

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import dev.kichan.daseul.R
import dev.kichan.daseul.ui.main.MainActivity
import dev.kichan.daseul.ui.main.nav.Main3Fragment

class GroupCompleteFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_group_complete, container, false)
        val invite_id = arguments?.getString("key_invite")
        val barcodeValue = "dasuel://invite?token="+invite_id
        // QR 코드 생성
        val writer = QRCodeWriter()
        val bitMatrix: BitMatrix = writer.encode(barcodeValue, BarcodeFormat.QR_CODE, 400, 400)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }

        // 생성된 QR 코드를 ImageView에 표시
        view.findViewById<ImageView>(R.id.img_qr).setImageBitmap(bitmap)


        Log.d("fortest","컴플리트 받은 값 = "+invite_id)

        view.findViewById<TextView>(R.id.txt_invite).text = invite_id
        view.findViewById<Button>(R.id.next_btn).setOnClickListener{
            var intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return view

    }
}