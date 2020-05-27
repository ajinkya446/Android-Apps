import 'dart:async';
import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:statefull_widget/Model/note.dart';
import 'package:statefull_widget/Screens/note_detail.dart';
import 'package:statefull_widget/utils/database_helper.dart';

class NoteList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return NoteListState();
  }
}

class NoteListState extends State<NoteList> {
  int count = 0;
  DatabaseHelper _databaseHelper = DatabaseHelper();
  List<Note> notelist;

  @override
  Widget build(BuildContext context) {
    if (notelist == null) {
      notelist = List<Note>();
      updateListView();
    }

    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text("Notes"),
      ),
      body: getNotesList(),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          debugPrint('Fab Clicked');
          navidateNoteDetail(Note('','',2),'Add More');
        },
        tooltip: 'Add More',
        child: Icon(Icons.add),
      ),
    );
  }

  ListView getNotesList() {
    TextStyle textStyle = Theme.of(context).textTheme.subhead;
    return ListView.builder(
      itemCount: count,
      itemBuilder: (BuildContext context, int position) {
        return Card(
            elevation: 2.0,
            child: ListTile(
              leading: CircleAvatar(
                backgroundColor:
                    getPriorityColor(this.notelist[position].priority),
                child: getPriorityIcon(this.notelist[position].priority),
              ),
              title: Text(this.notelist[position].title, style: textStyle),
              subtitle: Text(this.notelist[position].Date),
              trailing: GestureDetector(
                child: Icon(
                  Icons.delete,
                  color: Colors.grey,
                ),
                onTap: () {
                  _delete(context, notelist[position]);
                },
              ),
              onTap: () {
                debugPrint('ListTile Tapped');
                navidateNoteDetail(this.notelist[position],'Edit Note');
              },
            ));
      },
    );
  }

  //return priority color
  Color getPriorityColor(int priority) {
    switch (priority) {
      case 1:
        return Colors.red;
        break;
      case 2:
        return Colors.yellow;
        break;
      default:
        return Colors.yellow;
    }
  }

  //returning priority for icon
  Icon getPriorityIcon(int priority) {
    switch (priority) {
      case 1:
        return Icon(Icons.play_arrow);
        break;
      case 2:
        return Icon(Icons.keyboard_arrow_right);
        break;
      default:
        return Icon(Icons.keyboard_arrow_right);
    }
  }

  void _delete(BuildContext context, Note note) async {
    int result = await _databaseHelper.deleteNote(note.id);
    if (result != 0) {
      _showSnackBar(context, 'Note Deleted Successfully');
      //todo list
      updateListView();
    }
  }

  void _showSnackBar(BuildContext context, String message) {
    final snackbar = SnackBar(content: Text(message));
    Scaffold.of(context).showSnackBar(snackbar);
  }

  void navidateNoteDetail(Note note,String title) async{
    bool result= await Navigator.push(context, MaterialPageRoute(builder: (context) {
      return NoteDetails(note,title);
    }));
    if(result==true){
      updateListView();
    }
  }

  void updateListView() {
    final Future<Database> dbFuture = _databaseHelper.initaliseDatabase();
    dbFuture.then((database) {
      Future<List<Note>> noteListFuture = _databaseHelper.getNoteList();
      noteListFuture.then((notelist) {
        setState(() {
          this.notelist = notelist;
          this.count = notelist.length;
        });
      });
    });
  }
}
