import 'package:flutter/material.dart';
import 'Home.dart';

void main() {
  runApp(MaterialApp(
    title: "Exploring UI Widget",
    home: Scaffold(
        appBar: AppBar(
          title: Text("Long List Example"),
        ),
        body: getListView(),
        floatingActionButton: FloatingActionButton(
          onPressed: (){
            debugPrint("Add More item");
          },
          child: Icon(Icons.add),
          tooltip: "Add One More Item",
        ),
    ),
  ));
}

void showSnackbar(BuildContext context,String Item){
  var snackbar=SnackBar(
    content: Text("You Just Clicked $Item"),
    action: SnackBarAction(
      label:"Undo",
      onPressed: (){
        debugPrint("Undo Operation");
      },
    ),
  );

  Scaffold.of(context).showSnackBar(snackbar);
}

List<String> getListElement() {
  var items = List<String>.generate(100, (counter) => "Item $counter");
  return items;
}

Widget getListView() {
  var listItems = getListElement();
  var listView = ListView.builder(itemBuilder: (context, index) {
    return ListTile(
      leading: Icon(Icons.arrow_right),
      title: Text(listItems[index]),
      onTap:(){
        showSnackbar(context,listItems[index]);
      }
    );
  });
  return listView;
}
