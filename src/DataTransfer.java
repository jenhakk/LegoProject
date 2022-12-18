
/**
 * Class that transfers data between threads.
 */
public class DataTransfer {

	/**
	 * boolean value for detected obstacle
	 */
	public boolean obstacleDetected = false;
	/**
	 * int value for robot's movement: 1 = moving, 0 = stopped
	 */
	public int status = 1;
	/**
	 * boolean value for detected line
	 */
	public boolean lineDetected = false;

	/**
	 * Method that checks boolean value of lineDetected
	 * 
	 * @return returns if line has been detected
	 */
	public boolean isLineDetected() {
		return lineDetected;
	}

	/**
	 * Method that sets boolean value to lineDetected
	 * 
	 * @param lineDetected boolean value
	 */
	public void setLineDetected(boolean lineDetected) {
		this.lineDetected = lineDetected;
	}

	/**
	 * Method that checks boolean value of obstacleDetected
	 * 
	 * @return returns if obstacle has been detected
	 */
	public boolean isObstacleDetected() {
		return obstacleDetected;
	}

	/**
	 * Method that sets boolean value to obstacleDetected
	 * 
	 * @param obstacleDetected boolean value
	 */
	public void setObstacleDetected(boolean obstacleDetected) {
		this.obstacleDetected = obstacleDetected;
	}

	/**
	 * Method that gets status int value
	 * 
	 * @return returns int value of status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Method that sets int value of status
	 * 
	 * @param status int value of status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
