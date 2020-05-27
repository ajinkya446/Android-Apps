import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lostpeople/Components/Complaints.dart';
import 'package:lostpeople/Components/account.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:lostpeople/Components/details.dart';
import 'package:lostpeople/Model/user.dart';
import 'package:lostpeople/src/LoginPage.dart';

class Home extends StatefulWidget {
  var emailId;

  Home({this.emailId});

  @override
  _HomeState createState() => _HomeState(idEmail: emailId);
}

class _HomeState extends State<Home> {
  var idEmail;
  String nameKey = "";
  List items = ["1", "2"];
  User user;
  SharedPreferences sharedPreferences;
  List missing = [];
  bool _isFetch = false;

  _HomeState({this.idEmail});

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0.0,
        title: Text("M I Tracking"),
        actions: <Widget>[
          new IconButton(
              icon: Icon(
                Icons.search,
                color: Colors.white,
              ),
              onPressed: () {}),
          IconButton(
              icon: Icon(
                Icons.exit_to_app,
                color: Colors.white,
              ),
              onPressed: () async {
                sharedPreferences = await SharedPreferences.getInstance();
                sharedPreferences.clear();
                sharedPreferences.commit();
                Navigator.of(context).pushAndRemoveUntil(
                    MaterialPageRoute(builder: (context) => LoginPage()),
                    (Route<dynamic> route) => false);
              }),
        ],
      ),
      drawer: new Drawer(
        child: new ListView(
          children: <Widget>[
            //Navigation drawer
            Row(
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.all(5.0),
                  child: IconButton(
                    onPressed: () {},
                    iconSize: 50.0,
                    icon: new CircleAvatar(
                      backgroundColor: Colors.transparent,
                      child: Icon(
                        Icons.person,
                        color: Colors.blueAccent,
                      ),
                    ),
                  ),
                ),
                SizedBox(
                  width: 10,
                ),
                Text(
                  idEmail,
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 17),
                ),
                SizedBox(
                  width: 80,
                ),
                IconButton(
                  onPressed: () {
                    Navigator.push(context, MaterialPageRoute(
                        builder: (context)=>account(name:idEmail.toString(),)
                    ));
                  },
                  alignment: Alignment.centerRight,
                  iconSize: 50.0,
                  icon: new CircleAvatar(
                    backgroundColor: Colors.transparent,
                    child: Icon(
                      Icons.account_balance_wallet,
                      color: Colors.blueAccent,
                    ),
                  ),
                ),
              ],
            ),
//             UserAccountsDrawerHeader(
//              accountName: Text("Ajinkya Aher"),
//              accountEmail: Text("ajinkya446@gmail.com"),
//              currentAccountPicture: GestureDetector(
//                child: new CircleAvatar(
//                  backgroundColor: Colors.grey,
//                  child: Icon(
//                    Icons.person,
//                    color: Colors.white,
//                  ),
//                ),
//              ),
//              decoration: new BoxDecoration(color: Colors.blue),
//            ),
            //Body
            Divider(
              thickness: 2,
              color: Colors.redAccent,
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text("Register MIR Card"),
                leading: Icon(Icons.add_to_queue, color: Colors.red),
              ),
            ),
            InkWell(
              onTap: () {
                Navigator.push(context,MaterialPageRoute(builder:
                (context)=>complaints()));
              },
              child: ListTile(
                title: Text('Complaints'),
                leading: Icon(Icons.view_compact, color: Colors.red),
              ),
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('My City Area'),
                leading: Icon(Icons.location_city, color: Colors.red),
              ),
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('Found People'),
                leading: Icon(Icons.people, color: Colors.red),
              ),
            ),
            Divider(),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('Settings'),
                leading: Icon(Icons.settings, color: Colors.blue),
              ),
            ),
            InkWell(
              onTap: () {},
              child: ListTile(
                title: Text('About'),
                leading: Icon(Icons.help, color: Colors.green),
              ),
            ),
          ],
        ),
      ),
      body: getList(),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          //navidateNoteDetail(Note('','',2),'Add More');
        },
        tooltip: 'Add More',
        child: Icon(Icons.add),
      ),
    );
  }

  Widget getList() {
    if (missing.contains(null) || missing.length < 0 || _isFetch == true) {
      return Center(
          child: CircularProgressIndicator(
        valueColor: new AlwaysStoppedAnimation<Color>(Colors.blueAccent),
      ));
    }
    return ListView.builder(
        itemCount: missing.length,
        itemBuilder: (context, index) {
          return getCard(missing[index]);
        });
  }

  Widget getCard(item) {
    var fullname = item['personFullName'];
    var address = item['address'];
    var photo = item['photo'];
    var gender = item['gender'];
    var birthdate = item['birthdate'];
    var parentMobile = item['parentMobile'];
    var parentFullName = item['parentFullName'];
    var dateMissing = item['dateMissing'];

    return Card(
      child: Padding(
          padding: const EdgeInsets.all(8.0),
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
              child: ListTile(
                title: Row(
                  children: <Widget>[
                    Container(
                      width: 60,
                      height: 60,
                      decoration: BoxDecoration(
                          color: Colors.blue,
                          borderRadius: BorderRadius.circular(60 / 2),
                          image: DecorationImage(
                              fit: BoxFit.cover,
                              image: NetworkImage(photo.toString()))),
                    ),
                    SizedBox(
                      width: 10,
                    ),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Text(
                          fullname.toString(),
                          style: TextStyle(
                              fontSize: 17, fontWeight: FontWeight.bold),
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Text(
                          address.toString(),
                          style: TextStyle(
                            fontSize: 12,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
                trailing: GestureDetector(
                  child:Icon(
                    Icons.delete,
                    color: Colors.red,
                  ),
                  onTap: (){


                  },
                ),
              ))),
    );
  }
}
