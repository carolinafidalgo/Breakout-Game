package breakout;

/**
 * @author Tiago Pinto, Carolina Fidalgo
 * Each instance of this class represents a ball at a point in time
 * @immutable
 * @invar The diameter must be positive
 * 		| 0 < getDiameter()
 * @invar Both center's coordinates must not be negative
 * 		| getCenter().getX() >= 0 && getCenter().getY() >= 0
 * @invar The velocity must not be null
 * 		| null != getVelocity()
 *
 */
public class BallState {	
	
	/**
	* @invar The diameter must be positive
	* 		| 0 < getDiameter()
	* @invar Both center's coordinates must not be negative
	* 		| center.getX() >= 0 && center.getY() >= 0
	* @invar The velocity must not be null
	* 		| null != getVelocity()
	*/
	private final Point center;
	private final Vector velocity;
	private final int diameter;
	
	
	
	/**
	 * @pre Argument {@code diameter} is positive
	 * 		 | 0 < getDiameter()
	 * @pre Argument {@code center} coordinates are positive
	 * 		 | center.getX() >= 0 && center.getY() >= 0
	 * @pre Argument {@code velocity} is not {@code null}
	 * 		 | null != getVelocity()
	 * @post |getCenter() == center
	 * @post |getVelocity() == velocity 
	 * @post |getDiameter() == diameter 
	 */
	public BallState(Point center, Vector velocity, int diameter) {
		this.center = center;
		this.velocity = velocity;
		this.diameter = diameter;
	}
	
	/** Returns this instance's center coordinates **/
	public Point getCenter() { return center; }
	
	/** Returns this instance's velocity vector **/
	public Vector getVelocity() { return velocity; }
	
	/** Returns this instance's diameter **/
	public int getDiameter() { return diameter; }
}