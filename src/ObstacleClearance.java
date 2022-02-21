import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.utility.Delay;

/**
 * Class where robot goes around the obstacle
 *
 **/
public class ObstacleClearance implements Runnable{
	
	private static EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.A);
	private static EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);
	private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	private static EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S4);
	
	private static boolean obstacleDetected;
	private static int status;
	private static int round;
	private DataTransfer DTObj;
	
	
	public ObstacleClearance(DataTransfer DT) {
		this.DTObj = DT;
	}


	/**
	 * Method where robot goes around the detected obstacle
	 *
	 **/
	@Override
	public void run() {
		
		while (true) {
			
			if (DataTransfer.isObstacleDetected() == true) {
				
				Sound.beep();
				motorL.stop();
				motorR.stop();

				motorR.setSpeed();
				motorL.setSpeed(speed);
				motorR.rotate(45);
				
				motorL.forward();
								
				motorR.setSpeed(15);
				motorR.forward();
				motorR.setSpeed(60);
				motorR.set
				
				
			}
		}
		
	}


	
}
