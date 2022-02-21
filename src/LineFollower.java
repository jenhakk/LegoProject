
/**
 * Class contains robots movements and color sensor to follow the line.
 *
 */
import lejos.ev3.*;
import lejos.hardware.*;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.Color;

public class LineFollower implements Runnable {

	private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	private static EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.A);
	private static EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);

	private static final int threshold = 30;
	private static float[] sample;
	private static int round;
	private DataTransfer DTObj;

	public LineFollower(DataTransfer DT) {
		this.DTObj = DT;
	}

	/**
	 * Method that uses color sensor to get light value in RGB and keeps the robot on the line.
	 *
	 */
	@Override
	public void run() {
		cs = new EV3ColorSensor(SensorPort.S3);

		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);
		cs.setCurrentMode("Red");

		// value in RGBs, 0 means zero light, 255 means maximum light
		System.out.println("hello");
		sample = new float[cs.sampleSize()];

		System.out.println("Push any button to start");
		Button.waitForAnyPress();

		while (true) {

			motorR.setSpeed(250);
			motorL.setSpeed(250);
			motorR.forward();
			motorL.forward();

			if (getRed() > threshold) {

				motorR.setSpeed(3);
				// motorR.stop();

				motorL.setSpeed(50);
				motorL.forward();

			} else if (getRed() < threshold) {

				motorL.setSpeed(3);
				// motorL.stop();

				motorR.setSpeed(50);
				motorR.forward();
			}
			LCD.drawInt(getRed(), 3, 9, 0);
			LCD.refresh();

			if (Button.getButtons() != 0) {
				break;
			}
		}

	}

	/**
	 * Method that gets light values in to list, changes them to integers (*100) and returns the first index from the list.
	 *
	 */
	public static int getRed() {

		cs.fetchSample(sample, 0);
		int value = (int) (sample[0] * 100);

		return value;

	}
}
