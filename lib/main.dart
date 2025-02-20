import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const platform = MethodChannel("com.example.channel");

  Future<void> clickNativeButton() async {
    try {
      final result = await platform.invokeMethod("clickNativeButton");
      print(result); // Logs response from Kotlin
    } catch (e) {
      print("Error: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text("Flutter with Native Button")),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                  height: 100,
                  child: AndroidView(
                    viewType: "native-button",
                    layoutDirection: TextDirection.ltr,
                    creationParams: {},
                    creationParamsCodec: StandardMessageCodec(),
                    hitTestBehavior: PlatformViewHitTestBehavior.opaque,
                    gestureRecognizers: {},
                  )),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: clickNativeButton,
              child: Text("Click Native Button from Flutter"),
            ),
          ],
        ),
      ),
    );
  }
}
