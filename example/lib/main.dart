import 'dart:async';

import 'package:flutter/material.dart';
import 'package:hover_liberty/hover_liberty.dart';
import 'package:logger/logger.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late HoverApi _hoverApi;
  Logger logger = Logger();

  @override
  void initState() {
    super.initState();

    _hoverApi = HoverApi();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    try {
      final hoverReq = HoverRequest(actionId: "b4f9eb4a");
      final response = await _hoverApi.initiateRequest(hoverReq);
      logger.d(response.errorMessage);
      logger.d(response.messages);
      logger.d(response.responseCode);
    } catch (e) {
      Logger().d(e.toString());
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        floatingActionButton: FloatingActionButton(
          child: Text("send"),
          onPressed: initPlatformState,
        ),
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('hhh'),
        ),
      ),
    );
  }
}
