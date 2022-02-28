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


	private static int status = 0;
	private static int obsCount = 0;
	private DataTransfer DTObj;
	private LineFollower LFObj;

	Wheel left = WheeledChassis.modelWheel(LineFollower.motorL, 55).offset(50);
	Wheel right = WheeledChassis.modelWheel(LineFollower.motorR, 55).offset(-50);

	Chassis chassis = new WheeledChassis(new Wheel[] { left, right }, WheeledChassis.TYPE_DIFFERENTIAL);
	// MovePilot pilot = new MovePilot(chassis);

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

			while (DTObj.isObstacleDetected() == true) {
				System.out.println("Obstacle clearance");

				chassis.setLinearSpeed(50);
				chassis.rotate(-45);
				Delay.msDelay(800);
				chassis.setLinearSpeed(95);
				chassis.arc(340, 90);
				chassis.waitComplete();
				chassis.setLinearSpeed(50);
				chassis.rotate(-60);
				chassis.waitComplete();
				
				if (DTObj.isLineDetected() == false) {
					System.out.println("line detected false");
					LFObj.searchLine();
					break;
				}
			}
			DTObj.setObstacleDetected(false);
			DTObj.setObsCount(1);
			DTObj.setStatus(1);
//			}
//
//			else if (DTObj.isLineDetected() == true) {
//				System.out.println("Line Detected true");
//
//				DTObj.setObstacleDetected(false);
//
//				DTObj.setStatus(1);
//			}

			// Delay.msDelay(800);
			// rotate ja toinen delay hyv‰
			// LFObj.motorR.rotateTo(300);
			// Delay.msDelay(800);

			// System.out.println("rotate tehty");
			// pilot.setLinearSpeed(100);
			// System.out.println("linear speed tehty");
			// pit‰‰ viilata mihin kohtaan pys‰htyy kierroksen j‰lkeen, j‰lkimm‰inen arvo
			//

//				pilot.setAngularSpeed(50);
//				pilot.rotate(-45);

		}
	}
}
