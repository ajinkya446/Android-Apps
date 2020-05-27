import 'dart:async';
import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';
import 'package:statefull_widget/Model/note.dart';

class DatabaseHelper {
  static DatabaseHelper _databaseHelper; //Declared only singleton object
  static Database _database;
  String noteTable = 'note_table';
  String colId = 'id';
  String ColTitle = 'title';
  String ColDescription = 'description';
  String ColPriority = 'priority';
  String ColDate = 'date';

  //it is named constructor for create instance of database helper
  DatabaseHelper._createInstance();

  factory DatabaseHelper() {
    if (_databaseHelper == null) {
      _databaseHelper = DatabaseHelper
          ._createInstance(); //this execute only once cos singleton object
    }
    return _databaseHelper;
  }

  Future<Database> get database async {
    if (_database == null) {
      _database = await initaliseDatabase();
    }
    return _database;
  }

  Future<Database> initaliseDatabase() async {
    //get the directory path for both android and ios db
    Directory directory = await getApplicationDocumentsDirectory();
    String path = directory.path + 'Notes.db';

    //Open/create the database at given  path
    var NoteDatabase =
        await openDatabase(path, version: 1, onCreate: _createDb);
    return NoteDatabase;
  }

  void _createDb(Database db, int newVersion) async {
    await db.execute(
        'CREATE TABLE $noteTable($colId INTEGER PRIMARY KEY AUTOINCREMENT,'
            '$ColTitle TEXT,$ColDescription TEXT,$ColPriority INTEGER,$ColDate TEXT)');
  }

  //Fetch operation: get all noteObject from the db
  Future<List<Map<String, dynamic>>> getNoteMapList() async {
    Database db = await this.database;
    //var result=await db.rawQuery('SELECT * FROM $noteTable order by $ColPriority ASC');
    var result = await db.query(noteTable, orderBy: '$ColPriority ASC');
    return result;
  }

  //perform insert operation
  Future<int> insertNote(Note note) async {
    Database db = await this.database;
    var result = await db.insert(noteTable, note.toMap());
    return result;
  }

  //update operation..
  Future<int> updateNote(Note note) async {
    Database db = await this.database;
    var result = await db.update(noteTable, note.toMap(),
        where: '$colId=?', whereArgs: [note.id]);
    return result;
  }

  //Delete opearation.
  Future<int> deleteNote(int id) async {
    Database db = await this.database;
    var result = await db.rawDelete('DELETE FROM $noteTable WHERE $colId=$id');
    return result;
  }

  //get number of record present in table
  Future<int> getCount() async {
    Database db = await this.database;
    List<Map<String, dynamic>> x =
        await db.rawQuery('SELECT COUNT(*) from $noteTable');
    int result = Sqflite.firstIntValue(x);
    return result;
  }

  //get Map List{List<Map>} and convert it to Note List{List<Note>}
  Future<List<Note>> getNoteList() async{
    var noteMapList=await getNoteMapList();
    int count=noteMapList.length;

    List<Note> noteList=List<Note>();
    for(int i=0;i<count;i++){
      noteList.add(Note.fromMapObject(noteMapList[i]));
    }
    return noteList;
  }
}
