package com.screentest.in.flippinglistview;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class FlipAnimation extends Animation {

	private final float fromDegrees;
	private final float toDegrees;
	private final float centerX;
	private final float centerY;
	private float depthZ;
	private boolean reverse;
	private Camera camera;

	public FlipAnimation(float fromDegrees, float toDegrees, float centerX,
			float centerY, float depthZ, boolean reverse) {
		this.fromDegrees = fromDegrees;
		this.toDegrees = toDegrees;
		this.centerX = centerX;
		this.centerY = centerY;
		this.depthZ = depthZ;
		this.reverse = reverse;
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		camera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		final float fromDegrees = this.fromDegrees;
		float degrees = fromDegrees + ((toDegrees - fromDegrees) * interpolatedTime);
		final Camera camera = this.camera;
		final float centerX = this.centerX;
		final float centerY = this.centerY;

		final Matrix matrix = t.getMatrix();
		camera.save();

		if (reverse) {
			camera.translate(0.0f, 0.0f, depthZ * (1.0f - interpolatedTime));
		} else {
			camera.translate(0.0f, 0.0f, depthZ * (1.0f - interpolatedTime));
		}

		camera.rotateX(degrees);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}
