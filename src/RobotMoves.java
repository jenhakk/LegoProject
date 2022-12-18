
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

/**
 * Class where robot follows the line and goes around the detected obstacle.
 *
 */
public class RobotMoves implements Runnable {

	private DataTransfer DTObj;

	// Sensor and motors
	private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	protected static EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.A);
	protected static EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);

	// Wheels for chassis and chassis for move pilot
	Wheel left = WheeledChassis.modelWheel(motorL, 55).offset(50);
	Wheel right = WheeledChassis.modelWheel(motorR, 55).offset(-50);
	Chassis chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);
	MovePilot pilot = new MovePilot(chassis);

	/**
	 * float list for color sensor values
	 */
	private static float[] sample;
	/**
	 * double value for line limit
	 */
	private static double tooBlack = 5.5;
	/**
	 * double value for line limit
	 */
	private static double tooWhite = 26;
	// outer line speed 245, inner line speed 230
	/**
	 * int value for faster motor speed
	 */
	private static int fastest = 245;
	/**
	 * int value for encountered obstacles
	 */
	public static int round = 0;

	/**
	 * Constructor of the class
	 * 
	 * @param DT for bringing DataTransfer object to class
	 */
	public RobotMoves(DataTransfer DT) {
		this.DTObj = DT;

	}

	/**
	 * Run method of the class
	 */
	@Override
	public void run() {

		// settings for color sensor
		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);
		cs.setCurrentMode("Red");

		sample = new float[cs.sampleSize()];

		while (true) {

			// Status 1 = moving
			// Normal line following

			// 1.1 If first condition was not executed, check if robot is moving and has
			// obstacle not been detected
			if (DTObj.getStatus() == 1 && DTObj.isObstacleDetected() == false) {

				// Starts on outer line

				// 1.1.1 When robot is on the edge of line 50/50 and moves too much to the
				// white,
				// move back to left
				if (getRed() >= tooWhite) {

					// outer line -> return left motorL.setSpeed(145), motorR.setSpeed(fastest)
					// inner line -> return right motorR.setSpeed(130), motorL.setSpeed(fastest)
					motorL.setSpeed(145);
					motorR.setSpeed(fastest);
					motorL.forward();
					motorR.forward();

					// 1.1.2 When robot is on the edge of line 50/50 and moves too much to the
					// black,
					// move back to right
				} else if (getRed() <= tooBlack) {

					// outer line -> return right motorR.setSpeed(145), motorL.setSpeed(fastest)
					// inner line -> return left motorL.setSpeed(130), motorR.setSpeed(fastest)
					motorR.setSpeed(145);
					motorL.setSpeed(fastest);
					motorR.forward();
					motorL.forward();

					// 1.1.3. Otherwise move forward
				} else {
					motorR.setSpeed(fastest);
					motorL.setSpeed(fastest);
					motorR.forward();
					motorL.forward();
				}

				// 1.2 Else if first or second condition aren't executed, check if robot isn't
				// moving and obstacle is detected
			} else if (DTObj.getStatus() == 0 && DTObj.isObstacleDetected() == true) {

				// 1.2.1 Check if round is zero (obstacle has not been passed yet) and then
				// clear the obstacle.
				if (round == 0) {
					clearObstacle();
					round++;

					// 1.2.2 round is more than 0, stop motors and exit system.
				} else {

					motorR.stop();
					motorL.stop();
					System.exit(0);

				}

				detectLine();

				// 2.1 After the arc has been made and line is detected, make a turn right to
				// correct pose
				if (DTObj.isLineDetected() == true) {

					System.out.println("line detected true");
					pilot.setAngularSpeed(60);
					pilot.rotate(-30);

					// start searching the line
					searchLine();

					// 2.2 else (line is not detected, search line with searcLine())
				} else {

					searchLine();

				}

				// 1.3 Stop motors
			} else {

				motorR.stop();
				motorL.stop();
			}
		}

	}

	/**
	 * Method that gets light values in to list, changes them to doubles (*100).
	 * Value 0 is the darkest, 100 the lightest.
	 * 
	 * @return returns the first index from the list
	 */
	public static double getRed() {

		cs.fetchSample(sample, 0);
		double value = (double) (sample[0] * 100.0);

		return value;

	}

	/**
	 * Method that checks if values from color sensor are less or equal than
	 * tooBlack (5.5) values and changes lineDetected to boolean value
	 * 
	 * @return returns as a boolean value if the line has been detected
	 */
	public boolean detectLine() {
		boolean detectLine = false;

		if (getRed() <= tooBlack) {
			DTObj.setLineDetected(true);
			detectLine = true;

		} else {
			DTObj.setLineDetected(false);
			detectLine = false;
		}

		return detectLine;

	}

	/**
	 * Method that executes an arc around the obstacle. If detectLine() returns
	 * false, robot makes a turn to the right and then the arc. If line is detected
	 * in the middle of the arc, robot stops. ObstacleDetected is set to false.
	 */
	public void clearObstacle() {

		while (detectLine() == false) {

			// OUTER LINE
			pilot.setAngularSpeed(60);
			pilot.rotate(-60);
			pilot.setLinearSpeed(95);
			pilot.arc(300, 200, true);

			// Check if line is detected while making the arc, if true pilot stops.
			while (pilot.isMoving()) {
				if (detectLine() == true) {
					pilot.stop();
				}
			}

			DTObj.setObstacleDetected(false);

		}
	}

	/**
	 * Method that tries to search back to line following. It uses detectLine(): If
	 * it returns true, motors stop, status is set to 1 (moving) and robot returns
	 * to following the line. If it returns false: pilot is used to make a waving
	 * move, first to the right and then to left. If detectLine() returns true (line
	 * is found), robot stops and breaks the loop.
	 */
	public void searchLine() {

		while (true) {

			detectLine();

			if (DTObj.isLineDetected() == true) {
				DTObj.setLineDetected(true);
				motorL.stop();
				motorR.stop();
				DTObj.setStatus(1);
				break;

				// start searching the line
			} else if (DTObj.isLineDetected() == false) {

				pilot.setLinearSpeed(70);
				pilot.travelArc(100, 150, true);

				while (pilot.isMoving()) {
					if (detectLine() == true) {
						pilot.stop();
						break;
					}
				}

				pilot.travelArc(-100, 150, true);

				while (pilot.isMoving()) {
					if (detectLine() == true) {
						pilot.stop();
						break;
					}

				}

			}
		}
	}

}
