import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

/**
 * Class where obstacles are being detected by the ultrasonic sensor.
 *
 **/
public class ObstacleDetector implements Runnable{

	private static EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S4);
	private static final int securityDistance = 30;
	private static final SampleProvider sp = us.getDistanceMode();
	private static int distanceValue = 0;
	private static float[] sample = new float[sp.sampleSize()];
	private DataTransfer DTObj;

	public ObstacleDetector(DataTransfer DT) {
		this.DTObj = DT; 
	}

	/**
	 * Method that detects an obstacle and sets status to 0 (stop) or 1 (move)
	 *
	 **/
	public void run() {

		while (true) {

			if (getRange() > securityDistance) {

				DataTransfer.setStatus(1);

			} else {

				DataTransfer.setStatus(0);
				LCD.drawString("Object found", 0, 1);
				LCD.refresh();
				DataTransfer.setObstacleDetected(true);
				Sound.twoBeeps();
			}
		}
	}

	/**
	 * Method that gets distance from the ultrasonic sensor into a list, changes them to integers (*100) and returns the first index from the list.
	 *
	 **/
	public float getRange() {
		
		sp.fetchSample(sample, 0);
		distanceValue = (int) (sample[0] * 100);

		return distanceValue;
	}

}
