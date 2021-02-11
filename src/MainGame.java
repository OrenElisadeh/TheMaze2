import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainGame {
	
	public final static int mazeRoomSize=5;
	public final static int mazeCharsSize=mazeRoomSize*7;
	private String[][] map=new String[mazeCharsSize][mazeCharsSize];
	public Rooms [][] roomsArray=new Rooms[mazeRoomSize][mazeRoomSize];
	
	private boolean inRound=true;
	private int roundNumber=1;
	public int gameNumberOfRounds=0;
	private int winnerScore=0;
	private String theWinner="";

	static Random rand=new Random();
	
	public MainGame() {
		
		this.roomsArray [0][0]=new Rooms("wall", "door", "wall", "door", true, true);
		this.roomsArray [0][1]=new Rooms("wall", "door", "wall", "door", true, false);
		this.roomsArray [0][2]=new Rooms("door", "door", "door", "door", true, false);
		this.roomsArray [0][3]=new Rooms("door", "wall", "wall", "door", true, false);
		this.roomsArray [0][4]=new Rooms("wall", "door", "wall", "door", true, true);
		this.roomsArray [1][0]=new Rooms("door", "door", "wall", "wall", true, false);
		this.roomsArray [1][1]=new Rooms("door", "wall", "door", "door", false, false);
		this.roomsArray [1][2]=new Rooms("wall", "door", "door", "wall", false, false);
		this.roomsArray [1][3]=new Rooms("door", "wall", "door", "door", false, false);
		this.roomsArray [1][4]=new Rooms("wall", "wall", "wall", "door", true, false);
		this.roomsArray [2][0]=new Rooms("wall", "door", "wall", "door", true, false);
		this.roomsArray [2][1]=new Rooms("door", "wall", "door", "wall", false, false);
		this.roomsArray [2][2]=new Rooms("door", "wall", "door", "door", false, true);
		this.roomsArray [2][3]=new Rooms("wall", "door", "door", "wall", false, false);
		this.roomsArray [2][4]=new Rooms("door", "wall", "door", "door", true, false);
		this.roomsArray [3][0]=new Rooms("wall", "door", "door", "wall", true, false);
		this.roomsArray [3][1]=new Rooms("door", "wall", "wall", "door", false, false);
		this.roomsArray [3][2]=new Rooms("wall", "door", "wall", "door", false, false);
		this.roomsArray [3][3]=new Rooms("door", "door", "wall", "wall", false, false);
		this.roomsArray [3][4]=new Rooms("door", "door", "door", "wall", true, false);
		this.roomsArray [4][0]=new Rooms("door", "door", "wall", "wall", true, true);
		this.roomsArray [4][1]=new Rooms("wall", "door", "door", "wall", true, false);
		this.roomsArray [4][2]=new Rooms("door", "door", "door", "door", true, false);
		this.roomsArray [4][3]=new Rooms("door", "wall", "wall", "wall", true, false);
		this.roomsArray [4][4]=new Rooms("wall", "door", "wall", "door", true, true);
		
	}
	
	public boolean checkIfWin(Players player, ArrayList<Players> playersArray, Treasures t1, MainGame mg1, Map map1, Scanner im) {
		if(player.room_x==t1.room_x && player.room_y==t1.room_y) {
			if(Integer.parseInt(t1.score)-player.steps>0) {System.out.println("Congratulations "+player.name+"!\r\nYou are the winner of round "+mg1.roundNumber+"!\r\nYou earned "+(Integer.parseInt(t1.score)-player.steps)+" points!");}
			else if(Integer.parseInt(t1.score)-player.steps<0) {System.out.println("Congratulations "+player.name+"!\r\nYou are the winner of round "+mg1.roundNumber+"!\r\nYou lost "+(Integer.parseInt(t1.score)-player.steps)*-1+" points because you made to much steps!");}
			else {System.out.println("Congratulations "+player.name+"!\r\nYou are the winner of round "+mg1.roundNumber+"!\r\nYou haven't won or lost any points beacause number of steps equal to treasure price!");}
			//Adding or dropping scores to all players
			String nameOfWinner=player.name;
			
			for(Players eachPlayer : playersArray) {
				if(eachPlayer.name!=nameOfWinner) {
					eachPlayer.score-=eachPlayer.steps;
				}
			}
			
			player.score+=Integer.parseInt(t1.score)-player.steps;
			
			map1.printMaze(map);
			im.nextLine();
			mg1.inRound=false;
			return true;
		}
		else {return false;}
	}
		
	public void botsSteps(Map map1, MainGame mg1, Treasures t1,Players player, Scanner im, ArrayList<Players> playersArray) {
		map1.movmentChoice(mg1, player, roomsArray, t1, map1, map, true, playersArray);
		player.steps+=1;
		mg1.checkIfWin(player, playersArray, t1, mg1, map1, im);
		map1.printMaze(map);
	}
	
	public static void main(String[] args) {

		//Creating objects for game
		MainGame mg1=new MainGame();
		InterfaceMethod im=new InterfaceMethod();
		Map map1=new Map();
		ArrayList<Players> playersArray=new ArrayList<Players>();
		ArrayList<Integer> scoreSummary=new ArrayList<Integer>();
		//Creating players objects 
		Players p1=new Players("A", 2, 1, 0, 0, false); Players p2=new Players("B", 2, 3, 0, 0, true); Players p3=new Players("C", 2, 5, 0, 0, true);
		playersArray.add(p1); playersArray.add(p2); playersArray.add(p3); 
		
		//Creating treasures object
		Treasures t1=new Treasures(5, 3, 0, 0, "0");
		
		//First interface + choose number of rounds
		im.welcome(mg1);		
		
		//Run the game in for loop depends on gameNumberOfRounds
		for(int round=0; round<mg1.gameNumberOfRounds; round++) {
			//Random score for the treasure
			t1.score=String.valueOf(1+rand.nextInt(20-1+1));
			
			//Place the treasure in the map randomly
			int t1_random_x=0;
			int t1_random_y=0;
			do {
				t1_random_x=rand.nextInt(MainGame.mazeRoomSize-1);
				t1_random_y=rand.nextInt(MainGame.mazeRoomSize-1);
				if((mg1.roomsArray[t1_random_x][t1_random_y].isSideRoom) && (!mg1.roomsArray[t1_random_x][t1_random_y].isEmpty)) {
					t1.room_y=t1_random_y;
					t1.room_x=t1_random_x;
					t1.x+=7*t1_random_y;
					t1.y+=7*t1_random_x;
				}
			}while((mg1.roomsArray[t1_random_x][t1_random_y].isEmpty) || (!mg1.roomsArray[t1_random_x][t1_random_y].isSideRoom));

			//Place the players in the map randomly
			for(Players player : playersArray) {
				int random_x=0;
				int random_y=0;
				do {
					random_x=rand.nextInt(MainGame.mazeRoomSize-1);
					random_y=rand.nextInt(MainGame.mazeRoomSize-1);
					if((mg1.roomsArray[random_x][random_y].isSideRoom==true) && (mg1.roomsArray[random_x][random_y].isEmpty==false) && (t1.room_x!=random_x || t1.room_y!=random_y)) {
						player.room_y=random_y;
						player.room_x=random_x;
						player.x+=7*random_y;
						player.y+=7*random_x;
					}
				}while((mg1.roomsArray[random_x][random_y].isSideRoom==false) || (mg1.roomsArray[random_x][random_y].isEmpty==true) || (t1.room_x==random_x && t1.room_y==random_y));
				random_x=0;
				random_y=0;
			}
			
			//Preparing the map
			map1.prepareMap(playersArray, t1, mg1.roomsArray, mg1.map, map1, mg1);
			
			//Start the game
			while(mg1.inRound) {
				for(Players nowPlaying : playersArray) {
					if(nowPlaying.isInGame) {	
						im.gameData(nowPlaying, mg1.roundNumber);
						im.movmentMenu(nowPlaying, mg1.roomsArray);
						map1.printMaze(mg1.map);
						map1.movmentChoice(mg1, nowPlaying, mg1.roomsArray, t1, map1, mg1.map, nowPlaying.isBot, playersArray);
						if(mg1.checkIfWin(nowPlaying, playersArray, t1, mg1, map1, im.im)) {break;}
						if(playersArray.get(0).isInGame==false && playersArray.get(0).isInGame==false && playersArray.get(0).isInGame==false) {
							System.out.println("All the players got off the maze, the round is over and we're going for next round!");
							mg1.inRound=false;
							break;
						}
					}
				}
			}
			
			//Next Round
			mg1.roundNumber+=1;
			t1.x=5;
			t1.y=3;
			for(Players player : playersArray) {
				player.steps=0;
				player.isInGame=true;
				if(player.name=="A") {player.x=2; player.y=1;} else if(player.name=="B") {player.x=2; player.y=3;} else {player.x=2; player.y=5;}
			}
			im.printScore(playersArray);
			mg1.inRound=true;
			
			im.im.nextLine();
		}
		
		//Announce the winner of the game
		mg1.winnerScore=playersArray.get(0).score-1;
		for(int playerIndex=0; playerIndex<playersArray.size(); playerIndex++) {
			if(playersArray.get(playerIndex).score>mg1.winnerScore) {
				mg1.winnerScore=playersArray.get(playerIndex).score;
				mg1.theWinner=playersArray.get(playerIndex).name;
			}
		}
		mg1.winnerScore=0;
		
		for(Players player : playersArray) {scoreSummary.add(player.score);}
		scoreSummary.sort(null);
		if(scoreSummary.get(1)==scoreSummary.get(2)) {System.out.println("No one won this game... it's a draw!");}
		else {System.out.println("The winner is "+mg1.theWinner+"!!!");}
	}
}

//make the treasure location randomly and who didn't win the round loose score depend on number of steps