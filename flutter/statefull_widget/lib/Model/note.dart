class Note {
  int _id;
  String _title;
  String _Description;
  String _Date;
  int _priority;

  Note(this._title, this._Date, this._priority, [this._Description]);

  Note.withId(this._id, this._title, this._Date, this._priority,
      [this._Description]);

  int get priority => _priority;

  String get Date => _Date;

  String get Description => _Description;

  String get title => _title;

  int get id => _id;

  set priority(int value) {
    if (value >= 1 && value <= 2) {
      _priority = value;
    }
  }

  set Date(String value) {
    _Date = value;
  }

  set Description(String value) {
    if (value.length <= 255) {
      _Description = value;
    }
  }

  set title(String value) {
    if (value.length <= 255) {
      _title = value;
    }
  }

//data into the MAP Object
  Map<String, dynamic> toMap() {
    var map = Map<String, dynamic>();

    if (id != null) {
      map['id'] = _id;
    }
    map['title'] = _title;
    map['description'] = _Description;
    map['priority'] = _priority;
    map['date'] = _Date;

    return map;
  }

  //Extracting MapObject into map
  Note.fromMapObject(Map<String, dynamic> map) {
    this._id = map['id'];
    this._title = map['title'];
    this._Description = map['description'];
    this._priority = map['priority'];
    this._Date = map['date'];
  }
}
