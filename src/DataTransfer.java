/**
 * Class transfers data between threads.
 *
 */
public class DataTransfer {

	public static boolean obstacleDetected = false;
	private static int status = 1;
	private static int round = 0;

	/**
	 * Method that checks boolean value of obstacleDetected
	 *
	 */
	public static boolean isObstacleDetected() {
		return obstacleDetected;
	}

	/**
	 * Method that sets boolean value to obstacleDetected
	 *
	 */
	public static void setObstacleDetected(boolean obstacleDetected) {
		DataTransfer.obstacleDetected = obstacleDetected;
	}

	/**
	 * Method that gets status int value
	 *
	 */
	public static int getStatus() {
		return status;
	}

	/**
	 * Method that sets int value of status
	 *
	 */
	public static void setStatus(int status) {
		DataTransfer.status = status;
	}

	/**
	 * Method that gets round int value
	 *
	 */
	public static int getRound() {
		return round;
	}

	/**
	 * Method that sets int value of status
	 *
	 */
	public static void setRound(int round) {
		DataTransfer.round = round;
	}
}
