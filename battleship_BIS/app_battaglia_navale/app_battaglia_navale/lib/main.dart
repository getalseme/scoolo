import 'dart:async';
import 'dart:io';

import 'package:app_battaglia_navale/land.dart';
import 'package:flutter/material.dart';

late ClientGame cg;

void main() {
  cg = ClientGame();
  runApp(MyApp());
}

class ClientGame{

  late Socket socket; 

  Land _landOp = Land();
  Land _land = Land();

  bool _ready = false;
  bool _playing = false;
  bool _isTurn = false;
  bool _finish = false;
  bool _win = false;

  String startMessage = '';

  List<String> serverResponce = [];

  ClientGame(){
    startConnection();
  }

    List<List<String>> getLand(){
      return _land.getMatrix();
    }

    List<List<String>> getLandOp(){
      return _landOp.getMatrix();
    }

    void writeToServer(String message){
      socket.write(message);
    }

    void startConnection(){
      Socket.connect('192.168.1.28', 3000).then((Socket sock) {
      this.socket = sock;
      socket.listen(dataHandler,
          onError: errorHandler, onDone: doneHandler, cancelOnError: false);
      }, onError: (e) {
        print("Unable to connect: $e");
        exit(1);
      });
    }

  void errorHandler(error, StackTrace trace) {
    print(error);
  }

  void doneHandler() {
    socket.destroy();
    exit(0);  
  } 
  
  void dataHandler(data) {
    String msgs = String.fromCharCodes(data).trim();
    List<String> msg = msgs.split('\t');
    //print(msg);
    int n = msg.length;
    for (int i = 0; i < n; i++){
      if(msg[i].contains('MAP OP')){
        _landOp.messageToGrid(msg[i]);
        continue;
      }
      if(msg[i].contains('MAP')){
        _land.messageToGrid(msg[i]);
        continue;
      }
      if(_ready){
        if(_playing){
          if(msg[i] == 'WIN'){
            print('CONGRATULATIONS, YOU WON THE GAME');
            continue;
          }
          if(msg[i] == 'LOSE'){
            print('WHAT A SHAME, YOU LOST THE GAME');
            continue;
          }
          if(msg[i] == 'FINISHED'){
            print('THE GAME IS FINISHED');
            _finish = true;
          }
          if(msg[i] == 'TURN'){
            print("IT'S YOUR TURN");
            _isTurn = true;
            continue;
          }
          if(msg[i] == 'W_TURN'){
            print("WAIT YOUR TURN");
            _isTurn = false;
            continue;
          }
          if(msg[i] == 'HIT'){
            print("YOU HITTED ONE OF YOUR ENEMY'S SHIPS");
            socket.write('SHOW OP');
            continue;
          }
          if(msg[i] == 'HITTED'){
            print('ONE OF YOUR SHIPS HAS BEEN HITTED');
            socket.write('SHOW ME');
            continue;
          }
          if(msg[i] == 'MISS'){
            print("YOU MISSED YOUR ENEMY'S SHIPS");
            socket.write('SHOW OP');
            continue;
          }
          if(msg[i] == 'MISSED'){
            print('YOUR SHIPS HAS BEEN MISSED');
            socket.write('SHOW ME');
            continue;
          }
        }
        if(msg[i] == 'W_OP'){
          print('WAIT FOR AN OPPONENT');
          continue;
        }
        if(msg[i] == 'F_OP'){
          print('FOUND AN OPPONENT');
          _playing = true;
          continue;
        }
      }else{
        if(msg[i] == 'READY'){
          print('SHIPS SUCCESFULLY PLACED');
          _ready = true;
          continue;
        }
        if(msg[i] == 'HI'){
          print(  'WELLCOME\n' + 
                  'YOU HAVE TO PLACE YOUR SHIPS\n' + 
                  'THEIR DIMENTIONS ARE: 5 - 4 - 3 - 3 - 2\n' + 
                  'YOU HAVE TO WRITE THE COORDINATES AND THE ORIETETION IN THIS WAY:\n' +
                  '"X Y DIM ORI/VER" FOR EXEMPLLE -> "1 8 5 ORI"');
          continue;
        }
        if(msg[i] == 'OK'){
          print('COMMAND ACCEPTED');
          ships.removeAt(selectedShip);
          socket.write('SHOW ME');
          continue;
        }
        if(msg[i] == 'NO'){
          print('COMMAND NOT ACCEPTED');
          //serverResponce.add(msg[i]);
          continue;
        }
      }
    }
  }
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Battleship Game',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: BattleshipGame(),
    );
  }
}

