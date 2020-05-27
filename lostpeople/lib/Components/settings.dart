import 'package:flutter/material.dart';

class settings extends StatefulWidget {
  @override
  _settingsState createState() => _settingsState();
}

class _settingsState extends State<settings> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Settings"),
      ),
      body: Container(
        child: Column(
          children: [
            InkWell(
              onTap: (){

              },
                child: Padding(
                    padding: EdgeInsets.only(left: 20, top: 20,bottom: 20),
                    child: Row(
                      children: [
                        Icon(Icons.lock_outline),
                        SizedBox(
                          width: 30,
                        ),
                        Text(
                          "Change Password",
                          style: TextStyle(
                              fontStyle: FontStyle.normal, fontSize: 18),
                        )
                      ],
                    ))),
            InkWell(
                onTap: (){

                },
                child: Padding(
                    padding: EdgeInsets.only(left: 20, top: 20,bottom: 20),
                    child: Row(
                      children: [
                        Icon(Icons.verified_user),
                        SizedBox(
                          width: 30,
                        ),
                        Text(
                          "Change User Name",
                          style: TextStyle(
                              fontStyle: FontStyle.normal, fontSize: 18),
                        )
                      ],
                    ))),
            InkWell(
                onTap: (){

                },
                child: Padding(
                    padding: EdgeInsets.only(left: 20, top: 20,bottom: 20),
                    child: Row(
                      children: [
                        Icon(Icons.delete_outline),
                        SizedBox(
                          width: 30,
                        ),
                        Text(
                          "Delete Account",
                          style: TextStyle(
                              fontStyle: FontStyle.normal, fontSize: 18),
                        )
                      ],
                    ))),
            InkWell(
                onTap: (){

                },
                child: Padding(
                    padding: EdgeInsets.only(left: 20, top: 20,bottom: 20),
                    child: Row(
                      children: [
                        Icon(Icons.info_outline),
                        SizedBox(
                          width: 30,
                        ),
                        Text(
                          "Update Profile Info",
                          style: TextStyle(
                              fontStyle: FontStyle.normal, fontSize: 18),
                        )
                      ],
                    ))),
          ],
        ),
      ),
    );
  }
}
