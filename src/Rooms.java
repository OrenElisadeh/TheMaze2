
public class Rooms {

	String leftWall, rightWall, upWall, downWall="";
	boolean isSideRoom, isEmpty;
	
	public Rooms(String leftWall, String rightWall, String upWall, String downWall, boolean isSideRoom, boolean isEmpty) {
		
		this.leftWall=leftWall;
		this.rightWall=rightWall;
		this.upWall=upWall;
		this.downWall=downWall;
		this.isSideRoom=isSideRoom;
		this.isEmpty=isEmpty;
		
	}
	
}
