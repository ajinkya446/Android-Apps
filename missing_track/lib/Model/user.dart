class User {
  int _id;
  String _fullname;
  String _email;
  String _password;
  String _dob;

  User(this._id, this._fullname, this._email, this._password, this._dob);

  set id(int id) {}

  //data into the MAP Object
  Map<String, dynamic> toMap() {
    var map = <String, dynamic>{
      'id': _id,
      'fullname': _fullname,
      'email': _email,
      'password': _password,
      'dob': _dob,
    };
    return map;
  }

  int get id => _id;

  User.fromMap(Map<String, dynamic> map) {
    _id = map['id'];
    _fullname = map['fullname'];
    _email = map['email'];
    _password = map['password'];
    _dob = map['dob'];
  }

  String get fullname => _fullname;

  String get dob => _dob;

  String get password => _password;

  String get email => _email;
}
