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

public class RobotMoves implements Runnable {

	private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	protected static EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.A);
	protected static EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);

	Wheel left = WheeledChassis.modelWheel(motorL, 55).offset(50);
	Wheel right = WheeledChassis.modelWheel(motorR, 55).offset(-50);

	Chassis chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);
		
	MovePilot pilot = new MovePilot(chassis);

	private static float[] sample;
	private DataTransfer DTObj;

	// Ensin 8, logiikka toimii mutta väistää liian nopeasti mustalta takaisin,
	// tökkii
	// 11 toimii myös sisäradalla, mutta vähän huonommin kuin ulkokaarella
	private static double tooBlack = 5.5;
	// ensin 33, kävi liikaa valkoisella, logiikka toimii kuitenkin, tökkivästi
	// 30 toimii myös sisäradalla, mutta vähän huonommin kuin ulkokaarella
	private static double tooWhite = 26;
	//ulkoreunan vauhti 250, sisäreunan vauhti 230
	private static int fastest = 250;
	private static int round = 0;

	public RobotMoves(DataTransfer DT) {
		this.DTObj = DT;

	}

	@Override
	public void run() {
		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);
		cs.setCurrentMode("Red");

		// value in RGBs, 0 means zero light, 255 means maximum light
		sample = new float[cs.sampleSize()];

		while (true) {
			// 1. ALOITUS JA 6. ELI KUN TAKAISIN RADALLA
			// Kun status on 1 eli liikkuu
			// Normaali eteneminen viivalla
			if (DTObj.getStatus() == 1 && DTObj.isObstacleDetected() == false) {

				// Aloitus ulkoreunan puolelta
				// Kun robo menee viivan rajalla 50/50 ja eksyy valkoiselle liikaa, korjataan
				// vasemmalle
				if (getRed() >= tooWhite) {

					//ulkoreuna -> korjataan vasemmalle motorL.setSpeed(145), motorR.setSpeed(fastest)
					//sisäreuna -> korjataan oikealle motorR.setSpeed(130), motorL.setSpeed(fastest)
					motorL.setSpeed(145);
					motorR.setSpeed(fastest);
					motorL.forward();
					motorR.forward();

					// Aloitus ulkoreunan puolelta
					// Kun robo menee viivan rajalla 50/50 ja eksyy liikaa mustalle, korjataan
					// oikealle
				} else if (getRed() <= tooBlack) {

					//ulkoreuna -> korjataan oikealle motorR.setSpeed(145), motorL.setSpeed(fastest)
					//sisäreuna -> korjataan vasemmalle motorL.setSpeed(130), motorR.setSpeed(fastest)
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
				// Jos status on 0 ja este havaittu, kierrä este
			} else if (DTObj.getStatus() == 0 && DTObj.isObstacleDetected() == true) {
				// 3. SIIRRYTÄÄN METODIIN
				
				if (round == 0) {
					clearObstacle();
					round++;
					
				} else {
					motorR.stop();
					motorL.stop();
					//System.exit(0);
					
				}
				

				// 4. KATSOTAAN METODILLA OLLAANKO VIIVALLA, KOTONA EI OLTU :D
				detectLine();
				System.out.println(detectLine());
				System.out.println(getRed());

				// 4. EI SIIS PÄÄSTY TÄHÄN
				// Jos viiva havaittu, käännä oikealle
				if (DTObj.isLineDetected() == true) {
					System.out.println("line detected true");
					//searchLine();
					System.out.println("seuraavaksi vauhti 50");
					motorR.setSpeed(50);
					motorL.setSpeed(50);
					motorR.forward();
					motorL.forward();
					System.out.println("onko vauhti 50?");
//					chassis2.setLinearSpeed(50);
					//ulkoreuna
					//chassis.rotate(-80);
					//sisäreuna
//					chassis2.rotate(80);
//					chassis2.waitComplete();
					// DTObj.setStatus(1);

					// 5. VAAN HYPÄTTIIN SUORAAN TÄHÄN -> searchLine()
					// jos viivaa ei ole havaittu, etsi viivaa searchLine() metodilla
				} else {
					System.out.println("ollaanko searchissa?");
//					chassis2.setLinearSpeed(50);
					//ulkoreuna
					//chassis.rotate(-60);
					//sisäreuna
//					chassis2.rotate(60);
//					chassis2.waitComplete();
					searchLine();
				}

			} else {
				
				motorR.stop();
				motorL.stop();
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

	public static double getRed() {

		cs.fetchSample(sample, 0);
		double value = (double) (sample[0] * 100.0);

		return value;

	}

	public boolean detectLine() {
		boolean detectLine = false;

		if (getRed() <= 9) {
			DTObj.setLineDetected(true);
			detectLine = true;

		} else {
			DTObj.setLineDetected(false);
			detectLine = false;
		}

		return detectLine;

	}

	// 3. JATKUU KUNNES ESTE ON KIERRETTY
	public void clearObstacle() {
		// Tehdään kaari ja asetetaan obstacleDetected > false

		while (detectLine() == false) {
			//ulkoreuna
			System.out.println("este");
			chassis.setAngularSpeed(60);
			chassis.rotate(-70);
			Delay.msDelay(1500);
//			chassis.setLinearSpeed(95);
//			chassis.arc(340, 78);
//			chassis.waitComplete();
			pilot.setLinearSpeed(95);
			pilot.arc(340, 130, detectLine());
			System.out.println("este ohitettu");
			DTObj.setObstacleDetected(false);
			break;
			
			//sisäreuna
//			System.out.println("este");
//			chassis.setAngularSpeed(60);
//			chassis.rotate(45);
//			Delay.msDelay(1500);
//			chassis.setLinearSpeed(95);
//			chassis.arc(-340, 78);
//			chassis.waitComplete();
//			System.out.println("este ohitettu");
//			DTObj.setObstacleDetected(false);
			
//			break;
			
			
			

		}
	}

	// 5. JATKUU: JOS detectLine() ILMOITTAA ETTÄ OLLAAN VIIVALLA, MENNÄÄN EKAAN
	// IFIIN JA PÄÄSTÄÄN TAKAISIN VIIVANSEURAUKSEEN
	public void searchLine() {
		// niin kauan kun detectLine() antaa arvon false eli getRed() on >= kuin 6
		// tehdään kääntymisliikettä
		while (true) {
			System.out.println(getRed());
			detectLine();

			if (DTObj.isLineDetected() == true) {
				System.out.println("viiva");
				DTObj.setLineDetected(true);
				// chassis.setLinearSpeed(100);
				motorL.stop();
				motorR.stop();

				// Delay.msDelay(1000);
				DTObj.setStatus(1);
				break;

				// KÄÄNTYY ENSIN VASEMMALLE JA SITTEN OIKEALLE JOS VIIVAA EI OLE, NÄMÄ
				// KÄÄNTYMISET VOISI VIELÄ EROTELLA JA TSEKATA VÄLISSÄ OLLAANKO VIIVALLA,
				// MYÖS ISOMMAT KÄÄNNÖKSET VOISI TEHDÄ
			} else if (DTObj.isLineDetected() == false) {
				System.out.println("ei");
				// kääntyy vasemmalle
				motorL.setSpeed(35);
				motorR.setSpeed(100);
				motorL.forward();
				motorR.forward();
				Delay.msDelay(1500);

				if (detectLine() == false) {
					// kääntyy oikealle
					motorR.setSpeed(35);
					motorL.setSpeed(100);
					motorL.forward();
					motorR.forward();
					Delay.msDelay(2250);

				}
			}

		}
	}
}
