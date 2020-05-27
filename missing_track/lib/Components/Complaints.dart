import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:missingtrack/Components/details.dart';

class complaints extends StatefulWidget {
  @override
  _complaintsState createState() => _complaintsState();
}

class _complaintsState extends State<complaints> {
  List missing = [];
  bool _isFetch = false;

  @override
  void initState() {
    super.initState();
    this.fetchComplaint();
  }

  fetchComplaint() async {
    setState(() {
      _isFetch = true;
    });
    var url = "http://192.168.43.32/missing/api/all.php";
    var response = await http.get(url);

    if (response.statusCode == 200) {
      var items = json.decode(response.body);
      setState(() {
        missing = items;
        _isFetch = false;
      });
    } else {
      missing = [];
      _isFetch = false;
    }
  }

  Widget getData() {
    if (missing.contains(null) || missing.length < 0 || _isFetch == true) {
      return Center(
          child: CircularProgressIndicator(
            valueColor: new AlwaysStoppedAnimation<Color>(Colors.blueAccent),
          ));
    }
    return GridView.builder(
        itemCount: missing.length,
        gridDelegate:
        new SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 2),
        itemBuilder: (BuildContext context, int index) {
          return getCard(missing[index]);
        });
  }

  Widget getCard(item){
    var fullname = item['personFullName'];
    var address = item['address'];
    var photo = item['photo'];
    var gender = item['gender'];
    var birthdate = item['birthdate'];
    var parentMobile = item['parentMobile'];
    var parentFullName = item['parentFullName'];
    var dateMissing = item['dateMissing'];

    return Card(
      child: Hero(
        tag: fullname.toString(),
        child: Material(
          child: InkWell(
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => details(
                          title: 'Complaint Details',
                          fullname: fullname.toString(),
                          address: address.toString(),
                          photo: photo.toString(),
                          gender: gender.toString(),
                          birthdate: birthdate.toString(),
                          parentMobile: parentMobile.toString(),
                          parentFullName: parentFullName.toString(),
                          dateMissing: dateMissing.toString())));
            },
            child: GridTile(
              footer: Container(
                color: Colors.white70,
                child: ListTile(
                  leading:Column(
                    children: [
                      Padding(
                        padding: EdgeInsets.only(top:10,left: 5),
                        child:Text(
                          fullname.toString(),
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ),
                      SizedBox(height:5),
                      Text(gender.toString(),
                        style: TextStyle(
                            fontWeight: FontWeight.w800,
                            color: Colors.red
                        ),
                        textAlign: TextAlign.end,
                      ),
                    ],
                  )
                ),
              ),
              child:  Image(
                fit: BoxFit.cover,
                image: NetworkImage(photo.toString())
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Complaints"),
      ),
      body:getData(),
    );
  }
}
