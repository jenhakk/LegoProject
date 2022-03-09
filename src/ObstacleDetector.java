import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

/**
 * Class where obstacles are being detected by the ultrasonic sensor.
 *
 */
public class ObstacleDetector implements Runnable {

	// Ultrasonic sensor
	private static EV3UltrasonicSensor us;
	/**
	 * int value for distance when obstacle is detected
	 */
	private static final int securityDistance = 25;
	/**
	 * int value used for saving return value from the getRange method
	 */
	private static int distanceValue = 0;

	private DataTransfer DTObj;

	/**
	 * Constructor of the class Creating object for Ultrasonic sensor
	 * 
	 * @param DT for bringing DataTransfer object to class
	 */
	public ObstacleDetector(DataTransfer DT) {
		this.DTObj = DT;
		us = new EV3UltrasonicSensor(SensorPort.S4);
	}

	/**
	 * Run method of the class Checks obstacle distance and sets status to 0 (stop)
	 * or 1 (move)
	 */
	public void run() {

		while (true) {

			// When robot is moving
			while (DTObj.getStatus() == 1) {

				// Check if distance of an obstacle is more than security distance (25 cm)
				if (getRange() > securityDistance) {
					DTObj.setObstacleDetected(false);

					// Else if distance of an obstacle is less than security distance, stop robot
					// and set obstacle detected to true
				} else if (getRange() < securityDistance) {

					DTObj.setStatus(0);
					DTObj.setObstacleDetected(true);
					System.out.println("Obstacle detected");

				}

			}
		}
	}

	/**
	 * Gets values collected by ultrasonic sensor and stores them to the list,
	 * fetches first index from the list, changes it to integer and multiplies it by
	 * 100 and saves it to the distanceValue attribute
	 * 
	 * @return returns first value from the list
	 */
	public float getRange() {
		SampleProvider sp = us.getDistanceMode();
		float[] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		distanceValue = (int) (sample[0] * 100);

		return distanceValue;
	}

}