class BattleshipGame extends StatefulWidget {
  @override
  _BattleshipGameState createState() => _BattleshipGameState();
}

final List<int> ships = [2, 3, 3, 4, 5];
int selectedShip = 0;

class _BattleshipGameState extends State<BattleshipGame> {
  int currentGrid = 1; // Variable to track the current grid (1 or 2)

  late Timer _timer;
  
  List<List<String>> grid1 = cg.getLand();
  List<List<String>> grid2 = cg.getLandOp();

  List<List<String>> getCurrentGrid() {
    return currentGrid == 1 ? grid1 : grid2;
  }

  // Store the ships' sizes
  //final List<int> ships = [2, 3, 3, 4, 5];
  //int selectedShip = 0; // Index of the selected ship size
  String selectedOrientation = 'horizontal'; // Initial orientation

  void switchGrid() {
    setState(() {
      currentGrid = (currentGrid == 1) ? 2 : 1;
    });
  }

  void updateAllGrids(Timer timer) {
    setState(() {
      // Update both grid1 and grid2 with new data
      grid1 = cg.getLand();
      grid2 = cg.getLandOp();
    });
  }

  void cellSelected(int row, int col) {
    // Check if ship placement is valid here
    print('Cell selected: Row $row, Col $col');
    if(cg._playing && cg._isTurn){
      cg.writeToServer('$col $row');
    }
    // For demonstration purposes, let's update the selected cell with 'X'
    int ship = ships[selectedShip];

    if(selectedOrientation == 'vertical'){
      cg.writeToServer('$col $row $ship VER');  
    }else{
      cg.writeToServer('$col $row $ship ORI');    
    }
    if(cg.serverResponce[0] == 'OK'){
      setState(() {
        ships.removeAt(selectedShip);
        selectedShip = 0;
      });
      cg.serverResponce.removeAt(0);
    }else{
      cg.serverResponce.removeAt(0);
    }
  }

  void selectOrientation(String orientation) {
    setState(() {
      selectedOrientation = orientation;
    });
  }

  @override
  void initState() {
    super.initState();

    // Start the periodic timer when the widget is initialized
    _timer = Timer.periodic(Duration(milliseconds: 100), updateAllGrids);
  }

  @override
  void dispose() {
    // Dispose the timer when the widget is removed from the widget tree
    _timer.cancel();
    super.dispose();
  }

  void startPlayerTurnCheck() {
    // Controlla periodicamente lo stato del turno del giocatore
    Timer.periodic(Duration(seconds: 2), (Timer timer) {
      setState(() {
        // Utilizza la variabile _isTurn dalla classe ClientGame
        // Per esempio, se _isTurn è true, nascondi il popup
        if (cg._isTurn) {
          // Nascondi il popup se è il turno del giocatore
          // Aggiorna l'interfaccia in base a ciò che è necessario
          // ... codice per nascondere il popup o consentire l'interazione
        } else {
          // Mostra il popup se non è il turno del giocatore
          // ... codice per mostrare il popup fisso
        }
      });
    });
  }

  String getCurrentGridName(){
    if(getCurrentGrid() == grid1){
      return "MY FLEET";
    }
    else{
      return "ENEMY FLEET";
    }
  }

