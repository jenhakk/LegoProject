
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
import lejos.utility.Delay;

public class LineFollower implements Runnable {

	private static EV3ColorSensor cs;
	protected static EV3LargeRegulatedMotor motorR;
	protected static EV3LargeRegulatedMotor motorL;

	private static float[] sample;
	private DataTransfer DTObj;

	// Ensin 8, logiikka toimii mutta väistää liian nopeasti mustalta takaisin,
	// tökkii
	// 11 toimii myös sisäradalla, mutta vähän huonommin kuin ulkokaarella
	private static int tooBlack = 6;
	// ensin 33, kävi liikaa valkoisella, logiikka toimii kuitenkin, tökkivästi
	// 30 toimii myös sisäradalla, mutta vähän huonommin kuin ulkokaarella
	private static int tooWhite = 26;
	private static int fastest = 250;

	public LineFollower(DataTransfer DT) {
		this.DTObj = DT;
		cs = new EV3ColorSensor(SensorPort.S3);
		motorR = new EV3LargeRegulatedMotor(MotorPort.A);
		motorL = new EV3LargeRegulatedMotor(MotorPort.B);
	}

	/**
	 * Method that uses color sensor to get light value in RGB and keeps the robot
	 * on the line.
	 *
	 */
	@Override
	public void run() {

		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);
		cs.setCurrentMode("Red");

		// value in RGBs, 0 means zero light, 255 means maximum light
		//System.out.println("hello");
		sample = new float[cs.sampleSize()];

		// System.out.println("Push any button to start");
		// Button.waitForAnyPress();

		//System.out.println("LineFollower " + DTObj.getStatus());

		while (true) {
			//System.out.println(getRed());
			if (DTObj.getStatus() == 1) {
				// Aloitus ulkoreunan puolelta
				// Kun robo menee viivan rajalla 50/50 ja eksyy valkoiselle liikaa, korjataan
				// vasemmalle

				if (getRed() >= tooWhite) {

					//motorR.setSpeed(230);
					//motorL.setSpeed(230);
					//motorL.forward();
					//motorR.forward();
					// motorL.flt();
					// motorL.stop();

					// ensin 40, toimii tökkivästi
					motorL.setSpeed(145);
					motorR.setSpeed(fastest);
					motorL.forward();
					motorR.forward();

					// Aloitus ulkoreunan puolelta
					// Kun robo menee viivan rajalla 50/50 ja eksyy liikaa mustalle, korjataan
					// oikealle
				} else if (getRed() <= tooBlack) {

//					motorR.setSpeed(230);
//					motorL.setSpeed(230);
//					motorL.forward();
//					motorR.forward();
//					motorR.flt();
					// motorR.stop();

					// ensin 40, toimii tökkivästi
					// motorL.setSpeed(60);
					// motorL.forward();
					motorR.setSpeed(145);
					motorL.setSpeed(fastest);
					motorR.forward();
					motorL.forward();

					// Väli 4-31 -> skaala 27
				} else {
					motorR.setSpeed(250);
					motorL.setSpeed(250);
					motorR.forward();
					motorL.forward();
				}

//				if (Button.getButtons() != 0) {
//					break;
//				}
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

}
