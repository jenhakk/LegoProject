import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

/**
 * Robot's main class and main thread
 *
 */
public class RoboRun {

	public static void main(String[] args) {

		DataTransfer DT = new DataTransfer();
		RobotMoves RMObj = new RobotMoves(DT);
		ObstacleDetector ODObj = new ObstacleDetector(DT);
		SoundLib SD = new SoundLib(DT);
		
		System.out.println("Press Enter to start");
		Button.ENTER.waitForPress();

		Delay.msDelay(5000);

		Thread Moves = new Thread(RMObj);
		Thread Detect = new Thread(ODObj);
		Thread Sounds = new Thread(SD);

		Sounds.start();
		Moves.start();
		Detect.start();
		

//		while (Button.getButtons() != 0) {
//			break;
//
//		}

	}

}
