package breakout;
import java.lang.Math;

/**
 * @author Tiago Pinto, Carolina Fidalgo
 * Each instance of this class represents a game state at a point in time
 * @invar The balls array must not be null
 * 		| null != getBalls()
 * @invar The blocks array must not be null
 * 		| null != getBlocks()
 * @invar The paddle must not be null
 * 		| null != getPaddle()
 * @invar Both bottomRight's coordinates must not be negative
 * 		| getBottomRight().getX() >= 0 && getBottomRight().getY() >= 0
 */
public class BreakoutState {
	
	/**
	 * @invar The balls array must not be null
	 * 		| null != getBalls()
	 * @invar The blocks array must not be null
	 * 		| null != getBlocks()
	 * @invar The paddle must not be null
	 * 		| null != getPaddle()
	 * @invar Both bottomRight's coordinates must not be negative
	 * 		| bottomRight.getX() >= 0 && bottomRight.getY() >= 0
	 */

	private BallState[] balls;
	private BlockState[] blocks;
	private Point bottomRight;
	private PaddleState paddle;
	
	//constants
	private static int no_hit = 0;
	private static int top = 1;
	private static int bot = 2;
	private static int left = 3;
	private static int right = 4;
	private static boolean in = true;
	private static boolean out = false;
	
	/**
	 * @throws IllegalArgumentException if the given balls array is null
	 * 		 | getBalls() == null
	 * @throws IllegalArgumentException if the given blocks array is null
	 * 		 | getBlocks() == null
	 * @throws IllegalArgumentException if the given paddle is null
	 * 		 | getPaddle() == null
	 * @throws IllegalArgumentException if the given bottomRight is null
	 * 		 | getBottomRight() == null
	 * @throws IllegalArgumentException if any of the the given bottomRight coordinates is negative
	 * 		 | (bottomRight.getX() > 0 && bottomRight.getY() > 0)
	 * @post | getBalls() == balls
	 * @post | getBlocks() == blocks 
	 * @post | getPaddle() == paddle 
	 * @post | getBottomRight() == bottomRight
	 */
	
