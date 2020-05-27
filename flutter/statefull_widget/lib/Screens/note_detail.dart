import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:statefull_widget/Model/note.dart';
import 'package:statefull_widget/utils/database_helper.dart';

class NoteDetails extends StatefulWidget {
  final String appBarTitle;
  Note note;

  NoteDetails(this.note, this.appBarTitle);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return NoteDetailsState(this.note, this.appBarTitle);
  }
}

class NoteDetailsState extends State<NoteDetails> {
  static var priority = ['High', 'Low'];

  final String appBarTitle;
  Note note;
  var _formKey = GlobalKey<FormState>();

  DatabaseHelper _databaseHelper = DatabaseHelper();

  TextEditingController titleController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();

  NoteDetailsState(this.note, this.appBarTitle);

  @override
  Widget build(BuildContext context) {
    TextStyle textStyle = Theme
        .of(context)
        .textTheme
        .subhead;

    titleController.text = note.title;
    descriptionController.text = note.Description;

    // TODO: implement build
    return WillPopScope(
        onWillPop: () {
          moveToLastScreen();
        },
        child: Scaffold(
            appBar: AppBar(
              title: Text(appBarTitle),
              leading: IconButton(
                  icon: Icon(Icons.arrow_back),
                  onPressed: () {
                    moveToLastScreen();
                  }),
            ),
            body: Form(
              key: _formKey,
              child: Padding(
                padding: EdgeInsets.only(top: 10.0, left: 5.0, right: 10.0),
                child: ListView(
                  children: <Widget>[
                    ListTile(
                      title: DropdownButton(
                          items: priority.map((String dropDownString) {
                            return DropdownMenuItem<String>(
                              value: dropDownString,
                              child: Text(dropDownString),
                            );
                          }).toList(),
                          style: textStyle,
                          value: getPriorityAsString(note.priority),
                          onChanged: (valueSelected) {
                            setState(() {
                              debugPrint('User Selected $valueSelected');
                              updatePriorityAsInt(valueSelected);
                            });
                          }),
                    ),
                    Padding(
                      padding: EdgeInsets.all(5.0),
                      child: TextFormField(
                        controller: titleController,
                        validator: (String value) {
                          if (value.isEmpty) {
                            return 'Please Enter Title';
                          }
                        },
                        style: textStyle,
                        onChanged: (value) {
                          debugPrint('Something Change in title');
                          updateTitle();
                        },
                        decoration: InputDecoration(
                            labelText: 'Title',
                            errorStyle: TextStyle(
                              color: Colors.red,
                            ),
                            labelStyle: textStyle,
                            border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5.0))),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.all(5.0),
                      child: TextFormField(
                        controller: descriptionController,
                        validator: (String value) {
                          if (value.isEmpty) {
                            return 'PLease Enter Description';
                          }
                        },
                        style: textStyle,
                        onChanged: (value) {
                          debugPrint('Something Change in description');
                          updateDescription();
                        },
                        decoration: InputDecoration(
                            labelText: 'Description',
                            labelStyle: textStyle,
                            errorStyle: TextStyle(color: Colors.red),
                            border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5.0))),
                      ),
                    ),
                    Padding(
                        padding: EdgeInsets.all(5.0),
                        child: Row(
                          children: <Widget>[
                            Expanded(
                              child: RaisedButton(
                                color: Theme
                                    .of(context)
                                    .primaryColorDark,
                                textColor: Theme
                                    .of(context)
                                    .primaryColorLight,
                                child: Text(
                                  'Save',
                                  textScaleFactor: 1.5,
                                ),
                                onPressed: () {
                                  setState(() {
                                    if (_formKey.currentState.validate()) {
                                      debugPrint('Save Button Clicked');
                                      _save();
                                    }
                                  });
                                },
                              ),
                            ),
                            Container(
                              width: 5.0,
                            ),
                            Expanded(
                              child: RaisedButton(
                                color: Theme
                                    .of(context)
                                    .primaryColorDark,
                                textColor: Theme
                                    .of(context)
                                    .primaryColorLight,
                                child: Text(
                                  'Delete',
                                  textScaleFactor: 1.5,
                                ),
                                onPressed: () {
                                  setState(() {
                                    debugPrint('Delete Button Clicked');
                                    _delete();
                                  });
                                },
                              ),
                            )
                          ],
                        ))
                  ],
                ),
              ),
            )));
  }

  void moveToLastScreen() {
    Navigator.pop(context, true);
  }

  //Convert int into string
  void updatePriorityAsInt(String value) {
    switch (value) {
      case 'High':
        note.priority = 1;
        break;
      case 'Low':
        note.priority = 2;
        break;
    }
  }

  String getPriorityAsString(int value) {
    String priorities;
    switch (value) {
      case 1:
        priorities = priority[0];
        break;
      case 2:
        priorities = priority[1];
        break;
    }
    return priorities;
  }

  void updateTitle() {
    note.title = titleController.text;
  }

  void updateDescription() {
    note.Description = descriptionController.text;
  }

  void _save() async {
    moveToLastScreen();
    note.Date = DateFormat.yMMMd().format(DateTime.now());
    int result;
    if (note.id != null) {
      result = await _databaseHelper.updateNote(note);
    } else {
      result = await _databaseHelper.insertNote(note);
    }

    if (result != 0) {
      _showAlertDialog('status', 'Save Successfully');
    } else {
      _showAlertDialog('status', 'Problem Saving Note');
    }
  }

  void _showAlertDialog(String s, String t) {
    AlertDialog alertDialog = AlertDialog(
      title: Text(s),
      content: Text(t),
    );
    showDialog(context: context, builder: (_) => alertDialog);
  }

  void _delete() async {
    moveToLastScreen();
    if (note.id == null) {
      _showAlertDialog('Status', 'No Note Was Deleted');
      return;
    }
    int result = await _databaseHelper.deleteNote(note.id);
    if (result != 0) {
      _showAlertDialog('Status', 'Note Deleted Successsfully');
    } else {
      _showAlertDialog('Status', 'Error Occured while Deleting Note');
    }
  }
}
