import java.io.File;

import lejos.hardware.Sound;

public class SoundLib implements Runnable {

	File passed = new File("Fairy1.wav");
	static File ending = new File("Ending.wav");
	static File obstacle = new File("Obstacle.wav");
	File beginning = new File("Beginning.wav");

	private DataTransfer DTObj;
	public static boolean locked = false;

	public SoundLib(DataTransfer DT) {
		this.DTObj = DT;
	}

	@Override
	public void run() {

		while (true) {

//			while (locked == true) {
//				Thread.yield();
//			}
			if (DTObj.isStarted() == false && RobotMoves.round < 1) {

				playSound(beginning);
//				for (int i = 0; i < 1; i++) {
//					System.out.println("start");
//					playSound(beginning);
				// DTObj.setStarted(true);
//					System.out.println("moi");
				// }

				// locked = true;

//			} else if (DTObj.obstacleDetected == true && RobotMoves.round < 1) {
//
//				for (int y = 0; y < 1; y++) {
//					System.out.println("obstacle");
//					playSound(obstacle);
//				}
//				locked = true;
			}

		}

	}

	public void playSound(File sound) {

		while (DTObj.isSoundPlayed() == false) {

			System.out.println(Sound.playSample(sound));
			Sound.playSample(sound);
			System.out.println(Sound.getTime());

			if (Sound.getTime() == 0) {
				System.out.println(Sound.playSample(sound));
				System.out.println("sound played true");
				DTObj.setSoundPlayed(true);

			}
		}
	}

}
