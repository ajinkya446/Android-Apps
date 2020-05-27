import 'package:flutter/material.dart';
import 'package:lostpeople/src/welcomePage.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Missing Login',
      debugShowCheckedModeBanner: false,
      home: welcome(),
    );
  }
}
