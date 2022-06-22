package com.example.hover_liberty

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry
import io.flutter.hover.Pigon

/** HoverLibertyPlugin */
class HoverLibertyPlugin : FlutterPlugin, MethodCallHandler, ActivityAware,
    PluginRegistry.ActivityResultListener {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private var hoverApi: HoverApi? = null


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        hoverApi = HoverApi()
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "hover_liberty")
        Pigon.HoverApi.setup(flutterPluginBinding.binaryMessenger, hoverApi)
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        hoverApi?.initialize(binding.activity)
        binding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        hoverApi?.initialize(binding.activity)
    }

    override fun onDetachedFromActivity() {
        Log.d(TAG, "onDetachedFromActivity: Detached")

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {

        val resultError =
            data?.getStringExtra("error").toString()

        val resultMessage: List<String>? =
            data?.getStringArrayExtra("session_messages")?.toList()

        var response = Pigon.HoverResponse().apply {
            responseCode = resultCode.toLong()
            errorMessage = resultError
            messages = resultMessage
        }
        hoverApi?.hoverResponse?.success(response)

        return true
    }
}
