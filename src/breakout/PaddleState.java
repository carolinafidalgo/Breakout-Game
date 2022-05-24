package breakout;

/**
 * @author Tiago Pinto, Carolina Fidalgo
 * Each instance of this class represents a paddle state at a point in time
 * @immutable
 * @invar Both center's coordinates must not be negative
 * 		| getCenter().getX() >= 0 && getCenter().getY() >= 0
 * @invar Both size's coordinates must not be negative
 * 		| getSize().getX() >= 0 && getSize().getY() >= 0
 */
public class PaddleState {

	/**
	 * @invar Both center's coordinates must not be negative
	 * 		| center.getX() >= 0 && center.getY() >= 0
	 * @invar Both size's coordinates must not be negative
	 * 		| size.getX() >= 0 && size.getY() >= 0
	 */
	private final Point center;
	private final Vector size;
	
	/**
	 * @pre Argument {@code center} coordinates are positive
	 * 		 | center.getX() >= 0 && center.getY() >= 0
	 * @pre Argument {@code size} coordinates are positive
	 * 		 | size.getX() >= 0 && size.getY() >= 0
	 * @post |getCenter() == center
	 * @post |getSize() == size 
	 */
	public PaddleState(Point center, Vector size) {
		this.center = center;
		this.size = size;
	}
	
	/** Returns this instance's center coordinates **/
	public Point getCenter() { return center; }
	
	/** Returns this instance's size coordinates **/
	public Vector getSize() { return size; }
}
