import 'package:flutter/material.dart';

class details extends StatefulWidget {
  String title = "";
  String fullname = "";
  String address = "";
  String photo = "";
  String gender = "";
  String birthdate = "";
  String parentMobile = "";
  String parentFullName = "";
  String dateMissing = "";

  details({this.title, this.fullname, this.address, this.photo, this.gender,
    this.birthdate, this.parentMobile, this.parentFullName, this.dateMissing});

  @override
  _detailsState createState() =>
      _detailsState(title: title,
          fullname: fullname,
          address: address,
          photo: photo,
          gender: gender,
          birthdate: birthdate,
          parentMobile: parentMobile,
          parentFullName: parentFullName,
          dateMissing: dateMissing);
}

class _detailsState extends State<details> {
  String title = "";
  String fullname = "";
  String address = "";
  String photo = "";
  String gender = "";
  String birthdate = "";
  String parentMobile = "";
  String parentFullName = "";
  String dateMissing = "";

  _detailsState({this.title, this.fullname, this.address, this.photo,
    this.gender, this.birthdate, this.parentMobile, this.parentFullName,
    this.dateMissing});

  nested() {
    return NestedScrollView(
      headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) {
        return <Widget>[
          SliverAppBar(
            expandedHeight: 200.0,
            floating: false,
            pinned: false,
            flexibleSpace: FlexibleSpaceBar(
              centerTitle: true,
              title: Text(fullname.toString(),
                style: TextStyle(
                    color: Colors.redAccent,
                    fontStyle: FontStyle.italic
                ),
              ),
              background: Image(
                fit: BoxFit.fill,
                image: NetworkImage(photo.toString()),
              ),
            ),
          )
        ];
      },
      body: getList(),
    );
  }
  Widget buttons(){
    return Padding(
      padding: EdgeInsets.only(left: 60),
        child:RaisedButton(
          child:Text("Update Complaint",
          style: TextStyle(
            color:Colors.white,
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
          ),
          color: Colors.deepPurple,
          onPressed: (){

          },
        ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: nested(),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          //navidateNoteDetail(Note('','',2),'Add More');
        },
        tooltip: 'delete More',
        child: Icon(Icons.delete),
      ),
    );
  }

  Widget getList() {
    return ListView.builder(
        itemCount: 1,
        itemBuilder: (context, index) {
          return getCard();
        });
  }

  Widget getCard() {
    return Card(
      child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: InkWell(
              onTap: () {},
              child: ListTile(
                title: Row(
                  children: <Widget>[
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Text("Address:" + " " +
                            address.toString(),
                          style: TextStyle(
                              fontSize: 17, fontWeight: FontWeight.bold),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text("Gender:" + " " +
                            gender.toString(),
                          style: TextStyle(
                            fontSize: 16,
                          ),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text("DOB:" + " " +
                            birthdate.toString(),
                          style: TextStyle(
                            fontSize: 16,
                          ),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text("Parent Name:" + " " +
                            parentFullName.toString(),
                          style: TextStyle(
                            fontSize: 16,
                          ),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text("Parent Contact:" + " " +
                            parentMobile.toString(),
                          style: TextStyle(
                            fontSize: 16,
                          ),
                        ),
                        SizedBox(
                          height: 10,
                        ),
                        Text("Date of Missing:" + " " +
                            dateMissing.toString(),
                          style: TextStyle(
                            fontSize: 16,
                          ),
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        buttons()
                      ],
                    ),
                  ],
                ),
              ))),
    );
  }
}
