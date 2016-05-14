/**
 * 
 */
package baseline.exceptions;

/**
 * @author sbt-lichman-ak
 *
 */
public class BaseException extends Exception {

		public BaseException(Exception e) {
			e.printStackTrace();
		}
}
