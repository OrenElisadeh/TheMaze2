import java.util.ArrayList;
import java.util.Scanner;

public class InterfaceMethod {

	Scanner im=new Scanner(System.in);

	public void welcome(MainGame mg1) {
		System.out.println("Welcome to: THE MAZE!\r\nPress ENTER to start");
		im.nextLine();
		System.out.println("Choose the number of rounds for this game (up to 5)");
		do {
			try {
				mg1.gameNumberOfRounds=Integer.parseInt(im.next());
				if(mg1.gameNumberOfRounds<1 || mg1.gameNumberOfRounds>5) {System.out.println("Please enter a number between 1 and 5!");}
			}
			catch(Exception e) {
				System.out.println("Are you testing me?\r\nPlease choose again...");
			}
		}while(mg1.gameNumberOfRounds<1 || mg1.gameNumberOfRounds>5);
	}
	
	public void printScore(ArrayList<Players> playersArray) {
		for(int player=0; player<3; player++) {
			System.out.println(playersArray.get(player).name+" your score is: "+playersArray.get(player).score);
		}
	}
	
	public void gameData(Players nowPlaying, int roundNumber) {
		//Printing round number
		System.out.println("Round: "+roundNumber);
		//Printing who's turn and number of steps
		System.out.println(nowPlaying.name+", it's your turn\r\nYou did "+nowPlaying.steps+" steps this round.");
	}
	
	public void movmentMenu(Players nowPlaying, Rooms[][] roomsArray) {
		System.out.println("What do toy want to do?");
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].leftWall=="door") {System.out.println("1. Move left");}
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].rightWall=="door") {System.out.println("2. Move right");}
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].upWall=="door") {System.out.println("3. Move up");}
		if(roomsArray[nowPlaying.room_y][nowPlaying.room_x].downWall=="door") {System.out.println("4. Move down");}
		System.out.println("5. Stay put");
		System.out.println("6. HELP-1: Give me the distance to the treasure!");
		System.out.println("7. HELP-2: Show me what inside the room!");
	}
}
