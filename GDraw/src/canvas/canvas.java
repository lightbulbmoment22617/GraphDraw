package canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.UIManager;

import graphics.ActiveLine;
import graphics.BTBucket;
import graphics.BTHighlight;
import graphics.Bitmap;
import graphics.BoxedText;
import graphics.CenteredLabel;
import graphics.Circle;
import graphics.GraphicsObject;
import graphics.HoverCircle;
import graphics.Label;
import graphics.LabeledLine;
import graphics.Layer;
import graphics.Line;
import graphics.Node;
import graphics.Rectangle;
import graphics.SortBox;
import graphics.VerticalActiveLine;

@SuppressWarnings("serial")
public class canvas extends JPanel implements MouseMotionListener,
		MouseListener {
	public static Font f = new Font(Font.MONOSPACED, 0, 14);
	// Draw list, objects at the end appear in front of preceding objects.
	public static ArrayList<GraphicsObject> objects = new ArrayList<GraphicsObject>();
	private AnimationControl ac;
	Point mousePosition = new Point();

	// Below are wrapped constructors to:
	// Create objects.
	// Add them to drawing list at draw order 0 (Appears over older objects).
	// Set the "parent" property to this.
	// Return them for assignment purposes.

	public canvas() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setAnimationControl(AnimationControl _ac) {
		ac = _ac;
	}

	public ActiveLine createActiveLine(int _x, int _y, float thickness) {
		ActiveLine al = new ActiveLine(_x, _y, thickness);
		al.parent = this;
		objects.add(al);
		return al;
	}

	public VerticalActiveLine createVerticalActiveLine(int _x, int _y, float thickness) {
		VerticalActiveLine val = new VerticalActiveLine(_x, _y, thickness);
		val.parent = this;
		objects.add(val);
		return val;
	}
	

	public Line createLine(int _x, int _y, int _x2, int _y2, float thickness) {
		Line l = new Line(_x, _y, _x2, _y2, thickness);
		l.parent = this;
		objects.add(l);
		return l;
	}

	public LabeledLine createLabeledLine(int _x, int _y, int _x2, int _y2, float thickness, String _text, int _fontSize) {
		LabeledLine l = new LabeledLine(_x, _y, _x2, _y2, thickness, _text, _fontSize);

		l.parent = this;
		objects.add(l);
		return l;
	}

	public CenteredLabel createCenteredLabel(int _x, int _y, String _text, int _fontSize) {
		CenteredLabel l = new CenteredLabel(_x, _y, _text,_fontSize);

		l.parent = this;
		objects.add(l);
		return l;
	}

	public Label createLabel(int _x, int _y, String _text) {
		Label l = new Label(_x, _y, _text);
		l.parent = this;
		objects.add(l);
		return l;
	}

	public Rectangle createRect(int _x, int _y, int _width, int _height) {
		Rectangle r = new Rectangle(_x, _y, _width, _height);
		r.parent = this;
		objects.add(r);
		return r;
	}

	public BoxedText createBoxedText(int _x, int _y, int _width, int _height,
			String _text) {
		BoxedText bT = new BoxedText(_x, _y, _width, _height, _text);
		bT.parent = this;
		bT.getRect().parent = this;
		bT.getLabel().parent = this;
		objects.add(bT);
		return bT;
	}

	public BTBucket createBTBucket(int _x, int _y, int _width, int _height,
			String _text) {
		BTBucket bT = new BTBucket(_x, _y, _width, _height, _text);
		bT.parent = this;
		bT.getRect().parent = this;
		bT.getLabel().parent = this;
		objects.add(bT);
		return bT;
	}

	public BTHighlight createBTHighlight(int _x, int _y, int _width,
			int _height, String _text) {
		BTHighlight bT = new BTHighlight(_x, _y, _width, _height, _text);
		bT.parent = this;
		bT.getRect().parent = this;
		bT.getLabel().parent = this;
		objects.add(bT);
		return bT;
	}

	public SortBox createSortBox(int _x, int _y, int _width, int _height,
			String _text) {
		SortBox bT = new SortBox(_x, _y, _width, _height, _text);
		bT.parent = this;
		bT.getRect().parent = this;
		bT.getLabel().parent = this;
		objects.add(bT);
		return bT;
	}

	public Node createNode(int _x, int _y) {
		Node n = new Node(_x, _y);
		n.parent = this;
		objects.add(n);
		return n;
	}

	public Circle createCircle(int _x, int _y, int _diameter) {
		Circle c = new Circle(_x, _y, _diameter);
		c.parent = this;
		objects.add(c);
		return c;
	}

	public HoverCircle createHoverCircle(int _x, int _y, int _diameter) {
		HoverCircle c = new HoverCircle(_x, _y, _diameter);
		c.parent = this;
		objects.add(c);
		return c;
	}

	public Bitmap createBitmap(String name, int _x, int _y) {
		Bitmap b = new Bitmap(name, _x, _y);
		b.parent = this;
		objects.add(b);
		return b;

	}

	public Layer createLayer() {
		Layer l = new Layer();
		l.parent = this;
		objects.add(l);
		return l;
	}

	@Override
	public void paintComponent(Graphics g) {

		// Routine to draw objects to screen.
		Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D for
											// convenience.
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); // Enable antialiasing.
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight()); // Clear canvas.
		g2d.setColor(new Color(238, 238, 238));
		g2d.fillRect(0,0, this.getWidth(), this.getHeight());
		g.setFont(f);

		for (GraphicsObject go : canvas.objects)
			drawGraphicsObject(g2d, go);
	}

	public static void drawGraphicsObject(Graphics2D g, GraphicsObject go) {
		g.setStroke(new BasicStroke(go.lineThickness));
		go.draw(g);
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
	}

	public void removeObject(GraphicsObject go) { // Remove an object from the
													// canvas.
		objects.remove(go);
	}

	public void removeAll() { // Remove all objects from the canvas.
		objects = new ArrayList<GraphicsObject>();
	}

	public GraphicsObject getObjectByName(String name) {
		for (GraphicsObject go : canvas.objects)
			if (go.name != null)
				if (go.name.equals(name))
					return go;
		return null;
	}

	public void updateCanvas() {
		revalidate();
		repaint();
	}

	public void tick() {
		for (GraphicsObject go : canvas.objects)
			tickObject(go);
	}

	public static void tickObject(GraphicsObject go) {
		if (go.tween != null) {
			go.setAlpha(go.tween.getAlpha());
			Point newPos = new Point(0, 0);
			if (go.tween.tweenMode == 0)
				newPos = go.tween.getNextPoint();
			else if (go.tween.tweenMode == 1)
				newPos = go.tween.getEasedPoint();
			go.x = newPos.x;
			go.y = newPos.y;
			go.refresh();
		}
		go.tick();
	}

	public Point getLocation(GraphicsObject go) { // Used to provide the
													// absolute position of a
													// child.
		Point p = getLocationOnScreen(); // Get the absolute position of this.
		p.translate(go.x, go.y); // Offset it by the child position.
		return p; // Return the position.
	}

	// Listeners for:
	// Mouse movement, for whatever interactivity we wish to implement.
	// Mouse dragging, requirement of interface.
	// FPS ticker, listed as "actionPerformed", proceeds animation at 60fps.

	@Override
	public void mouseMoved(MouseEvent e) { // Whenever the mouse is moved...
		mousePosition.x = e.getLocationOnScreen().x - getLocationOnScreen().x;
		mousePosition.y = e.getLocationOnScreen().y - getLocationOnScreen().y;
		for (GraphicsObject go : canvas.objects) { // For each GraphicsObject...
			if(go.mouseEnabled)
			go.handleMouse(e.getXOnScreen(), e.getYOnScreen()); // Pass the
																// coordinates
																// to the
																// object.
		}
		ac.handleMouseMove(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	} // Required for MouseMotionListener.

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (GraphicsObject go : canvas.objects)
			// For each GraphicsObject...
			if(go.mouseEnabled)
			go.handleLeftMouse(e.getXOnScreen(), e.getYOnScreen(), true);
		if (e.getButton() == 1) // Left click
			ac.handleMouse(e.getX(), e.getY());
		else
			// Middle/Right
			ac.handleAltMouse(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (GraphicsObject go : canvas.objects)
			// For each GraphicsObject...
			if(go.mouseEnabled)
			go.handleLeftMouse(e.getXOnScreen(), e.getYOnScreen(), false);

	}

}