/**
 * Class transfers data between threads.
 *
 */
public class DataTransfer {

	public boolean obstacleDetected = false;
	public int status = 1;
	private int obsCount = 0;
	public boolean lineDetected = false;
	
	
	//private static boolean lineDetected = false;

	public boolean isLineDetected() {
		return lineDetected;
	}

	public void setLineDetected(boolean lineDetected) {
		this.lineDetected = lineDetected;
	}

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
	 * Method that get the count of obstacles encountered
	 *
	 */
	public int getObsCount() {
		return obsCount;
	}

	/**
	 * Method that sets the count of obstacles encountered
	 *
	 */
	public void setObsCount(int obsCount) {
		this.obsCount = obsCount;
	}

	

}
