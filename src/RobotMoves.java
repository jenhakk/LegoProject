import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

public class RobotMoves implements Runnable {

	private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	protected static EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.A);
	protected static EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);

	Wheel left = WheeledChassis.modelWheel(motorL, 55).offset(50);
	Wheel right = WheeledChassis.modelWheel(motorR, 55).offset(-50);

	Chassis chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);

	MovePilot pilot = new MovePilot(chassis);
	Stopwatch timer = new Stopwatch();
	

	private static float[] sample;
	private DataTransfer DTObj;
	private static double tooBlack = 5.5;
	private static double tooWhite = 28;
	// ulkoreunan vauhti 245, sis‰reunan vauhti 230
	private static int fastest = 245;
	private static int round = 0;
	private static int time = 0;

	public RobotMoves(DataTransfer DT) {
		this.DTObj = DT;

	}
	
	//Timer tsekkaus
	//Jos eksyy viivalta muulloin kuin esteen ohituksen j‰lkeen niin miten k‰y?!
	//ƒ‰nien etsint‰ ja koodaus
	//Sis‰puolen rata, miten toimii?
	//ohjelman pys‰ytys nappulasta

	@Override
	public void run() {
		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);
		cs.setCurrentMode("Red");

		// arvo 0 on tummin, 100 on valoisin
		sample = new float[cs.sampleSize()];

		while (true) {
			timer.reset();
			// 1. ALOITUS JA 6. ELI KUN TAKAISIN RADALLA
			// Kun status on 1 eli liikkuu
			// Normaali eteneminen viivalla
			if (DTObj.getStatus() == 1 && DTObj.isObstacleDetected() == false) {

				// Aloitus ulkoreunan puolelta
				// Kun robo menee viivan rajalla 50/50 ja eksyy valkoiselle liikaa, korjataan vasemmalle
				if (getRed() >= tooWhite) {

					// ulkoreuna -> korjataan vasemmalle motorL.setSpeed(145),
					// motorR.setSpeed(fastest)
					// sis‰reuna -> korjataan oikealle motorR.setSpeed(130),
					// motorL.setSpeed(fastest)
					motorL.setSpeed(145);
					motorR.setSpeed(fastest);
					motorL.forward();
					motorR.forward();

				// Aloitus ulkoreunan puolelta
				// Kun robo menee viivan rajalla 50/50 ja eksyy liikaa mustalle, korjataan oikealle
				} else if (getRed() <= tooBlack) {

					// ulkoreuna -> korjataan oikealle motorR.setSpeed(145),
					// motorL.setSpeed(fastest)
					// sis‰reuna -> korjataan vasemmalle motorL.setSpeed(130),
					// motorR.setSpeed(fastest)
					motorR.setSpeed(145);
					motorL.setSpeed(fastest);
					motorR.forward();
					motorL.forward();

				} else {
					motorR.setSpeed(fastest);
					motorL.setSpeed(fastest);
					motorR.forward();
					motorL.forward();
				}
			// 2. ESTE HAVAITTU
			// Jos status on 0 ja este havaittu, kierr‰ este
			} else if (DTObj.getStatus() == 0 && DTObj.isObstacleDetected() == true) {
				
				// 3. SIIRRYTƒƒN METODIIN clearObstacle
				if (round == 0) {
					clearObstacle();
					round++;

				} else {
					motorR.stop();
					motorL.stop();
					time = timer.elapsed();
					System.out.println("Time: " + time/100.0F + " seconds");
					Delay.msDelay(5000);
					System.exit(0);

				}

				// 4. KATSOTAAN METODILLA OLLAANKO VIIVALLA
				detectLine();
				// System.out.println(detectLine());
				// System.out.println(getRed());

				
				// Jos viiva havaittu, k‰‰nn‰ oikealle
				if (DTObj.isLineDetected() == true) {

					System.out.println("line detected true");
					pilot.setAngularSpeed(60);
					pilot.rotate(-30);
					searchLine();

				// 5. VAAN HYPƒTTIIN SUORAAN TƒHƒN -> searchLine()
				// jos viivaa ei ole havaittu, etsi viivaa searchLine() metodilla
				} else {
					System.out.println("ollaanko searchissa?");
					searchLine();
				}

			} else {

				motorR.stop();
				motorL.stop();
			}
			while (Button.getButtons() != 0) {
				break;

			}
		}

	}

	/**
	 * Method that gets light values in to list, changes them to integers (*100) and
	 * returns the first index from the list.
	 *
	 */
//	public static int getRed() {
//
//		cs.fetchSample(sample, 0);
//		int value = (int) (sample[0] * 100);
//
//		return value;
//
//	}

	/**
	 * Method that gets light values in to list, changes them to doubles (*100) and
	 * returns the first index from the list.
	 *
	 */
	public static double getRed() {

		cs.fetchSample(sample, 0);
		double value = (double) (sample[0] * 100.0);

		return value;

	}

	/**
	 * Method that checks if values from color sensor are <= than tooBlack values and changes lineDetected to boolean, returns detectLine value
	 *
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
	 * Method that executes an arc around the obstacle. If detectLine-method returns false, robot makes a turn to the right and then an arc. 
	 * If line is detected in the middle of arc, robot stops. ObstacleDetected is set to false.
	 */
	
	public void clearObstacle() {
		// Tehd‰‰n kaari ja asetetaan obstacleDetected > false

		while (detectLine() == false) {
			// ulkoreuna

			pilot.setAngularSpeed(60);
			pilot.rotate(-60);
			// Delay.msDelay(1500);
			pilot.setLinearSpeed(95);
			pilot.arc(300, 200, true);
			while (pilot.isMoving()) {
				if (detectLine() == true) {
					pilot.stop();
					System.out.println("stop");
				}
			}

			DTObj.setObstacleDetected(false);

			// break;

			// sis‰reuna
//			pilot.setAngularSpeed(60);
//			pilot.rotate(60);
//			// Delay.msDelay(1500);
//			pilot.setLinearSpeed(95);
//			pilot.arc(-300, 200, true);
//			while (pilot.isMoving()) {
//				if (detectLine() == true) {
//					pilot.stop();
//					System.out.println("stop");
//				}
//			}
//
//			DTObj.setObstacleDetected(false);
		}
	}

	/**
	 * Method that tries to search back to line following. It uses detectLine(): If it returns true, motors stop, status is set to 1 (moving) and robot returns to following the line.
	 * If it returns false: pilot is used to make a waving move, first to the right and then to left. If detectLine() returns true (line is found), robot stops and breaks the loop.
	 *
	 */
	// 5. JATKUU: JOS detectLine() ILMOITTAA ETTƒ OLLAAN VIIVALLA, MENNƒƒN EKAAN
	// IFIIN JA PƒƒSTƒƒN TAKAISIN VIIVANSEURAUKSEEN
	public void searchLine() {
		// niin kauan kun detectLine() antaa arvon false eli getRed() on >= kuin 6
		// tehd‰‰n k‰‰ntymisliikett‰
		while (true) {
			
			detectLine();

			if (DTObj.isLineDetected() == true) {
				DTObj.setLineDetected(true);
				motorL.stop();
				motorR.stop();
				DTObj.setStatus(1);
				break;

			//start searching the line
			} else if (DTObj.isLineDetected() == false) {
				
				pilot.setLinearSpeed(70);
				pilot.travelArc(100, 150, true);

				while (pilot.isMoving()) {
					if (detectLine() == true) {
						pilot.stop();
						break;
					}
				}

				//pilot.setLinearSpeed(70);
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
