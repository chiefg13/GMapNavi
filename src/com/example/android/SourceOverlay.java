package com.example.android;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class SourceOverlay extends Overlay {

	private GeoPoint sourcePoint;
	private float accuracy;

	public SourceOverlay() {
		super();
	}

	public void setSource(GeoPoint geoPoint, float accuracy) {
		sourcePoint = geoPoint;
		this.accuracy = accuracy;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false);
		Projection projection = mapView.getProjection();
		Point center = new Point();

		int radius = (int) (projection.metersToEquatorPixels(accuracy));
		projection.toPixels(sourcePoint, center);

		Paint accuracyPaint = new Paint();
		accuracyPaint.setAntiAlias(true);
		accuracyPaint.setStrokeWidth(2.0f);
		accuracyPaint.setColor(0xff6666ff);
		accuracyPaint.setStyle(Style.STROKE);

		canvas.drawCircle(center.x, center.y, radius, accuracyPaint);

		accuracyPaint.setColor(0x096666ff);
		accuracyPaint.setStyle(Style.FILL);
		canvas.drawCircle(center.x, center.y, radius, accuracyPaint);

	}

}
