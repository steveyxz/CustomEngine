package engine.core.tools.maths.vectors;

import java.util.ArrayList;

public class MatrixStack {
	private ArrayList<Matrix4f> stack = new ArrayList<>();
	private int currIdx;

	public MatrixStack() {
		clear();
	}

	public MatrixStack clear() {
		stack.clear();
		stack.add(new Matrix4f());
		currIdx = 0;
		return this;
	}

	public Matrix4f getTop() {
		return stack.get(currIdx);
	}

	public MatrixStack setTop(Matrix4f m) {
		stack.get(currIdx).set(m);
		return this;
	}

	public MatrixStack pushMatrix() {
		stack.add(new Matrix4f(getTop()));
		currIdx++;
		return this;
	}

	public MatrixStack popMatrix() {
		if (currIdx == 0) {
			throw new IllegalStateException("Already at the bottom of the stack.");
		}

		stack.remove(currIdx);
		currIdx--;
		return this;
	}
}
