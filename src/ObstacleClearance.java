import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.*;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

/**
 * Class where robot goes around the obstacle
 *
 **/
public class ObstacleClearance implements Runnable {

	// private static EV3LargeRegulatedMotor motorR = new
	// EV3LargeRegulatedMotor(MotorPort.A);
	// private static EV3LargeRegulatedMotor motorL = new
	// EV3LargeRegulatedMotor(MotorPort.B);

	// private static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
	// private static EV3UltrasonicSensor us = new
	// EV3UltrasonicSensor(SensorPort.S4);

	
	private static boolean immediateReturn;
	private static int status = 0;
	private static int round = 0;
	private DataTransfer DTObj;
	private LineFollower LFObj;
	
	Wheel left = WheeledChassis.modelWheel(LineFollower.motorL, 55).offset(50);
	Wheel right = WheeledChassis.modelWheel(LineFollower.motorR, 55).offset(-50);

	Chassis chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);
	MovePilot pilot = new MovePilot(chassis);

	public ObstacleClearance(DataTransfer DT, LineFollower LF) {
		this.DTObj = DT;
		this.LFObj = LF;
	}

	/**
	 * Method where robot goes around the detected obstacle
	 *
	 **/
	@Override
	public void run() {

		while (true) {
			// System.out.println("Obstacle clearance");

			//LineDetected t‰h‰n? Pys‰ytt‰m‰‰n arcin?
			if (DTObj.isObstacleDetected() == true) {

				// Delay.msDelay(800);
				// rotate ja toinen delay hyv‰
				// LFObj.motorR.rotateTo(300);
				// Delay.msDelay(800);
				while (!pilot.isMoving())Thread.yield();
				pilot.setAngularSpeed(50);
				pilot.rotate(-45);
				Delay.msDelay(800);
				pilot.setLinearSpeed(120);
				pilot.arc(260, 120);
				
				
				//System.out.println("rotate tehty");
				//pilot.setLinearSpeed(100);
				//System.out.println("linear speed tehty");
				// pit‰‰ viilata mihin kohtaan pys‰htyy kierroksen j‰lkeen, j‰lkimm‰inen arvo
				//
				
				
				System.out.println("arc tehty");
//				pilot.setAngularSpeed(50);
//				pilot.rotate(-45);
				
				//T‰m‰ pit‰‰ siirt‰‰ johonkin muuhun tai tehd‰ jotain muuta!
				//DTObj.setObstacleDetected(false);

				// DTObj.setStatus(1);

			}

		}
	}

	
}
