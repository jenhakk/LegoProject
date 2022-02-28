import lejos.hardware.Button;
import lejos.utility.Delay;

/**
 * Robot's main class and main thread
 *
 */
public class RoboRun {

	public static void main(String[] args) {

		DataTransfer DT = new DataTransfer();
		//LineFollower LFObj = new LineFollower(DT);
		RobotMoves RMObj = new RobotMoves(DT);
		ObstacleDetector ODObj = new ObstacleDetector(DT);
		//ObstacleClearance OCObj = new ObstacleClearance(DT, RMObj);
		
		System.out.println("Press Enter to start");
		Button.ENTER.waitForPress();

		Delay.msDelay(5000);

		//Thread Follow = new Thread(LFObj);
		Thread Moves = new Thread(RMObj);
		Thread Detect = new Thread(ODObj);
		//Thread Clear = new Thread(OCObj);

		//Detect.setPriority(9);
		//Follow.setPriority(10);

		//Follow.start();
		
		Moves.start();
		Detect.start();
		//Clear.start();

		while (Button.getButtons() != 0) {
			break;

		}

	}
	
	

}
