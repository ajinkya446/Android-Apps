import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:lostpeople/Components/Home.dart';
import 'package:lostpeople/src/SignUp.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

enum LoginStatus { notSignIn, signIn }

class _LoginPageState extends State<LoginPage> {
  var _formKey = GlobalKey<FormState>();
  String nameKey = "_key_name";
  var name = "";
  bool _isLoading = false;

  TextEditingController email = TextEditingController();
  TextEditingController password = TextEditingController();

  Widget _title() {
    return RichText(
      textAlign: TextAlign.center,
      text: TextSpan(
          text: 'M-',
          style: TextStyle(color: Colors.white, fontSize: 30),
          children: [
            TextSpan(
              text: 'I',
              style: TextStyle(color: Colors.black, fontSize: 24),
            ),
            TextSpan(
              text: 'Tracking',
              style: TextStyle(color: Colors.white, fontSize: 30),
            ),
          ]),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        key: _globalKey,
        body: Form(
          key: _formKey,
          child: Container(
            width: MediaQuery
                .of(context)
                .size
                .width,
            height: MediaQuery
                .of(context)
                .size
                .height,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(4.0),
              boxShadow: <BoxShadow>[
                BoxShadow(
                    color: Colors.grey.shade200,
                    offset: Offset(2, 4),
                    blurRadius: 5,
                    spreadRadius: 2)
              ],
              gradient: LinearGradient(
                  begin: Alignment.topCenter,
                  end: Alignment.bottomCenter,
                  colors: [Color(0xfffbb448), Color(0xffe46b10)]),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                _title(),
                SizedBox(height: 30),
                Padding(
                  padding: EdgeInsets.all(10.0),
                  child: TextFormField(
                    cursorColor: Colors.redAccent,
                    keyboardType: TextInputType.emailAddress,
                    controller: email,
                    validator: (String value) {
                      if (value.isEmpty) {
                        return 'Please Username';
                      }
                    },
                    decoration: InputDecoration(
                        labelText: 'Enter Username',
                        hintText: 'Username',
                        icon: Icon(Icons.person),
                        errorStyle: TextStyle(
                          color: Colors.yellowAccent,
                          fontSize: 15.0,
                        ),
                        hintStyle: TextStyle(color: Colors.white54),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(4.0),
                        )),
                  ),
                ),
                SizedBox(
                  height: 10.0,
                ),
                Padding(
                  padding: EdgeInsets.only(left: 10.0, right: 10.0),
                  child: TextFormField(
                    cursorColor: Colors.redAccent,
                    obscureText: true,
                    autofocus: false,
                    controller: password,
                    validator: (String value) {
                      if (value.isEmpty) {
                        return 'Please Enter Password';
                      }
                    },
                    decoration: InputDecoration(
                        labelText: 'Enter Password',
                        hintText: 'Password',
                        icon: Icon(Icons.lock),
                        errorStyle: TextStyle(
                          color: Colors.yellowAccent,
                          fontSize: 15.0,
                        ),
                        hintStyle: TextStyle(color: Colors.white54),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(4.0),
                        )),
                  ),
                ),
                SizedBox(
                  height: 8.0,
                ),
                InkWell(
                  child: Padding(
                    padding: EdgeInsets.all(10.0),
                    child: Container(
                      width: 150,
                      height: 30.0,
                      alignment: Alignment.center,
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(4.0),
                          boxShadow: <BoxShadow>[
                            BoxShadow(
                                color: Color(0xffdf8e33).withAlpha(100),
                                offset: Offset(2, 4),
                                blurRadius: 8,
                                spreadRadius: 2)
                          ],
                          color: Colors.white),
                      child: Text(
                        'Login',
                        style:
                        TextStyle(fontStyle: FontStyle.italic, fontSize: 20.0),
                      ),
                    ),
                  ),
                  onTap: () {
                    setState(() {
                      _isLoading = true;
                    });
                    if (_formKey.currentState.validate()) {
                      sign(email.text, password.text);
                    }
                  },
                ),
                SizedBox(
                  height: 5,
                ),
                Padding(
                    padding: EdgeInsets.only(left: 180),
                    child: Text(
                      'Forgot Password?',
                      style: TextStyle(
                          fontSize: 14, fontWeight: FontWeight.bold),
                    )),
                SizedBox(
                  height: 10,
                ),
                Padding(
                    padding: EdgeInsets.only(top: 10),
                    child: InkWell(
                      onTap: () {
                        Navigator.pushReplacement(context,
                            MaterialPageRoute(
                                builder: (context) => SignUpPage()));
                      },
                      child: Text(
                        "Don't have an account?" + "  " + "Register",
                        style: TextStyle(color: Colors.white, fontSize: 16),
                      ),
                    ))
              ],
            ),
          ),
        ));
  }

  sign(String username, password) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    Map data = {'username': username, 'password': password};
    var jsonData = null;
    var response = await http.post(
        "http://192.168.43.32/missing/api/login.php", body: data);
    if (response.statusCode == 200) {
      String status = response.body;
      setState(() {
        _isLoading = false;
        if (response.body.trim() == "true") {
          sharedPreferences.setString("token", email.text);
          Navigator.pushAndRemoveUntil(
              context,
              MaterialPageRoute(
                  builder: (context) => Home(emailId:email.text)),
              ModalRoute.withName("/Home"));
        } else {
          _showSnackBar(context);
        }
      });
    }
  }

  //Snackbar Code
  final GlobalKey<ScaffoldState> _globalKey = new GlobalKey<ScaffoldState>();

  void _showSnackBar(BuildContext context) {
    final snackbar = SnackBar(content: Text("Invalied Username & password"),
      backgroundColor: Colors.white54,);
//    Scaffold.of(context).showSnackBar(snackbar);
    _globalKey.currentState.showSnackBar(snackbar);
  }
}
