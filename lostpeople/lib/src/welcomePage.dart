import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:lostpeople/Components/Home.dart';
import 'package:lostpeople/Model/user.dart';
import 'package:lostpeople/src/LoginPage.dart';
import 'package:lostpeople/src/SignUp.dart';

class welcome extends StatefulWidget {
  @override
  _welcomeState createState() => _welcomeState();
}

enum LoginStatus { notSignIn, signIn }

class _welcomeState extends State<welcome> {
  LoginStatus _loginStatus = LoginStatus.notSignIn;
  var value;
  SharedPreferences sharedPreferences;

  @override
  void initState(){
    super.initState();
    checkLogin();
  }

  checkLogin() async{
    sharedPreferences=await SharedPreferences.getInstance();
    if(sharedPreferences.getString("token")!=null){
      Navigator.pushAndRemoveUntil(
          context,
          MaterialPageRoute(
              builder: (context) => Home(emailId:sharedPreferences.getString("token"))),
          ModalRoute.withName("/Home"));
    }else{
      Navigator.pushAndRemoveUntil(
          context,
          MaterialPageRoute(
              builder: (context) => LoginPage()),
          ModalRoute.withName("/LoginPage"));
    }
  }

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

  Widget _login() {
    return InkWell(
      child: Container(
        width: MediaQuery.of(context).size.width,
        padding: EdgeInsets.symmetric(vertical: 13),
        alignment: Alignment.center,
        decoration: BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(5)),
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
          style: TextStyle(fontSize: 20, color: Color(0xfff7892b)),
        ),
      ),
      onTap: () {
        Navigator.push(
            context, MaterialPageRoute(builder: (context) => LoginPage()));
      },
    );
  }

  Widget _register() {
    return InkWell(
      child: Container(
        width: MediaQuery.of(context).size.width,
        padding: EdgeInsets.symmetric(vertical: 13),
        alignment: Alignment.center,
        decoration: BoxDecoration(
            borderRadius: BorderRadius.all(Radius.circular(5)),
            border: Border.all(color: Colors.blueAccent, width: 2),
            color: Colors.blueAccent),
        child: Text(
          'Sign up',
          style: TextStyle(
              fontSize: 20, color: Colors.white, fontStyle: FontStyle.italic),
        ),
      ),
      onTap: () {
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => SignUpPage()));
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: EdgeInsets.symmetric(horizontal: 20),
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
            gradient: LinearGradient(
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
                colors: [Color(0xfffbb448), Color(0xffe46b10)])),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            _title(),
            SizedBox(
              height: 80,
            ),
            _login(),
            SizedBox(
              height: 20,
            ),
            _register(),
          ],
        ),
      ),
    );
  }
}
