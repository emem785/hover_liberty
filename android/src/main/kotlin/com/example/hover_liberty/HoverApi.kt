package com.example.hover_liberty

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverConfigException
import com.hover.sdk.api.HoverHelper
import io.flutter.plugins.Pigeon
import com.hover.sdk.api.HoverParameters
import kotlin.coroutines.cancellation.CancellationException


class HoverApi() : Pigeon.HoverApi {
    var hoverResponse: Pigeon.Result<Pigeon.HoverResponse>? = null
    private var flutterActivity: Activity? = null

    fun initialize(activity: Activity) {
        flutterActivity = activity
        Hover.initialize(activity)
    }


    override fun initiateRequest(
        request: Pigeon.HoverRequest,
        result: Pigeon.Result<Pigeon.HoverResponse>?
    ) {
            hoverResponse = result
            val intent = HoverParameters.Builder(flutterActivity)
                .request(request.actionId)  //Action ID
                .finalMsgDisplayTime(0)


            request.extraData?.forEach { entry ->
                intent.extra(entry.key, entry.value)
            }

            flutterActivity?.startActivityForResult(intent.buildIntent(), 0)
    }

}