/**
 * Class transfers data between threads.
 *
 */
public class DataTransfer {

	public boolean obstacleDetected = false;
	public int status = 1;
	private int round = 0;
	
	
	//private static boolean lineDetected = false;

	/**
	 * Method that checks boolean value of obstacleDetected
	 *
	 */
	public boolean isObstacleDetected() {
		return obstacleDetected;
	}

	/**
	 * Method that sets boolean value to obstacleDetected
	 *
	 */
	public void setObstacleDetected(boolean obstacleDetected) {
		this.obstacleDetected = obstacleDetected;
	}

	/**
	 * Method that gets status int value
	 *
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Method that sets int value of status
	 *
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Method that gets round int value
	 *
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Method that sets int value of status
	 *
	 */
	public void setRound(int round) {
		this.round = round;
	}
}
