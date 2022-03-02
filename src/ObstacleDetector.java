import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

/**
 * Class where obstacles are being detected by the ultrasonic sensor.
 *
 **/
public class ObstacleDetector implements Runnable {

	private static EV3UltrasonicSensor us;
	private static final int securityDistance = 20;
	private static int distanceValue = 0;

	private DataTransfer DTObj;

	public ObstacleDetector(DataTransfer DT) {
		this.DTObj = DT;
		us = new EV3UltrasonicSensor(SensorPort.S4);
	}

	/**
	 * Method that detects an obstacle and sets status to 0 (stop) or 1 (move)
	 *
	 **/
	public void run() {
		// System.out.println("obstacle detector");
		while (true) {

			while (DTObj.getStatus() == 1) {
				// System.out.println("Obstacle Detector in while " + DTObj.getStatus());
				if (getRange() > securityDistance) {
					DTObj.setObstacleDetected(false);

				} else if (getRange() < securityDistance) {

					DTObj.setStatus(0);
					// System.out.println("Obstacle Detector: " + DTObj.getStatus());
					DTObj.setObstacleDetected(true);
					System.out.println("Obstacle detected");

				}

			}
		}
	}

	/**
	 * Method that gets distance from the ultrasonic sensor into a list, changes
	 * them to integers (*100) and returns the first index from the list.
	 *
	 **/
	public float getRange() {
		SampleProvider sp = us.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		distanceValue = (int) (sample[0] * 100);

		return distanceValue;
	}

}