  Widget buildInitialSetup() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Text(
          'Select Ship Size:',
          style: TextStyle(fontSize: 20),
        ),
        SizedBox(height: 10),
        Wrap(
          spacing: 10,
          children: ships.map((shipSize) {
            return ElevatedButton(
              onPressed: () {
                setState(() {
                  selectedShip = ships.indexOf(shipSize);
                });
              },
              child: Text('$shipSize'),
              style: selectedShip == ships.indexOf(shipSize)
                  ? ElevatedButton.styleFrom(primary: Colors.green)
                  : null,
            );
          }).toList(),
        ),
        SizedBox(height: 20),
        Text(
          'Select Orientation:',
          style: TextStyle(fontSize: 20),
        ),
        SizedBox(height: 10),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                selectOrientation('horizontal');
              },
              child: Text('Horizontal'),
              style: selectedOrientation == 'horizontal'
                  ? ElevatedButton.styleFrom(primary: Colors.green)
                  : null,
            ),
            SizedBox(width: 10),
            ElevatedButton(
              onPressed: () {
                selectOrientation('vertical');
              },
              child: Text('Vertical'),
              style: selectedOrientation == 'vertical'
                  ? ElevatedButton.styleFrom(primary: Colors.green)
                  : null,
            ),
          ],
        ),
        SizedBox(height: 20),
        // Display grid for ship placement
        Container(
          width: 300,
          height: 300,
          decoration: BoxDecoration(
            border: Border.all(),
          ),
          child: GridView.builder(
            itemCount: 100,
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 10,
            ),
            itemBuilder: (context, index) {
              final row = index ~/ 10;
              final col = index % 10;
              final currentMatrix = getCurrentGrid();
              return GestureDetector(
                onTap: () {
                  cellSelected(row, col);
                },
                child: Container(
                  decoration: BoxDecoration(
                    border: Border.all(),
                  ),
                  child: Center(
                    child: Text(
                      currentMatrix[row][col],
                      style: TextStyle(fontSize: 20.0),
                    ),
                  ),
                ),
              );
            },
          ),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    if(!ships.isEmpty){
      return Scaffold(
        appBar: AppBar(
          title: Text('Battleship Game'),
        ),
        body: Center(
          child: buildInitialSetup(),
        ),
      );  
    }else{
      return Scaffold(
        appBar: AppBar(
          title: const Text('Battleship Game'),
        ),
        body: Stack(
          children: [
            // Widget principale (griglia di gioco)
            buildGameGrid(),
            // Popup fisso quando isTurn è false
            if (!cg._isTurn)
              Container(
                color: Colors.black54, // Sfondo scuro per il popup
                child: const Center(
                  child: Card(
                    elevation: 5,
                    child: Padding(
                      padding: EdgeInsets.all(16.0),
                      child: Text(
                        'ATTENDERE, turno dell\'avversario',
                        style: TextStyle(fontSize: 20),
                      ),
                    ),
                  ),
                ),
              ),
            if(!cg._playing)
              Container(
                color: Colors.black54, // Sfondo scuro per il popup
                child: const Center(
                  child: Card(
                    elevation: 5,
                    child: Padding(
                      padding: EdgeInsets.all(16.0),
                      child: Text(
                        'ATTENDERE, ricerca di un avversario in corso',
                        style: TextStyle(fontSize: 20),
                      ),
                    ),
                  ),
                ),
              ),
            if(cg._finish && cg._win)
                Container(
                  color: Colors.black54, // Sfondo scuro per il popup
                  child: const Center(
                    child: Card(
                      elevation: 5,
                      child: Padding(
                        padding: EdgeInsets.all(16.0),
                        child: Text(
                          'CONGRATULAZIONI HAI VINTO! :-D',
                          style: TextStyle(fontSize: 20),
                        ),
                      ),
                    ),
                  ),
                ),
            if(cg._finish && !cg._win)
                Container(
                  color: Colors.black54, // Sfondo scuro per il popup
                  child: const Center(
                    child: Card(
                      elevation: 5,
                      child: Padding(
                        padding: EdgeInsets.all(16.0),
                        child: Text(
                          "CAVOLETTI HAI PERSO! :'-(",
                          style: TextStyle(fontSize: 20),
                        ),
                      ),
                    ),
                  ),
                ),
          ],
        ),
      );
    }
  }

  Widget buildGameGrid(){
  return Column(
    mainAxisAlignment: MainAxisAlignment.center,
    children: [
      Text(
        getCurrentGridName(),
        style: TextStyle(
          fontWeight: FontWeight.bold,
          fontSize: 24.0,
        ),
      ),
      SizedBox(height: 10), // Spazio tra il nome e la griglia
      Expanded(
        child: GridView.builder(
          itemCount: 100, // 10x10 grid
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 10,
          ),
          itemBuilder: (context, index) {
            final row = index ~/ 10;
            final col = index % 10;
            final currentMatrix = getCurrentGrid();
            return GestureDetector(
              onTap: () {
                cellSelected(row, col);
              },
              child: Container(
                decoration: BoxDecoration(
                  border: Border.all(),
                ),
                child: Center(
                  child: Text(
                    currentMatrix[row][col],
                    style: TextStyle(fontSize: 20.0),
                  ),
                ),
              ),
            );
          },
        ),
      ),
      SizedBox(height: 20),
      Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(
            onPressed: () {
              switchGrid();
            },
            child: Text('Switch Grid'),
          ),
        ],
      ),
    ],
  );
}
}
