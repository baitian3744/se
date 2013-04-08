package com.uestc.se.applet;

import static org.lwjgl.opengl.ARBTransposeMatrix.glLoadTransposeMatrixARB;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NORMALIZE;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.FloatBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.uestc.se.datashare.substar.SubstarModel;
import com.uestc.se.util.ResourceLoader;

public class ModelApplet extends Applet{
	
	private static String location = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1240895794231044410L;

	/** The Canvas where the LWJGL Display is added */
	Canvas display_parent;

	/** Thread which runs the main game loop */
	Thread gameThread;

	/** is the game loop running */
	boolean running;

	/** variables used to rotate the view */
	private float view_rotx	= 20.0f;
	private float view_roty	= 30.0f;
	private float view_rotz;

	private float angle;
	private boolean enableRotate = true;

	boolean keyDown;

	private int prevMouseX, prevMouseY;
	private boolean mouseButtonDown;
	
	private float zoom = 1.0f;
	int dWheel = 0;
	
	private SubstarModel substarModel;
	private boolean isInitialized = false;
	
	private synchronized void setlocation(final String arg){
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				location = arg;
				return null;
			}
		});
	}
	
	private synchronized boolean isDataAvailable(){
		return location==null ? false : true;
	}
	
	private boolean isInitialized(){
		return this.isInitialized;
	}
	
