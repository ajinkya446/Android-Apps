import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'E-Library',
      debugShowCheckedModeBanner: false,
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    List items=[
    "1","2"
  ];
    return Scaffold(
      appBar: AppBar(title: Text("Complaint JSON"),
      ),
      body: ListView.builder(
        itemCount: items.length,
        itemBuilder: (context,index){
        return getCard();
      }),
    );
  }
Widget getCard(){
  return Card(
    child: Padding(
      padding: const EdgeInsets.all(8.0),
      child: ListTile(
        title: Row(
          children: <Widget>[
            Container(width: 60,
            height: 60,
            decoration: BoxDecoration(
              color: Colors.white54,
              borderRadius: BorderRadius.circular(60/2),
              image: DecorationImage(image: NetworkImage("http:192.168.43.32/missing/api/missing_photo/MISSING_Satish padwal.jpg"))
            ),
          )
        ],
        ),
      ),
    ),  
  );
}

}
