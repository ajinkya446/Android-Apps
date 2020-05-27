import 'package:flutter/material.dart';

class Products extends StatefulWidget {
  @override
  _ProductsState createState() => _ProductsState();
}

class _ProductsState extends State<Products> {
  var product_list = [
    {
      "name": "Blazer",
      "picture": "images/products/blazer1.jpeg",
      "old_price": "1500",
      "price": "1200"
    },
    {
      "name": "Black Dress",
      "picture": "images/products/dress2.jpeg",
      "old_price": "900",
      "price": "700"
    },
    {
      "name": "Hills",
      "picture": "images/products/hills1.jpeg",
      "old_price": "430",
      "price": "400"
    },
    {
      "name": "Hills Adidas",
      "picture": "images/products/hills2.jpeg",
      "old_price": "520",
      "price": "300"
    },
    {
      "name": "Nike Pants",
      "picture": "images/products/pants1.jpg",
      "old_price": "690",
      "price": "600"
    },
    {
      "name": "Blazer Black",
      "picture": "images/products/blazer2.jpeg",
      "old_price": "1500",
      "price": "1200"
    },
    {
      "name": "Skirt",
      "picture": "images/products/skt2.jpeg",
      "old_price": "690",
      "price": "600"
    },
    {
      "name": "Blue Skirt",
      "picture": "images/products/skt1.jpeg",
      "old_price": "1500",
      "price": "1200"
    }
  ];

  @override
  Widget build(BuildContext context) {
    return GridView.builder(
        itemCount: product_list.length,
        gridDelegate:
            new SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 2),
        itemBuilder: (BuildContext context, int index) {
          return Single_prod(
            prod_name: product_list[index]['name'],
            prod_picture: product_list[index]['picture'],
            prod_oldprice: product_list[index]['old_price'],
            prod_price: product_list[index]['price'],
          );
        });
  }
}

class Single_prod extends StatelessWidget {
  final prod_name;
  final prod_picture;
  final prod_oldprice;
  final prod_price;

  Single_prod(
      {this.prod_name, this.prod_picture, this.prod_oldprice, this.prod_price});

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Hero(
        tag: prod_name,
        child: Material(
          child: InkWell(
            onTap: () {},
            child: GridTile(
              footer: Container(
                color: Colors.white70,
                child: ListTile(
                  leading: Text(
                    prod_name,
                    style: TextStyle(fontWeight: FontWeight.bold),
                    textAlign: TextAlign.center,
                  ),
                  title: Text("\$$prod_price",
                    style: TextStyle(
                        fontWeight: FontWeight.w800,
                      color: Colors.red
                    ),
                  ),
                  subtitle: Text(
                    "\$ $prod_oldprice",
                    style: TextStyle(
                        color: Colors.black54,
                      decoration: TextDecoration.lineThrough,
                      fontWeight: FontWeight.w800,
                    ),
                  ),
                ),
              ),
              child: Image.asset(prod_picture, fit: BoxFit.cover),
            ),
          ),
        ),
      ),
    );
  }
}