//	private ModelActionMacro macroCommand = new ModelActionMacro();

	/**
	 * Once the Canvas is created its add notify method will call this method to
	 * start the LWJGL Display and game loop in another thread.
	 */
	public void startLWJGL() {
		gameThread = new Thread() {
			public void run() {
				running = true;
				try {
					Display.setParent(display_parent);
					Display.create();
					
					initGL();

				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				gameLoop();
			}
		};
		gameThread.start();
	}


	/**
	 * Tell game loop to stop running, after which the LWJGL Display will be destoryed.
	 * The main thread will wait for the Display.destroy() to complete
	 */
	private void stopLWJGL() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start() {

	}

	public void stop() {

	}

	/**
	 * Applet Destroy method will remove the canvas, before canvas is destroyed it will notify
	 * stopLWJGL() to stop main game loop and to destroy the Display
	 */
	public void destroy() {
		remove(display_parent);
		super.destroy();
		System.out.println("Clear up");
	}

	/**
	 * initialise applet by adding a canvas to it, this canvas will start the LWJGL Display and game loop
	 * in another thread. It will also stop the game loop and destroy the display on canvas removal when
	 * applet is destroyed.
	 */
	public void init() {
		
		// Display related
		setLayout(new BorderLayout());	
		
		try {
				display_parent = new Canvas() {
					private static final long serialVersionUID = -5044292896282488845L;
					public void addNotify() {
						super.addNotify();
						startLWJGL();
					}
					public void removeNotify() {
						stopLWJGL();
						super.removeNotify();
					}
				};
			display_parent.setSize(getWidth(),getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		} catch (Exception e) {
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
		
	}
	
	/** This method load texture for you, please make sure that the picture is in the subfolder of src
	 * 	or you may fail in loading it!
	 * @throws IOException
	 */
	private void loadTexture() throws IOException{
			// load texture from PNG file
			// argument "true": load the picture upside down to make the texture "right"
			// 		or you can set earthSphere.setOrientation(true) to make the texture "right"
//			this.earthTexture = TextureLoader.getTexture("BMP", ResourceLoader.getResourceAsStream("res/img/earth.bmp"),true);
//			this.moonTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/img/moon.png"),true);
	}

	public void gameLoop() {
		long startTime = System.currentTimeMillis() + 5000;
		long fps = 0;
		
		while(running) {
			if(this.enableRotate == true){
				angle += 0.01f;
				if(this.angle >= 360.0f){
					this.angle = 0.0f;
				}
			}
			if(isDataAvailable()){
				if(!isInitialized()){

					if(ResourceLoader.resourceExists(location)){
						printErr("file exist, location = " + location);
						this.isInitialized = true;
						
						this.substarModel = SubstarModel.newInstance(location);
						if(substarModel == null){
							printErr("failed to create substarModel");
						}
						
//						this.isInitialized = true;
//						this.setlocation(null);
//				        
////						try {
////							substarModel = new SubstarModel(this.location);
////							substarModel.init();
////							this.isInitialized = true;
////							this.iAmReady();
////						} catch (Exception e) {
////							printErr("Unable to create SubstarModel or load data");
////						}
//					}else{
//						this.location = null;
//						this.isInitialized = false;
//						printErr("file not existed");
//						printErr("location = " + this.location);
					}
				}
			}
			
			drawLoop();

			Display.update();

			if (startTime > System.currentTimeMillis()) {
				fps++;
			} else {
				long timeUsed = 5000 + (startTime - System.currentTimeMillis());
				startTime = System.currentTimeMillis() + 5000;
				System.out.println(fps + " frames 2 in " + timeUsed / 1000f + " seconds = "
						+ (fps / (timeUsed / 1000f)));
				fps = 0;
			}

			if (Mouse.isButtonDown(0)) {
				if (!mouseButtonDown) {
					prevMouseX = Mouse.getX();
					prevMouseY= Mouse.getY();
				}
				mouseButtonDown = true;
			}
			else {
				mouseButtonDown = false;
			}

			if (mouseButtonDown) {
				int x = Mouse.getX();
			    int y = Mouse.getY();

				float thetaY = 360.0f * ( (float)(x-prevMouseX)/(float)display_parent.getWidth());
			    float thetaX = 360.0f * ( (float)(prevMouseY-y)/(float)display_parent.getHeight());

			    prevMouseX = x;
			    prevMouseY = y;

			    view_rotx += thetaX;
			    view_roty += thetaY;			    
			}

			// F Key Pressed (i.e. released)
			if (keyDown && !Keyboard.isKeyDown(Keyboard.KEY_F)) {
				keyDown = false;

				try {
					if (Display.isFullscreen()) {
						Display.setFullscreen(false);
					}
					else {
						Display.setFullscreen(true);
					}
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
			
			// Check the scroll
			this.dWheel = Mouse.getDWheel();
			/* Zoom: 0.3 <--> 5.0 */
			if((this.dWheel > 0) && (this.zoom < 5.0f)){
				this.zoom += 0.1f;
				this.dWheel = 0;
			}
			else if((this.dWheel < 0) && (this.zoom > 0.3f)){
				this.zoom -= 0.1f;
				this.dWheel = 0;
			}
		}

		Display.destroy();
	}

	public void drawLoop() {
//		this.macroCommand.clear();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glPushMatrix();
		
//		this.macroCommand.add(new ModelActionRotate(this.view_rotx, this.view_roty, this.view_rotz));
//		this.macroCommand.add(new ModelActionZoom(this.zoom));
//		this.macroCommand.add(new OrbActionRotate(this.earth, this.angle));
//		this.macroCommand.execute();
		if(isInitialized()){
			this.substarModel.render();
		}
		
		glPopMatrix();
	}

	protected void initGL() {
		try {
			// setup ogl
			FloatBuffer pos = BufferUtils.createFloatBuffer(4).put(new float[] { 5.0f, 5.0f, 10.0f, 0.0f});		// The position of light0
			FloatBuffer red = BufferUtils.createFloatBuffer(4).put(new float[] { 0.8f, 0.1f, 0.0f, 1.0f});
			FloatBuffer green = BufferUtils.createFloatBuffer(4).put(new float[] { 0.0f, 0.8f, 0.2f, 1.0f});
			FloatBuffer blue = BufferUtils.createFloatBuffer(4).put(new float[] { 0.2f, 0.2f, 1.0f, 1.0f});

			pos.flip();
			red.flip();
			green.flip();
			blue.flip();

			/* set light */
//			glLight(GL_LIGHT0, GL_POSITION, pos);
//			glEnable(GL_CULL_FACE);
//			glEnable(GL_LIGHTING);
//			glEnable(GL_LIGHT0);
			glEnable(GL_DEPTH_TEST);
			
			GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			glEnable(GL_NORMALIZE);
			glMatrixMode(GL_PROJECTION);

			/* display the info of the computer */
			System.err.println("LWJGL: " + Sys.getVersion() + " / " + LWJGLUtil.getPlatformName());
			System.err.println("GL_VENDOR: " + glGetString(GL_VENDOR));
			System.err.println("GL_RENDERER: " + glGetString(GL_RENDERER));
			System.err.println("GL_VERSION: " + glGetString(GL_VERSION));
			System.err.println();
			System.err.println("glLoadTransposeMatrixfARB() supported: " + GLContext.getCapabilities().GL_ARB_transpose_matrix);

			if (!GLContext.getCapabilities().GL_ARB_transpose_matrix) {
				// --- not using extensions
				glLoadIdentity();
			} else {
				// --- using extensions
				final FloatBuffer identityTranspose = BufferUtils.createFloatBuffer(16).put(
						new float[] { 1, 0, 0, 0, 0, 1, 0, 0,
							0, 0, 1, 0, 0, 0, 0, 1});
				identityTranspose.flip();
				glLoadTransposeMatrixARB(identityTranspose);
			}
			float h = (float) display_parent.getHeight() / (float) display_parent.getWidth();
			glFrustum(0.0f, 400/2, 0, h*400/2, 5.0f, 40.1f);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			glTranslatef(0.0f, 0.0f, -10.0f);
		} catch (Exception e) {
			System.err.println(e);
			running = false;
		}
	}
	
	public void setLocation(String location){
		this.setlocation(location);
//		URL url;
//		try {
//			url = new URL(location);
//			this.hostString = url.getHost();
//			this.portString = Integer.toString(url.getPort());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		webappHead = "http://" + hostString
//				+ "/"
//				+ ":"
//				+ portString
//				+ "//"
//				+ webappString;
	}
	
	private void iAmReady(){
		System.out.println("I am ready");
	}
	
	private void printErr(String errMsg){
		System.out.println("Err@SubstarData: " + errMsg);
	}
}


//for(int i = 0; i < 100; i++){
//ResourceLoader.resourceExists(this.location);
//printErr("after check resource, file exist, slocation = " + this.location);
//}
//this.isInitialized = true;


//URL url;
//URLConnection urlCon;
//BufferedReader bufReader;
//try {
//	url = new URL(location);
//	urlCon = url.openConnection();
//	urlCon.setReadTimeout(1000);
//	bufReader = new BufferedReader(
//			new InputStreamReader(
//					urlCon.getInputStream())
//			);
//	String inputLine;
//	try {
//		while ((inputLine = bufReader.readLine()) != null) 
//		    System.out.println(inputLine);
//		bufReader.close();
//	} catch (IOException e) {
//		e.printStackTrace();
//		this.isInitialized = true;
//	}
//} catch (Exception e) {
//	e.printStackTrace();
//	System.exit(0);
//}