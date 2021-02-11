
public class Players {

	String name;
	int x, y, room_x, room_y, score, steps;
	boolean isBot, isInGame;
	
	public Players(String name, int x, int y, int room_x, int room_y, boolean isBot) {
		
		this.name=name;
		this.x=x;
		this.y=y;
		this.room_x=room_x;
		this.room_y=room_y;
		this.isBot=isBot;
		this.isInGame=true;
		this.score=0;
		this.steps=0;
		
	}
	
}
