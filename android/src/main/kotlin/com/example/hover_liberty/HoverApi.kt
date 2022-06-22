package com.example.hover_liberty

import android.app.Activity
import com.hover.sdk.api.Hover
import io.flutter.hover.Pigon
import com.hover.sdk.api.HoverParameters


class HoverApi() : Pigon.HoverApi {
    var hoverResponse: Pigon.Result<Pigon.HoverResponse>? = null
    private var flutterActivity: Activity? = null

    fun initialize(activity: Activity) {
        flutterActivity = activity
        Hover.initialize(activity)
    }


    override fun initiateRequest(
        request: Pigon.HoverRequest,
        result: Pigon.Result<Pigon.HoverResponse>?
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