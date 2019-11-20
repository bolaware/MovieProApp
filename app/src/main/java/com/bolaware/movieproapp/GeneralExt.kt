package com.bolaware.movieproapp

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.microsoft.azure.cognitiveservices.search.imagesearch.BingImageSearchManager
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.ImageType
import com.microsoft.azure.cognitiveservices.search.imagesearch.models.SearchOptionalParameter
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}


fun Activity.hideKeyboard() {
    try {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    catch (e : Exception) {
        e.printStackTrace()
    }
}


fun ImageView.searchForActorAndLoad(actorName :  String){
    doAsync {

        val client = BingImageSearchManager.authenticate(BING_API_KEY)
        val imageResults = client.bingImages().search(actorName, SearchOptionalParameter().withImageType(
            ImageType.PHOTO)).value()

        uiThread {
            if(imageResults.isNotEmpty()){
                Picasso
                    .get()
                    .load(imageResults.first().thumbnailUrl())
                    .transform(CircleTransform())
                    .placeholder(getRandomDrawbleColor())
                    .into(it)
            } else {

            }
        }
    }
}


private val vibrantLightColorList = arrayOf(
    ColorDrawable(Color.parseColor("#9ACCCD")),
    ColorDrawable(Color.parseColor("#8FD8A0")),
    ColorDrawable(Color.parseColor("#CBD890")),
    ColorDrawable(Color.parseColor("#DACC8F")),
    ColorDrawable(Color.parseColor("#D9A790")),
    ColorDrawable(Color.parseColor("#D18FD9")),
    ColorDrawable(Color.parseColor("#FF6772")),
    ColorDrawable(Color.parseColor("#DDFB5C"))
)

fun getRandomDrawbleColor(): ColorDrawable {
    val idx = Random().nextInt(vibrantLightColorList.size)
    return vibrantLightColorList[idx]
}