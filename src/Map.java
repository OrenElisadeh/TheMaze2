import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Map {

	Scanner im=new Scanner(System.in);
	Random rand=new Random();
	
	public String[][] createMaze(String[][] map, Rooms[][] roomsArray, MainGame mg1) {
		
		for(int mapRow=0; mapRow<MainGame.mazeCharsSize; mapRow++) {
			for(int mapColumn=0; mapColumn<MainGame.mazeCharsSize; mapColumn++) {
				map[mapRow][mapColumn]=".";
			}
		}
			
		for(int roomsRow=0; roomsRow<roomsArray.length; roomsRow++) {
			for(int roomsColumn=0; roomsColumn<roomsArray.length; roomsColumn++) {
				
				if(!roomsArray[roomsRow][roomsColumn].isEmpty) {
					for(int mapRow=roomsRow*7; mapRow<(roomsRow*7)+7; mapRow++) {
						for(int mapColumn=roomsColumn*7; mapColumn<(roomsColumn*7)+7; mapColumn++) {
							if(mapRow==roomsRow*7) {
								map[mapRow][mapColumn]="#";
								if(roomsArray[roomsRow][roomsColumn].upWall=="door") {
									map[mapRow][(roomsColumn*7)+2]="~";
									map[mapRow][(roomsColumn*7)+3]="~";
									map[mapRow][(roomsColumn*7)+4]="~";
								}
							}
							else if(mapRow==(roomsRow*7)+6) {
								map[mapRow][mapColumn]="#";
								if(roomsArray[roomsRow][roomsColumn].downWall=="door") {
									map[mapRow][(roomsColumn*7)+2]="~";
									map[mapRow][(roomsColumn*7)+3]="~";
									map[mapRow][(roomsColumn*7)+4]="~";
								}
							}
							else if (mapColumn==roomsColumn*7) {
								map[mapRow][mapColumn]="#";
								if(roomsArray[roomsRow][roomsColumn].leftWall=="door") {
									map[(roomsRow*7)+2][mapColumn]="~";
									map[(roomsRow*7)+3][mapColumn]="~";
									map[(roomsRow*7)+4][mapColumn]="~";
								}
							}
							else if(mapColumn==(roomsColumn*7)+6) {
								map[mapRow][mapColumn]="#";
								if(roomsArray[roomsRow][roomsColumn].rightWall=="door") {
									map[(roomsRow*7)+2][mapColumn]="~";
									map[(roomsRow*7)+3][mapColumn]="~";
									map[(roomsRow*7)+4][mapColumn]="~";
								}
							}
							else {
								map[mapRow][mapColumn]=" ";
							}
						}
					}
				}
			}
		}
	return map;
	}
	
	public void printMaze(String[][] map) {
		for(int rows=0; rows<map.length; rows++) {
			for(int columns=0; columns<map.length; columns++) {
				System.out.print(map[rows][columns]);
			}
			System.out.println();
		}
	}
	
	public void prepareMap(ArrayList<Players> playersArray, Treasures t1, Rooms[][] roomsArray, String[][] map, Map map1, MainGame mg1) {
		map=map1.createMaze(map, roomsArray, mg1);
		map[playersArray.get(0).x][playersArray.get(0).y]=playersArray.get(0).name;
		map[playersArray.get(1).x][playersArray.get(1).y]=playersArray.get(1).name;
		map[playersArray.get(2).x][playersArray.get(2).y]=playersArray.get(2).name;
		if(Integer.parseInt(t1.score)<10) {map[t1.x][t1.y]=t1.score;}
		else {
			map[t1.x][t1.y]=String.valueOf(t1.score.charAt(0));
			map[t1.x][t1.y+1]=String.valueOf(t1.score.charAt(1));
		}
	}
	
	public void movmentChoiceTry(Players nowPlaying, String[][] map, int add_x, int add_y, boolean isRoom_x, String direction, int isPlus) {
		map[nowPlaying.x+add_x][nowPlaying.y+add_y]=nowPlaying.name;
		map[nowPlaying.x][nowPlaying.y]=" ";
		if(isRoom_x) {
			nowPlaying.x+=7*isPlus;
			nowPlaying.room_y+=1*isPlus;
		}
		else {
			nowPlaying.y+=7*isPlus;
			nowPlaying.room_x+=1*isPlus;
		}
		System.out.println(nowPlaying.name+" moved "+direction);
		nowPlaying.steps+=1;
	}
	
	public void movmentChoiceCatch(Players nowPlaying, String[][] map, String direction) {
		System.out.println(nowPlaying.name+" moved "+direction);
		System.out.println("I'm sorry "+nowPlaying.name+", but you got out of the maze, please wait for next round bitch ^_^");
		map[nowPlaying.x][nowPlaying.y]=" ";
		nowPlaying.steps+=1;
		nowPlaying.isInGame=false;
	}
	
	public void movmentChoice(MainGame mg1, Players nowPlaying, Rooms[][] roomsArray, Treasures t1, Map map1, String[][] map, boolean isBot, ArrayList<Players> playersArray) {
		ArrayList<Integer> isDoor=new ArrayList<Integer>();
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].leftWall=="door") {isDoor.add(1);}
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].rightWall=="door") {isDoor.add(2);}
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].upWall=="door") {isDoor.add(3);}
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].downWall=="door") {isDoor.add(4);}
		isDoor.add(5); isDoor.add(6); isDoor.add(7);
		//System.out.println(isDoor);
		int playerChoice=0;
		if(! isBot) {
			do{
				try {
					playerChoice=Integer.parseInt(im.nextLine());
				}
				catch(Exception e) {
					//Next line will give the massage for the player that he cannot enter the f:):) he want...
				}
				if(! isDoor.contains(playerChoice)) {System.out.println("Sorry, you can't do that. Please choose another option.");}
			}while(! isDoor.contains(playerChoice));}
		else {
			do{playerChoice=rand.nextInt(4);}while(! isDoor.contains(playerChoice));
		}
		
		switch(playerChoice) {
		case 1:
			try {
				map1.movmentChoiceTry(nowPlaying, map, 0, -7, false, "left", -1);
				break;
			}
			catch(IndexOutOfBoundsException eds) {
				map1.movmentChoiceCatch(nowPlaying, map, "left");
				break;
			}
		case 2:
			try {
				map1.movmentChoiceTry(nowPlaying, map, 0, 7, false, "right", 1);
				break;
			}
			catch(IndexOutOfBoundsException eds) {
				map1.movmentChoiceCatch(nowPlaying, map, "right");
				break;
			}
		case 3:
			try {
				map1.movmentChoiceTry(nowPlaying, map, -7, 0, true, "up", -1);
				break;
			}
			catch(IndexOutOfBoundsException eds) {
				map1.movmentChoiceCatch(nowPlaying, map, "up");
				break;
			}
		case 4:
			try {
				map1.movmentChoiceTry(nowPlaying, map, 7, 0, true, "down", 1);
				break;
			}
			catch(IndexOutOfBoundsException eds) {
				map1.movmentChoiceCatch(nowPlaying, map, "down");
				break;
			}
		case 5:
			System.out.println(nowPlaying.name+" stayed put.");
			break;
		case 6:
			//Distance to treasure
			if(Math.abs(t1.room_x-nowPlaying.room_x)+Math.abs(t1.room_y-nowPlaying.room_y)==1) {System.out.println("Your distance from the treasure is only "+(Math.abs(t1.room_x-nowPlaying.room_x)+Math.abs(t1.room_y-nowPlaying.room_y))+" room!!!");}
			else {System.out.println("Your distance from the treasure is "+(Math.abs(t1.room_x-nowPlaying.room_x)+Math.abs(t1.room_y-nowPlaying.room_y))+" rooms!");}
			nowPlaying.steps+=1;
			im.nextLine();
			break;
		case 7:
			System.out.println(nowPlaying.name+", please choose a room from the menu:");
			//Prints the possibles rooms the player has.
			ArrayList<Integer> showRoomChoices=new ArrayList<Integer>(); 
			try {
				if(roomsArray[nowPlaying.room_y][nowPlaying.room_x+1]!=null) {
					System.out.println("Right room - press 1");
					showRoomChoices.add(1);
				}
			}
			catch(IndexOutOfBoundsException eds) {}
			try {
				if(roomsArray[nowPlaying.room_y][nowPlaying.room_x-1]!=null) {
					System.out.println("Left room - press 2");
					showRoomChoices.add(2);
				}
			}
			catch(IndexOutOfBoundsException eds) {}
			try {
				if(roomsArray[nowPlaying.room_y+1][nowPlaying.room_x]!=null) {
					System.out.println("Down room - press 3");
					showRoomChoices.add(3);
				}
			}
			catch(IndexOutOfBoundsException eds) {}
			try {
				if(roomsArray[nowPlaying.room_y-1][nowPlaying.room_x]!=null) {
					System.out.println("Up room - press 4");
					showRoomChoices.add(4);
				}
			}
			catch(IndexOutOfBoundsException eds) {}
			
			int playerRoomChoice=0;
			do{
				try {
					if(isBot) {playerRoomChoice=1+rand.nextInt(4-1+1);}
					else {playerRoomChoice=Integer.parseInt(im.nextLine());}
				}
				catch(Exception e) {
					System.out.println("What the hellll are you typing???");
				}
				if(playerRoomChoice<1 || playerRoomChoice>4 || ! showRoomChoices.contains(playerRoomChoice)) {System.out.println("Sorry, you can't do that. Please choose another option.");}
			}while(playerRoomChoice<1 || playerRoomChoice>4 || ! showRoomChoices.contains(playerRoomChoice));
			nowPlaying.steps+=1;
			
			//Show the player the result
			switch(playerRoomChoice) {
			case 1:
				if((nowPlaying.room_x+1==t1.room_x) && (nowPlaying.room_y==t1.room_y)) {System.out.println("At the right room you have "+t1.score+" points treasure!!!");}
				else if(mg1.roomsArray[nowPlaying.room_x+1][nowPlaying.room_y].isEmpty){System.out.println("You have no room at your right!");}
				else {System.out.println("On the right room you have NOTHING!");}
				im.nextLine();
				break;
			case 2:
				if((nowPlaying.room_x-1==t1.room_x) && (nowPlaying.room_y==t1.room_y)) {System.out.println("At the left room you have "+t1.score+" points treasure!!!");}
				else if(mg1.roomsArray[nowPlaying.room_x-1][nowPlaying.room_y].isEmpty){System.out.println("You have no room at your left!");}
				else {System.out.println("On the left room you have NOTHING!");}
				im.nextLine();
				break;
			case 3:
				if((nowPlaying.room_x==t1.room_x) && (nowPlaying.room_y+1==t1.room_y)) {System.out.println("At the up room you have "+t1.score+" points treasure!!!");}
				else if(mg1.roomsArray[nowPlaying.room_x][nowPlaying.room_y+1].isEmpty){System.out.println("You have no room at your down!");}
				else {System.out.println("On the down room you have NOTHING!");}
				im.nextLine();
				break;
			case 4:
				if((nowPlaying.room_x==t1.room_x) && (nowPlaying.room_y-1==t1.room_y)) {System.out.println("At the down room you have "+t1.score+" points treasure!!!");}
				else if(mg1.roomsArray[nowPlaying.room_x][nowPlaying.room_y-1].isEmpty){System.out.println("You have no room at your up!");}
				else {System.out.println("On the up room you have NOTHING!");}
				im.nextLine();
				break;
			}
			break;
		}
	}
}
