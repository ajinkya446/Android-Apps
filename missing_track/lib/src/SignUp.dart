import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:missingtrack/Model/user.dart';
import 'package:missingtrack/src/LoginPage.dart';

class SignUpPage extends StatefulWidget {
  User user;

  SignUpPage();

  @override
  _SignUpPageState createState() => _SignUpPageState();
}

class _SignUpPageState extends State<SignUpPage> {
  var _formKey = GlobalKey<FormState>();
  User user;
  var _gender = ['Select', 'Male', 'Female'];
  var _current = '';

  void initState() {
    super.initState();
    _current = _gender[0];
  }

  _SignUpPageState();

  TextEditingController email = TextEditingController();
  TextEditingController password = TextEditingController();
  TextEditingController name = TextEditingController();
  TextEditingController dob = TextEditingController();
  TextEditingController username = TextEditingController();
  TextEditingController contact = TextEditingController();
  TextEditingController designation = TextEditingController();

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
            child: SingleChildScrollView(
                child: Stack(
                    children: <Widget>[
                      Container(
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
                                keyboardType: TextInputType.text,
                                controller: name,
                                validator: (String value) {
                                  if (value.isEmpty) {
                                    return 'Please Enter Full Name';
                                  }
                                },
                                decoration: InputDecoration(
                                    labelText: 'Enter Full Name',
                                    hintText: 'Full Name',
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
                              height: .0,
                            ),
                            Padding(
                              padding: EdgeInsets.only(left: 10.0, right: 10.0),
                              child: TextFormField(
                                cursorColor: Colors.redAccent,
                                controller: email,
                                validator: (String value) {
                                  if (value.isEmpty) {
                                    return 'Please Enter Email';
                                  }
                                },
                                decoration: InputDecoration(
                                    labelText: 'Enter Email',
                                    hintText: 'Email-Address',
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
                                controller: username,
                                validator: (String value) {
                                  if (value.isEmpty) {
                                    return 'Please Enter Username';
                                  }
                                },
                                decoration: InputDecoration(
                                    labelText: 'Enter Username',
                                    hintText: 'Username',
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
                            Row(
                              children: <Widget>[
                                Expanded(
                                  child: Padding(
                                    padding: EdgeInsets.only(
                                        left: 10.0, right: 10.0),
                                    child: TextFormField(
                                      cursorColor: Colors.redAccent,
                                      controller: designation,
                                      validator: (String value) {
                                        if (value.isEmpty) {
                                          return 'Please Enter Designation';
                                        }
                                      },
                                      decoration: InputDecoration(
                                          labelText: 'Enter Designation',
                                          hintText: 'Designation',
                                          errorStyle: TextStyle(
                                            color: Colors.yellowAccent,
                                            fontSize: 15.0,
                                          ),
                                          hintStyle: TextStyle(
                                              color: Colors.white54),
                                          border: OutlineInputBorder(
                                            borderRadius: BorderRadius.circular(
                                                4.0),
                                          )),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Padding(
                                      padding: EdgeInsets.only(
                                          left: 10, right: 10),
                                      child: DropdownButton<String>(
                                        items: _gender.map((String value) {
                                          return DropdownMenuItem<String>(
                                            value: value,
                                            child: Text(value),
                                          );
                                        }).toList(),
                                        value: _current,
                                        onChanged: (String newValue) {
                                          _onDropDowntemSelect(newValue);
                                        },
                                      )),
                                )
                              ],
                            ),
                            SizedBox(
                              height: 5,
                            ),
                            Row(
                              children: <Widget>[
                                Expanded(
                                  child: Padding(
                                    padding: EdgeInsets.only(
                                        left: 10.0, right: 10.0),
                                    child: TextFormField(
                                      cursorColor: Colors.redAccent,
                                      controller: dob,
                                      validator: (String value) {
                                        if (value.isEmpty) {
                                          return 'Please Enter dob';
                                        }
                                      },
                                      decoration: InputDecoration(
                                          labelText: 'Enter dob',
                                          hintText: 'dob',
                                          errorStyle: TextStyle(
                                            color: Colors.yellowAccent,
                                            fontSize: 15.0,
                                          ),
                                          hintStyle: TextStyle(
                                              color: Colors.white54),
                                          border: OutlineInputBorder(
                                            borderRadius: BorderRadius.circular(
                                                4.0),
                                          )),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Padding(
                                    padding: EdgeInsets.only(
                                        left: 10.0, right: 10.0),
                                    child: TextFormField(
                                      cursorColor: Colors.redAccent,
                                      controller: contact,
                                      validator: (String value) {
                                        if (value.isEmpty) {
                                          return 'Please Enter contact';
                                        }
                                      },
                                      decoration: InputDecoration(
                                          labelText: 'Enter contact',
                                          hintText: 'contact',
                                          errorStyle: TextStyle(
                                            color: Colors.yellowAccent,
                                            fontSize: 15.0,
                                          ),
                                          hintStyle: TextStyle(
                                              color: Colors.white54),
                                          border: OutlineInputBorder(
                                            borderRadius: BorderRadius.circular(
                                                4.0),
                                          )),
                                    ),
                                  ),
                                )
                              ],
                            ),
                            SizedBox(
                              height: 5,
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
                                            color: Color(0xffdf8e33).withAlpha(
                                                100),
                                            offset: Offset(2, 4),
                                            blurRadius: 8,
                                            spreadRadius: 2)
                                      ],
                                      color: Colors.white),
                                  child: Text(
                                    'Sign Up',
                                    style: TextStyle(
                                        fontStyle: FontStyle.italic,
                                        fontSize: 20.0),
                                  ),
                                ),
                              ),
                              onTap: () {
                                if (_formKey.currentState.validate()) {
                                  signUpData(
                                      name.text,
                                      email.text,
                                      username.text,
                                      password.text,
                                      dob.text,
                                      contact.text,
                                      designation.text,
                                      _current);
                                  name.clear();
                                  email.clear();
                                  password.clear();
                                  username.clear();
                                  password.clear();
                                  dob.clear();
                                  contact.clear();
                                  designation.clear();
                                  _current = _gender[0];
                                }
                                setState(() {});
                              },
                            ),
                            SizedBox(
                              height: 10,
                            ),
                            Padding(
                                padding: EdgeInsets.only(top: 10),
                                child: InkWell(
                                  onTap: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) => LoginPage()));
                                  },
                                  child: Text(
                                    "Already have an account?" + "  " +
                                        "Sign In",
                                    style: TextStyle(
                                        color: Colors.white, fontSize: 16),
                                  ),
                                )),
                          ],
                        ),
                      ),
                    ]
                )
            )
        )
    );
  }

  void _onDropDowntemSelect(String newValue) {
    setState(() {
      this._current = newValue;
    });
  }

  signUpData(String rname, String email, String username, String password,
      String dob, String mobile, String designation, String gender) async {
    Map data = {
      'rname': rname,
      'email': email,
      'username': username,
      'password': password,
      'dob': dob,
      'mobile': mobile,
      'designation': designation,
      'gender': gender
    };
    var response = await http
        .post("http://192.168.43.32/missing/api/insertOfiicer.php", body: data);
    if (response.statusCode == 200) {
      setState(() {
        if (response.body.trim() == "true") {
          _showSnackBar(context, 'Registered Successfully');
        } else {
          _showSnackBar(context, 'Please Try Again');
        }
      });
    }
  }

  final GlobalKey<ScaffoldState> _globalKey = new GlobalKey<ScaffoldState>();

  void _showSnackBar(BuildContext context, String message) {
    final snackbar = SnackBar(
      content: Text('$message'),
      backgroundColor: Colors.white54,
    );
//    Scaffold.of(context).showSnackBar(snackbar);
    _globalKey.currentState.showSnackBar(snackbar);
  }
}
