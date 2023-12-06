class Land{

  late List<List<Landpiece>> _matrix;

  Land(){
    initialize_matrix();
  }

  void initialize_matrix(){
      _matrix = List<List<Landpiece>>.generate(10, (i) => List<Landpiece>.generate(10, (index) => Landpiece(), growable: false), growable: false);
  }

  List<List<Landpiece>> getMatrix(){
    return _matrix;
  }

  void setPointTake(int x, int y){
    _matrix[y][x].setTake();
  }

  void setPointHit(int x, int y){
    _matrix[y][x].setHit();
  }

  bool getPointTake(int x, int y){
    return _matrix[y][x].getTake();
  }

  bool getPointHit(int x, int y){
    return _matrix[y][x].getHit();
  }

  String personalLand(){
    String mes = '';
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 9; j++){
        mes += (_matrix[i][j].personalString() + '-');
      }
      mes += (_matrix[i][9].personalString() + '\n');
    }
    return mes;
  }

  String toEnemyLand(){
    String mes = '';
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 9; j++){
        mes += (_matrix[i][j].toString() + '-');
      }
      mes += (_matrix[i][9].toString() + '\n');
    }
    return mes;
  }

}

class Landpiece {

  late bool _taken;
  late bool _hitted;

  Landpiece(){
    _hitted = false;
    _taken = false;
  }

  @override
  String toString(){
    if(_hitted == false){
      return ' ';
    }else if(_hitted && _taken){
      return 'X';
    }else{
      return 'O';
    }
  }

  String personalString(){
    if(_hitted){
      if (_taken){
        return 'X';
      }else{
        return 'O';
      }
    }else{
      if(_taken){
        return 'N';
      }else{
        return ' ';
      }
    }
  }

  void setHit(){
    _hitted = true;
  }

  void setTake(){
    _taken = true;
  }

  bool getTake(){
    return _taken;
  }

  bool getHit(){
    return _hitted;
  }

}