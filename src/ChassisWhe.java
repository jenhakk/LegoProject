import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.Move;
import lejos.utility.Matrix;

public class ChassisWhe extends WheeledChassis {

	DataTransfer DTObj;
	RobotMoves RM;

	public ChassisWhe(Wheel[] wheels, int dim) {
		super(wheels, dim);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getLinearSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLinearSpeed(double linearSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getAngularSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAngularSpeed(double angularSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getLinearAcceleration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLinearAcceleration(double linearAcceleration) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getAngularAcceleration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAngularAcceleration(double angularAcceleration) {
		// TODO Auto-generated method stub

	}

	@Override
	public Matrix getCurrentSpeed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVelocity(double linearSpeed, double angularSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVelocity(double linearSpeed, double direction, double angularSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void travelCartesian(double xSpeed, double ySpeed, double angularSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void travel(double linear) {
		// TODO Auto-generated method stub

	}

	@Override
	public void arc(double radius, double angle) {
		if (DTObj.isLineDetected() == true) {

			RM.chas.rotate(-40);
		}

		if (DTObj.isLineDetected() == false) {

			RM.chas.rotate(80);

		}

		DTObj.setStatus(1);

	}

	@Override
	public double getMaxLinearSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMaxAngularSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void waitComplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStalled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getMinRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSpeed(double linearSpeed, double angularSpeed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAcceleration(double forwardAcceleration, double angularAcceleration) {
		// TODO Auto-generated method stub

	}

	@Override
	public PoseProvider getPoseProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public Move getDisplacement(Move move) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rotate(double angular) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getLinearVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLinearDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAngularVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
