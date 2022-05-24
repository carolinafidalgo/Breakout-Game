package breakout;

/**
 * @author Tiago Pinto, Carolina Fidalgo
 * Each instance of this class represents a block at a point in time
 * @immutable
 *
 * @invar Both topLeft coordinates must be not negative
 * 		| getTopLeft().getX() >= 0 && getTopLeft().getY() >= 0
 * @invar Both bottomRight coordinates must be not negative
 * 		| getBottomRight().getX() >= 0 && getBottomRight().getY() >= 0
 *
 */

public class BlockState {
	
	/**
	 * @invar Both topLeft coordinates must be not negative
	 * 		| topLeft.getX() >= 0 && topLeft.getY() >= 0
	 * @invar Both bottomRight coordinates must be not negative
	 * 		| bottomRight.getX() >= 0 && bottomRight.getY() >= 0
	 */
	private final Point topLeft;
	private final Point bottomRight;
	
	/**
	 * @pre Argument {@code topLeft} coordinates are positive
	 * 		 | topLeft.getX() >= 0 && topLeft.getY() >= 0
	 * @pre Argument {@code bottomRight} coordinates are positive
	 * 		 | bottomRight.getX() >= 0 && bottomRight.getY() >= 0
	 * @post |getTopLeft() == topLeft
	 * @post |getBottomRight() == bottomRight
	 */
	public BlockState(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	/** Returns this instance's topLeft coordinates **/
	public Point getTopLeft() { return topLeft; }
	
	/** Returns this instance's bottomRight coordinates **/
	public Point getBottomRight(){ return bottomRight; }
	
}
