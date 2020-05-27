import 'package:flutter/material.dart';
import 'package:missingtrack/Components/settings.dart';
import 'package:missingtrack/src/LoginPage.dart';
import 'package:shared_preferences/shared_preferences.dart';

class account extends StatefulWidget {
  String name = "";
  String pic = "";

  account({this.name, this.pic});

  @override
  _accountState createState() => _accountState(name: name);
}

class _accountState extends State<account> {
  String name = "";
  SharedPreferences sharedPreferences;

  _accountState({this.name});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Account Setting"),
        ),
        body: ListView.builder(
            itemCount: 1,
            itemBuilder: (context, index) {
              return getCard();
            }));
  }

  Widget getCard() {
    return Card(
      child: Padding(
          padding: EdgeInsets.all(15.0),
          child: ListTile(
            title: Column(
              children: [
                Row(
                  children: [
                    CircleAvatar(
                      backgroundColor: Colors.transparent,
                      child: Icon(
                        Icons.account_circle,
                        color: Colors.lightBlue,
                        size: 70,
                      ),
                    ),
                    Padding(
                        padding: EdgeInsets.only(top: 30, left: 40),
                        child: Expanded(
                            child: Text(
                          name.toString(),
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            fontStyle: FontStyle.italic,
                            fontSize: 30,
                            color: Colors.indigoAccent,
                          ),
                        )))
                  ],
                ),
                SizedBox(
                  height: 30,
                ),
                Divider(),
                InkWell(
                    onTap: () {
                      Navigator.push(context,
                          MaterialPageRoute(builder: (context) => settings()));
                    },
                    child: Padding(
                      padding:
                          EdgeInsets.only(top: 20, bottom: 20),
                      child: Row(
                        children: [
                          Icon(Icons.vpn_key),
                          SizedBox(
                            width: 30,
                          ),
                          Text(
                            "Account",
                            style: TextStyle(
                              fontStyle: FontStyle.normal,
                              fontSize: 20,
                            ),
                          )
                        ],
                      ),
                    )),
                InkWell(
                  onTap: (){

                  },
                    child: Padding(
                  padding: EdgeInsets.only(top: 20, bottom: 20),
                  child: Row(
                    children: [
                      Icon(Icons.help_outline),
                      SizedBox(
                        width: 30,
                      ),
                      Text(
                        "Help",
                        style: TextStyle(
                          fontStyle: FontStyle.normal,
                          fontSize: 20,
                        ),
                      )
                    ],
                  ),
                )),
                InkWell(
                    onTap: (){

                    },
                    child:Padding(
                      padding: EdgeInsets.only(top: 20,bottom: 20),
                      child:Row(
                        children: [
                          Icon(Icons.perm_device_information),
                          SizedBox(
                            width: 30,
                          ),
                          Text(
                            "App Info",
                            style: TextStyle(
                              fontStyle: FontStyle.normal,
                              fontSize: 20,
                            ),
                          )
                        ],
                      ),
                    )
                ),
                InkWell(
                  onTap: (){

                  },
                  child:Padding(
                    padding: EdgeInsets.only(top: 20,bottom: 20),
                    child:Row(
                      children: [
                        Icon(Icons.info_outline),
                        SizedBox(
                          width: 30,
                        ),
                        Text(
                          "About Us",
                          style: TextStyle(
                            fontStyle: FontStyle.normal,
                            fontSize: 20,
                          ),
                        )
                      ],
                    ),
                  )
                ),
                InkWell(
                  onTap: () async {
                    sharedPreferences =
                    await SharedPreferences.getInstance();
                    sharedPreferences.clear();
                    sharedPreferences.commit();
                    Navigator.of(context).pushAndRemoveUntil(
                        MaterialPageRoute(
                            builder: (context) => LoginPage()),
                            (Route<dynamic> route) => false);
                  },
                  child: Padding(
                      padding: EdgeInsets.only(top: 10,bottom: 10),
                    child: Row(
                      children: [
                         Icon(Icons.exit_to_app),
                        SizedBox(
                          width: 30,
                        ),
                        Text(
                          "Sign Out",
                          style: TextStyle(
                            fontStyle: FontStyle.normal,
                            fontSize: 20,
                          ),
                        )
                      ],
                    ),
                  ),
                ),

              ],
            ),
          )),
    );
  }
}
