package `in`.codepredators.vedanta.volley

import `in`.codepredators.vedanta.Constants
import `in`.codepredators.vedanta.Constants.TAG
import `in`.codepredators.vedanta.Constants.diseasesLink
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class DiseaseRecommender(val context: Context) : Constants {

    lateinit var onCompleteListener: OnCompleteListener

    fun getDisease(vararg symptoms: String): DiseaseRecommender {
        val queue = Volley.newRequestQueue(context)

        val link = StringBuilder(diseasesLink)

        for (i in symptoms.indices)
            link.append("input" + (i + 1) + "=" + symptoms[i] + "&")

        val stringRequest = StringRequest(Request.Method.GET, link.toString(),
                Response.Listener<String?> { response ->
                    onCompleteListener.onResult(response!!)
                },
                Response.ErrorListener { error ->
                    onCompleteListener.onError(error.localizedMessage!!)
                })
        queue.add(stringRequest)
        return this
    }

    interface OnCompleteListener {
        fun onResult(result: String)
        fun onError(err: String)
    }
}