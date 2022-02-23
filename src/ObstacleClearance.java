import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

/**
 * Class where robot goes around the obstacle
 *
 **/
public class ObstacleClearance implements Runnable{
	
	//private static EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.A);
	//private static EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.B);
	
	
	//private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	//private static EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S4);
	
	
	
	private static boolean obstacleDetected;
	private static int status = 0;
	private static int round = 0;
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
		System.out.println("Obstacle clearance");
		
		//while (true) {
			
			//if (DataTransfer.isObstacleDetected() == true) {
				
				
				
				
			//}
		//}
		
	}


	
}
