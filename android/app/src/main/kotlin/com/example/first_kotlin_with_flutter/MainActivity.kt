package com.example.first_kotlin_with_flutter

import android.content.Context
import android.view.View
import android.widget.Button
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import io.flutter.plugin.common.StandardMessageCodec

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.channel"
    private lateinit var methodChannel: MethodChannel
    private var nativeButton: NativeButton? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Register the native button view
        flutterEngine.platformViewsController
            .registry
            .registerViewFactory("native-button", NativeButtonFactory())

        // Set up method channel for Flutter communication
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        methodChannel.setMethodCallHandler { call, result ->
            if (call.method == "clickNativeButton") {
                nativeButton?.clickButton()
                result.success("Button clicked from Flutter!")
            } else {
                result.notImplemented()
            }
        }
    }

    private fun getNativeMessage(): String {
        println(" called from Kotlin")
        return "Hello from Kotlin!"
    }
}

// Custom Android Button View
class NativeButton(context: Context) : PlatformView {
    private val button: Button = Button(context).apply {
        text = "Click Me (Native)"
    }

    override fun getView(): View {
        return button
    }

    override fun dispose() {}

    // Function to trigger button click from Flutter
    fun clickButton() {
        button.performClick()
        button.text = "Clicked from Flutter!"
    }
}

// Factory to create the native button view
class NativeButtonFactory : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
    override fun create(context: Context, id: Int, args: Any?): PlatformView {
        return NativeButton(context)
    }
}