	public BreakoutState(BallState[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		if(balls == null || blocks == null || bottomRight == null || paddle == null) 
			throw new IllegalArgumentException("Invalid Arguments!");
		
		if((bottomRight.getX() <= 0 && bottomRight.getY() <= 0))
			throw new IllegalArgumentException("Invalid Arguments!");
		
		for(BlockState block: blocks) {
			if(block.getBottomRight().getX() > bottomRight.getX() || block.getBottomRight().getY() > bottomRight.getY() ||
					block.getTopLeft().getX() < 0 || block.getTopLeft().getY() < 0)
			throw new IllegalArgumentException("Invalid Arguments!");
		}
		
		for(BallState ball: balls) {
			if(ball.getCenter().getX() < 0 || ball.getCenter().getY() < 0 ||
					ball.getCenter().getX() > bottomRight.getX() || ball.getCenter().getY() > bottomRight.getY())
			throw new IllegalArgumentException("Invalid Arguments!");
		}
		
		this.balls = balls;
		this.blocks = blocks;
		this.bottomRight = bottomRight;
		this.paddle = paddle;
	}
	
	/** Returns this instance's balls array **/
	public BallState[] getBalls() {
		return balls;
	}

	/** Returns this instance's blocks array **/
	public BlockState[] getBlocks() {
		return blocks;
	}
	
	/** Returns this instance's paddle **/
	public PaddleState getPaddle() {
		return paddle;
	}
	
	/** Returns the bottom right point of the game map **/
	public Point getBottomRight() {
		return bottomRight;
	}
	
	/**
	 * Iterates over every ball in the balls array, moves each one a step and checks if it hits any surface (walls, paddle ou blocks) 
	 * making it bounce of
	 */

	public void tick(int paddleDir) {
		int i = 0, diameter;
		
		Point final_pos;
		

		for(BallState ball : balls) {
			Vector velocity = ball.getVelocity();
			final_pos = ball.getCenter().plus(velocity);
			diameter = ball.getDiameter();
			
			BallState next_state = new BallState(final_pos, velocity, diameter);
			
			int hit_wall;
			 
			
			hit_wall = checkRectangle(bottomRight, new Point(0,0), ball, out);
		
			switch(hit_wall) {
				case 0: //no hit
					break;
				case 1: //top
					next_state = bounce(next_state, top, false, paddleDir);
					balls[i] = next_state;
					break;
				case 2: //bot
					balls = remove_ball_from_array(balls, i);
					continue;
				case 3: //left
					next_state = bounce(next_state, left, false, paddleDir);
					balls[i] = next_state;
					break;
				case 4: //right
					next_state = bounce(next_state, right, false, paddleDir);
					balls[i] = next_state;
					break;
				default:
					break;
					
			}
			
			int hit_paddle = checkPaddle(next_state);
			
			switch(hit_paddle) {
				case 0: //no_hit
					balls[i] = next_state;
					break;
				case 1: //top
					next_state = bounce(next_state, top, true, paddleDir);
					balls[i] = next_state;
					break;
				case 2: //bot
					next_state = bounce(next_state, bot, true, paddleDir );
					balls[i] = next_state;
					break;
				case 3: //left
					next_state = bounce(next_state, left, true, paddleDir);
					balls[i] = next_state;
					break;
				case 4: //right
					next_state = bounce(next_state, right, true, paddleDir);
					balls[i] = next_state;
					break;
				default:
					balls[i] = next_state;
					break;
					
			}
			
			int hit_block = checkBricks(next_state);
			
			switch(hit_block) {
				case 0: //no_hit
					balls[i] = next_state;
					break;
				case 1: //top
					next_state = bounce(next_state, top, false, paddleDir);
					balls[i] = next_state;
					break;
				case 2: //bot
					next_state = bounce(next_state, bot, false, paddleDir);
					balls[i] = next_state;
					break;
				case 3: //left
					next_state = bounce(next_state, left, false, paddleDir);
					balls[i] = next_state;
					break;
				case 4: //right
					next_state = bounce(next_state, right, false, paddleDir);
					balls[i] = next_state;
					break;
				default:
					balls[i] = next_state;
					break;
				
			}
			
				
		}
			i++;
	}
	
	/**
	 * Moves the paddle one step to the right
	 */
	public void movePaddleRight() {
		int lat_distance = paddle.getSize().getX();
		int x, y;
		
		Point center = paddle.getCenter();
		if(center.plus(new Vector(10,0)).plus(new Vector(lat_distance,0)).getX() > bottomRight.getX()) {
			x = bottomRight.getX() - lat_distance;
			y = paddle.getCenter().getY();
			
			paddle = new PaddleState(new Point(x,y), paddle.getSize());
			return;
		}
		else {
			x = center.plus(new Vector(10,0)).getX();
			y = paddle.getCenter().getY();
			
			paddle = new PaddleState(new Point(x,y), paddle.getSize());
			return;
		}
		
	}
	
	/**
	 * Moves the paddle one step to the left
	 */
	public void movePaddleLeft() {
		int lat_distance = paddle.getSize().getX();
		int x, y;
		Vector step = new Vector(-10,0);
		
		Point center = paddle.getCenter();
		if(center.plus(step).plus(new Vector(-lat_distance,0)).getX() < 0) {
			x = lat_distance;
			y = paddle.getCenter().getY();
			
			paddle = new PaddleState(new Point(x,y), paddle.getSize());
			return;
		}
		else {
			x = center.plus(step).getX();
			y = paddle.getCenter().getY();
			
			paddle = new PaddleState(new Point(x,y), paddle.getSize());
			return;
		}
	}
	
	/**
	 * Checks if the player won the game
	 * @post
	 * 		The result is {@code true} if there are no more blocks but there is a ball still and {@code false} otherwise
	 * 		| result == (
	 * 		| 	getBlocks().length == 0 && getBalls().length != 0
	 * 		| )
	 */
	public boolean isWon() {
		if (blocks.length == 0 && balls.length != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the player lost the game
	 * @post
	 * 		The result is {@code true} if there are still blocks but there is no ball anymore and {@code false} otherwise
	 * 		| result == (
	 * 		| 	getBlocks().length != 0 && getBalls().length == 0
	 * 		| )
	 */
	public boolean isDead() {
		if (blocks.length != 0 && balls.length == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Iterates over the blocks to check if any was hit by a given ball
	 * 
	 */
	private int checkBricks(BallState ball) {
				
		int hit, i=0;
		
		for(BlockState block: blocks) {
			if((hit = checkRectangle(block.getBottomRight(), block.getTopLeft(), ball, in)) != no_hit) {
				blocks = remove_block_from_array(blocks, i);
				return hit;
			}
			i++;
		}
		return no_hit;
	
		
	}
	
	/**
	 * Checks if the paddle was hit by a given ball
	 * 
	 */
	private int checkPaddle(BallState ball) {
		
		Point br = paddle.getCenter().plus(paddle.getSize());
		Point tl = paddle.getCenter().minus(paddle.getSize());
		
		int hit = checkRectangle(br, tl, ball, in);
		return hit;
		
	}
	
	/*
	 * Changes the velocity of the ball according to the hit 
	 */
	private BallState bounce(BallState ball, int dir, boolean hit_paddle, int paddleDir) {
		
		Vector new_velocity;
		Point new_center;
		Vector velocity = ball.getVelocity();
		Vector paddleVec = new Vector(0,10);
		Point center = ball.getCenter();
		int diameter = ball.getDiameter(); 
		
		
		paddleVec = paddleVec.scaled(paddleDir).scaledDiv(5);
		
		if(dir == top) {
			if(!checkAngle(velocity, Vector.DOWN))
				return ball;
			
			new_velocity = velocity.mirrorOver(Vector.DOWN);
			if(hit_paddle) {
				new_velocity = new_velocity.plus(paddleVec);
			}
			new_center = center.plus(new_velocity);
			return new BallState(new_center, new_velocity, diameter);	
		}
		else if(dir == bot) {
			if(!checkAngle(velocity, Vector.UP))
				return ball;
			new_velocity = velocity.mirrorOver(Vector.UP);
			if(hit_paddle) {
				new_velocity = new_velocity.plus(paddleVec);
			}
			new_center = center.plus(new_velocity);
			return new BallState(new_center, new_velocity, diameter);	
		}
		else if(dir == left) {
			if(!checkAngle(velocity, Vector.RIGHT))
				return ball;
			new_velocity = velocity.mirrorOver(Vector.RIGHT);
			if(hit_paddle) {
				new_velocity = new_velocity.plus(paddleVec);
			}
			new_center = center.plus(new_velocity);
			return new BallState(new_center, new_velocity, diameter);	
		}
		else if(dir == right) {
			if(!checkAngle(velocity, Vector.LEFT))
				return ball;
			new_velocity = velocity.mirrorOver(Vector.LEFT);
			if(hit_paddle) {
				new_velocity = new_velocity.plus(paddleVec);
			}
			new_center = center.plus(new_velocity);
			return new BallState(new_center, new_velocity, diameter);	
		}
		return ball;
	}
	
	/**
	 * Removes a block from the array after being hit
	 */
	private BlockState[] remove_block_from_array(BlockState[] array, int index) {
 
        // check if the array is empty or index is out of bounds 
        if (array == null || index < 0 || index >= array.length) { 
            System.out.println("No removal operation can be performed!!");
        } 
               
        // create an array to hold elements after deletion
        BlockState[] copyArray = new BlockState[array.length - 1]; 
 
        // copy elements from original array from beginning till index into copyArray
        System.arraycopy(array, 0, copyArray, 0, index); 
 
        // copy elements from original array from index+1 till end into copyArray
        System.arraycopy(array, index + 1, copyArray, index, array.length - index - 1); 
 
        return copyArray;
	}
	
	/**
	 * Removes a ball from the array after falling to the bottom wall
	 */
	private BallState[] remove_ball_from_array(BallState[] array, int index) {
		 
        // check if the array is empty or index is out of bounds 
        if (array == null || index < 0 || index >= array.length) { 
            System.out.println("No removal operation can be performed!!");
        } 
        
        // create an array to hold elements after deletion
        BallState[] copyArray = new BallState[array.length - 1]; 
 
        // copy elements from original array from beginning till index into copyArray
        System.arraycopy(array, 0, copyArray, 0, index); 
 
        // copy elements from original array from index+1 till end into copyArray
        System.arraycopy(array, index + 1, copyArray, index, array.length - index - 1); 
 
        return copyArray;
	}
	
	/**
	 * Returns a constant integer according to which surface the ball hit
	 * 
	 */
	private int checkRectangle(Point br, Point tl, BallState ball, boolean pos) {
		Vector velocity = ball.getVelocity();
		Point center = ball.getCenter();
		int diameter = ball.getDiameter();
		int dist_top, dist_bot, dist_left, dist_right;
		
		Point impact = center.plus(velocity.scaledDiv(diameter/2));
				
		
		if(!pos) {
			if(impact.getX() < 0 && velocity.getX() < 0) {
				return left;
			}
			else if(impact.getX() > br.getX() && velocity.getX() > 0) {
				return right;
			}
			else if(impact.getY() > br.getY() && velocity.getY() > 0) {
				return bot;
			}
			else if(impact.getY() < 0 && velocity.getY() < 0) {
				return top;
			}
			else {
				return no_hit;
			}
			
		}
		if(impact.getX() <= br.getX() && impact.getX() >= tl.getX() &&
				impact.getY() <= br.getY() && impact.getY() >= tl.getY() && pos) {
			
			dist_top = Math.abs(impact.getY() - tl.getY());
			dist_bot = Math.abs(impact.getY() - br.getY());
			dist_left = Math.abs(impact.getX() - tl.getX());
			dist_right = Math.abs(impact.getX() - br.getX());
				
			if(dist_top <= dist_left && dist_top <= dist_right && dist_top <= dist_bot)
				return top;
			
			if(dist_bot <= dist_left && dist_bot <= dist_right && dist_bot <= dist_top)
				return bot;
			
			if(dist_left <= dist_right && dist_left <= dist_top && dist_left <= dist_bot)
				return left;
			
			if(dist_right <= dist_left && dist_right <= dist_top && dist_right <= dist_bot)
				return right;
		}
		return no_hit;
	}
	
	/**
	 * 
	 * Checks if the ball hit the surface at a sharp angle
	 */
	
	private boolean checkAngle(Vector vel, Vector normal) {
		
		int alpha = (int) Math.acos(vel.product(normal) / ( vel.getSquareLength() * normal.getSquareLength()));
		
		if(alpha >= Math.PI/2)
			return false;
		else 
			return true;
	}
}

