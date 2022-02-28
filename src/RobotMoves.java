import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

public class RobotMoves implements Runnable {

	private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	protected static EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.A);
	protected static EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);

	Wheel left = WheeledChassis.modelWheel(motorL, 55).offset(50);
	Wheel right = WheeledChassis.modelWheel(motorR, 55).offset(-50);

	Chassis chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);

	private static float[] sample;
	private DataTransfer DTObj;

	// Ensin 8, logiikka toimii mutta v�ist�� liian nopeasti mustalta takaisin,
	// t�kkii
	// 11 toimii my�s sis�radalla, mutta v�h�n huonommin kuin ulkokaarella
	private static int tooBlack = 6;
	// ensin 33, k�vi liikaa valkoisella, logiikka toimii kuitenkin, t�kkiv�sti
	// 30 toimii my�s sis�radalla, mutta v�h�n huonommin kuin ulkokaarella
	private static int tooWhite = 26;
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
			if (DTObj.getStatus() == 1) {

				// Aloitus ulkoreunan puolelta
				// Kun robo menee viivan rajalla 50/50 ja eksyy valkoiselle liikaa, korjataan
				// vasemmalle
				if (getRed() >= tooWhite) {

					motorL.setSpeed(145);
					motorR.setSpeed(fastest);
					motorL.forward();
					motorR.forward();

					// Aloitus ulkoreunan puolelta
					// Kun robo menee viivan rajalla 50/50 ja eksyy liikaa mustalle, korjataan
					// oikealle
				} else if (getRed() <= tooBlack) {

					motorR.setSpeed(145);
					motorL.setSpeed(fastest);
					motorR.forward();
					motorL.forward();

				} else {
					motorR.setSpeed(250);
					motorL.setSpeed(250);
					motorR.forward();
					motorL.forward();
				}
				//2. ESTE HAVAITTU
				// Jos status on 0 ja este havaittu, kierr� este
			} else if (DTObj.getStatus() == 0 && DTObj.isObstacleDetected() == true) {
				//3. SIIRRYT��N METODIIN
				clearObstacle();

				//4. KATSOTAAN METODILLA OLLAANKO VIIVALLA, KOTONA EI OLTU :D
				detectLine();
				System.out.println(DTObj.isLineDetected());
				System.out.println(getRed());
				
				//4. EI SIIS P��STY T�H�N
				// Jos viiva havaittu, k��nn� oikealle
				if (DTObj.isLineDetected() == true) {
					System.out.println("p��stiink� eteenp�in?");
					chassis.setLinearSpeed(50);
					chassis.rotate(-60);
					chassis.waitComplete();

					DTObj.setStatus(1);

				//5. VAAN HYP�TTIIN SUORAAN T�H�N -> searchLine()
					// jos viivaa ei ole havaittu, etsi viivaa searchLine() metodilla
				} else {
					System.out.println("ollaanko searchissa?");
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
	public static int getRed() {

		cs.fetchSample(sample, 0);
		int value = (int) (sample[0] * 100);

		return value;

	}

	public boolean detectLine() {
		boolean detectLine = false;

		if (getRed() <= 10) {
			// DTObj.setLineDetected(true);
			detectLine = true;

		} else {
			// DTObj.setLineDetected(false);
			detectLine = false;
		}

		return detectLine;

	}
	//3. JATKUU KUNNES ESTE ON KIERRETTY
	public void clearObstacle() {
		// Tehd��n kaari ja asetetaan obstacleDetected > false
		System.out.println("ohitetaan este");
		chassis.setAngularSpeed(60);
		chassis.rotate(-70);
		Delay.msDelay(1500);
		chassis.setLinearSpeed(95);
		chassis.arc(340, 120);
		chassis.waitComplete();
		System.out.println("este kierretty");
		DTObj.setObstacleDetected(false);

	}

	//5. JATKUU: JOS detectLine() ILMOITTAA ETT� OLLAAN VIIVALLA, MENN��N EKAAN IFIIN JA P��ST��N TAKAISIN VIIVANSEURAUKSEEN
	public void searchLine() {
		// niin kauan kun detectLine() antaa arvon false eli getRed() on >= kuin 6
		// tehd��n k��ntymisliikett�
		while (true) {
			System.out.println(getRed());
			detectLine();

			if (detectLine() == true) {
				System.out.println("viiva");
				DTObj.setLineDetected(true);
				DTObj.setStatus(1);
				break;

			//K��NTYY ENSIN VASEMMALLE JA SITTEN OIKEALLE JOS VIIVAA EI OLE, N�M� K��NTYMISET VOISI VIEL� EROTELLA JA TSEKATA V�LISS� OLLAANKO VIIVALLA, 
				//MY�S ISOMMAT K��NN�KSET VOISI TEHD�
			} else if (detectLine() == false) {
				System.out.println("ei");
				// k��ntyy vasemmalle
				motorL.setSpeed(66);
				motorR.setSpeed(100);
				motorL.forward();
				motorR.forward();
				Delay.msDelay(2000);

				// } else {
				// k��ntyy oikealle
				motorR.setSpeed(66);
				motorL.setSpeed(100);
				motorL.forward();
				motorR.forward();
				Delay.msDelay(2000);

			}
		}

	}
}