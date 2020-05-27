import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class horizontalList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 80.0,
      child: ListView(
        scrollDirection: Axis.horizontal,
        children: <Widget>[
          Category(
            image_location: 'images/cats/accessories.png',
            image_caption: 'Accessory',
          ),
          Category(
            image_location: 'images/cats/shoe.png',
            image_caption: 'shoe',
          ),
          Category(
            image_location: 'images/cats/dress.png',
            image_caption: 'dress',
          ),
          Category(
            image_location: 'images/cats/formal.png',
            image_caption: 'Formal',
          ),
          Category(
            image_location: 'images/cats/informal.png',
            image_caption: 'Informal',
          ),
          Category(
            image_location: 'images/cats/jeans.png',
            image_caption: 'jeans',
          ),
        ],
      ),
    );
  }
}

class Category extends StatelessWidget {
  final String image_location;
  final String image_caption;

  Category({this.image_location, this.image_caption});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(2.0),
      child: InkWell(
        onTap: () {},
        child: Container(
          width: 97,
          child: ListTile(
              title: Image.asset(
                image_location,
                width: 95.0,
                height: 50.0,
              ),
              subtitle: Container(
                alignment: Alignment.topCenter,
                child: Text(image_caption),
              )),
        ),
      ),
    );
  }
}
