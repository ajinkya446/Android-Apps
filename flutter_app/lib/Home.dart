import 'package:flutter/material.dart';

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
          alignment: Alignment.center,
          color: Colors.purpleAccent,
          padding:
              EdgeInsets.only(top: 15.0, bottom: 15.0, left: 15.0, right: 15.0),
          child: Column(
            children: <Widget>[
              Row(
                children: <Widget>[
                  Expanded(
                      child: Text(
                    "Flight",
                    textDirection: TextDirection.ltr,
                    style: TextStyle(
                      decoration: TextDecoration.none,
                      fontSize: 20,
                      fontStyle: FontStyle.italic,
                      fontFamily: 'Raleway',
                      fontWeight: FontWeight.w300,
                      color: Colors.white,
                    ),
                  )),
                  Expanded(
                      child: Text(
                    "Mumbai To Goa",
                    textDirection: TextDirection.ltr,
                    style: TextStyle(
                      decoration: TextDecoration.none,
                      fontSize: 20,
                      fontStyle: FontStyle.italic,
                      fontFamily: 'Raleway',
                      fontWeight: FontWeight.w300,
                      color: Colors.white,
                    ),
                  )),
                ],
              ),
              Row(
                children: <Widget>[
                  Expanded(
                      child: Text(
                    "Flight",
                    textDirection: TextDirection.ltr,
                    style: TextStyle(
                      decoration: TextDecoration.none,
                      fontSize: 20,
                      fontStyle: FontStyle.italic,
                      fontFamily: 'Raleway',
                      fontWeight: FontWeight.w300,
                      color: Colors.white,
                    ),
                  )),
                  Expanded(
                      child: Text(
                    "Rajasthan To Jammu Kasshmir",
                    textDirection: TextDirection.ltr,
                    style: TextStyle(
                      decoration: TextDecoration.none,
                      fontSize: 20,
                      fontStyle: FontStyle.italic,
                      fontFamily: 'Raleway',
                      fontWeight: FontWeight.w300,
                      color: Colors.white,
                    ),
                  )),
                ],
              ),
              FlightImage(),
              BookingButton()
            ],
          )),
    );
  }
}

class FlightImage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    AssetImage assetImage = AssetImage('images/abc.jpg');
    Image image = Image(image: assetImage, width: 250.0, height: 250.0);
    return Container(
      child: image,
    );
  }
}

class BookingButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
      margin: EdgeInsets.only(top: 30.0),
      width: 250.0,
      height: 50.0,
      child: RaisedButton(
          color: Colors.white,
          child: Text(
            "Book Ticket",
            style: TextStyle(
              fontSize: 20.0,
              color: Colors.lightBlue,
              fontFamily: 'Raleway',
            ),
          ),
          elevation: 6.0,
          onPressed: () => bookFlight(context)),
    );
  }

  void bookFlight(BuildContext context) {
    var alertDialog = AlertDialog(
      title: Text("Your Ticket Booked Successfully.."),
      content: Text("Have Good Journey"),
    );

    showDialog(
        context: context,
        builder: (BuildContext context) {
          return alertDialog;
        });
  }
}
