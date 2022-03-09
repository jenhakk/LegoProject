import java.io.File;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;

/**
 * Date: Mar 08-2022 This is an application for Lego Mindstorms EV3 robot that
 * follows line by reading light values using color sensor and detects obstacles
 * using ultrasonic sensor. It has been built using two extra threads and a
 * class that transfers data between threads. It plays .wav file in the
 * beginning of the app.
 * 
 * @author jenna hakkarainen, amanda karjalainen, anna-maria palm
 * @version 1.0
 *
 */
public class RoboRun {

	/**
	 * Main method of this application
	 * 
	 * @param args array of String arguments
	 */

	public static void main(String[] args) {

		DataTransfer DT = new DataTransfer();
		RobotMoves RMObj = new RobotMoves(DT);
		ObstacleDetector ODObj = new ObstacleDetector(DT);

		File beginning = new File("Beginning.wav");

		// Wait Enter to be pushed and delay 5 seconds
		System.out.println("Press Enter to start");
		Button.ENTER.waitForPress();
		Delay.msDelay(5000);

		// Create and start threads
		Thread Moves = new Thread(RMObj);
		Thread Detect = new Thread(ODObj);

		Moves.start();
		Detect.start();

		//Play sound in the beginning of the program
		Sound.playSample(beginning);

		while (Button.getButtons() != 0) {
			break;

		}

	}

}
