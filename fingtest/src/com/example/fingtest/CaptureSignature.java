package com.example.fingtest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class CaptureSignature extends Activity {
	signature mSignature;
	Paint paint;
	LinearLayout mContent;
	Button clear, save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capturesignature);

		save = (Button) findViewById(R.id.save);
		save.setEnabled(false);
		clear = (Button) findViewById(R.id.clear);
		mContent = (LinearLayout) findViewById(R.id.mysignature);

		mSignature = new signature(this, null);
		mContent.addView(mSignature);

		save.setOnClickListener(onButtonClick);
		clear.setOnClickListener(onButtonClick);
	}

	Button.OnClickListener onButtonClick = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == clear) {
				mSignature.clear();
			} else if (v == save) {
				mSignature.save();
			}
		}
	};

	public class signature extends View {
		static final float STROKE_WIDTH = 10f;
		static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		Paint paint = new Paint();
		Path path = new Path();

		float lastTouchX;
		float lastTouchY;
		int listNum = 0;
		final RectF dirtyRect = new RectF();
		int HaveManyLine = 0;
		float a = 0, b = 0, c = 0, d = 0;
		float DriHandleA = 0, DriHandleB = 0, DriHandleC = 0, DriHandleD = 0;

		private Canvas myCanvas;

		Boolean Runing = true;// 判斷是否摸線
		Boolean RuningClc1 = true;// 判斷是否摸元
		Boolean RuningClc2 = true;// 判斷是否摸元
		int reSetNum = 0;
		Bitmap circle_44, delete_44;

		// int d2=100;
		// ArrayList fistX2 = new ArrayList();
		// ArrayList<Integer> fistX2 = new ArrayList<Integer>();
		ArrayList<Integer> fistX = new ArrayList<Integer>();
		ArrayList<Integer> fistY = new ArrayList<Integer>();

		ArrayList<Integer> listX = new ArrayList<Integer>();
		ArrayList<Integer> listY = new ArrayList<Integer>();

		public signature(Context context, AttributeSet attrs) {
			super(context, attrs);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(STROKE_WIDTH);
		}

		public void clear() {
			path.reset();
			fistX.clear();
			fistY.clear();
			listX.clear();
			listY.clear();
			invalidate();
			save.setEnabled(false);
		}

		public void save() {
			Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),
					mContent.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(returnedBitmap);
			Drawable bgDrawable = mContent.getBackground();
			if (bgDrawable != null)
				bgDrawable.draw(canvas);
			else
				canvas.drawColor(Color.WHITE);
			mContent.draw(canvas);

			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			returnedBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
			Intent intent = new Intent();
			intent.putExtra("byteArray", bs.toByteArray());
			setResult(1, intent);
			finish();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			String s;
			// canvas.drawLine(100, 400, 500, 40, paint);// 画线
			this.myCanvas = canvas;
			for (int i = 0; i < fistX.size(); i++) {
				if (fistX.get(i).equals(listX.get(i))
						&& fistY.get(i).equals(listY.get(i))) {
					// 我不畫！
					s = Integer.toString(i);

					// Log.i("我不畫！", s);
				} else {
					// 我畫
					drawAL(fistX.get(i), fistY.get(i), listX.get(i),
							listY.get(i));

				}

			}
			// canvas.drawLine(fistX.get(i), fistY.get(i),c,d, paint);// 画线

			// canvas.drawLine(fistX.get(i), fistY.get(i), listX.get(i),
			// listY.get(i), paint);// 画线
			// canvas.drawLine(a, b, c, d, paint);// 画线
			// 線的前面
			circle_44 = BitmapFactory.decodeResource(getResources(),
					R.drawable.circle_44);

			if (Math.abs(DriHandleC - DriHandleA) > 10
					|| Math.abs(DriHandleD - DriHandleB) > 10) {
				// drawAL((int) a, (int) b, (int) c, (int) d);
				float BimWig = circle_44.getWidth() / 2;
				canvas.drawBitmap(circle_44, DriHandleA - BimWig, DriHandleB
						- BimWig, paint);
				canvas.drawBitmap(circle_44, DriHandleC - BimWig, DriHandleD
						- BimWig, paint);
				delete_44 = BitmapFactory.decodeResource(getResources(),
						R.drawable.delete_44);
				// canvas.drawBitmap(delete_44, a-BimWig, b-BimWig, paint);
				canvas.drawBitmap(delete_44, DriHandleC + (BimWig), DriHandleD
						- (BimWig * 3), paint);
			}

		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			String s;
			float eventX = event.getX();
			float eventY = event.getY();
			save.setEnabled(true);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// Log.i("TOP", "ACTION_DOWN");
				// if (PassLine((int) a, (int) b) != 10000) {

				Runing = false;
				RuningClc1 = false;
				RuningClc2 = false;
				a = eventX;
				b = eventY;
				c = eventX;
				d = eventY;

				if (PassPicture((int) a, (int) b, DriHandleC, DriHandleD,
						circle_44)) {
					// 如果第二個圈圈可以被點
					RuningClc2 = true;

				} else if (PassPicture((int) a, (int) b, DriHandleA,
						DriHandleB, circle_44)) {
					// 如果第一個圈圈可以被點
					RuningClc1 = true;

				} else if (PassLine((int) a, (int) b) != 10000) {
					// 這裏說明點下去是線的方法
					// s = Integer.toString(PassLine((int) a, (int) b));
					// Log.i("我是線", s);

					// 取出資料
					reSetNum = PassLine((int) a, (int) b);
					DriHandleA = fistX.get(reSetNum);
					DriHandleB = fistY.get(reSetNum);
					DriHandleC = listX.get(reSetNum);
					DriHandleD = listY.get(reSetNum);
					Runing = false;
				} else {
					// 如果不是點在線上
					listNum = listNum + 1;
					reSetNum = listNum - 1;
					DriHandleA = eventX;
					DriHandleB = eventY;
					DriHandleC = eventX;
					DriHandleD = eventY;
					fistX.add((int) a);
					fistY.add((int) b);
					listX.add((int) c);
					listY.add((int) d);
					Runing = true;
				}

				return true;

			case MotionEvent.ACTION_MOVE:
				// Log.i("TOP", "ACTION_MOVE");

			case MotionEvent.ACTION_UP:
				c = eventX;
				d = eventY;
				if (RuningClc2 == true) {
					// 如果第二個圈圈可以被點
					// 移動點
					DriHandleC = c;
					DriHandleD = d;
					listX.set(reSetNum, (int) c);
					listY.set(reSetNum, (int) d);
				} else if (RuningClc1 == true) {
					// 如果第一個圈圈可以被點
					// 移動點
					DriHandleA = c;
					DriHandleB = d;
					fistX.set(reSetNum, (int) c);
					fistY.set(reSetNum, (int) d);
				} else if (Runing == true) {
					// 如果不是點在線上繪製線
					// c = eventX;
					// d = eventY;
					DriHandleC = c;
					DriHandleD = d;
					listX.set(listNum - 1, (int) c);
					listY.set(listNum - 1, (int) d);
				} else {
					// c = eventX;
					// d = eventY;
					// listX.set(reSetNum, (int) c);
					// listY.set(reSetNum, (int) d);

				}
				break;
			}

			invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
			//
			lastTouchX = eventX;
			lastTouchY = eventY;

			return true;
		}

		/**
		 * 画箭头
		 * 
		 * @param sx
		 * @param sy
		 * @param ex
		 * @param ey
		 */
		public void drawAL(int sx, int sy, int ex, int ey) {
			double H = 8; // 箭头高度
			double L = 3.5; // 底边的一半
			int x3 = 0;
			int y3 = 0;
			int x4 = 0;
			int y4 = 0;
			double awrad = Math.atan(L / H); // 箭头角度
			double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
			double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true,
					arraow_len);
			double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true,
					arraow_len);
			double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
			double y_3 = ey - arrXY_1[1];
			double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
			double y_4 = ey - arrXY_2[1];
			Double X3 = new Double(x_3);
			x3 = X3.intValue();
			Double Y3 = new Double(y_3);
			y3 = Y3.intValue();
			Double X4 = new Double(x_4);
			x4 = X4.intValue();
			Double Y4 = new Double(y_4);
			y4 = Y4.intValue();
			// 画线
			myCanvas.drawLine(sx, sy, ex, ey, paint);
			Path triangle = new Path();
			triangle.moveTo(ex, ey);
			triangle.lineTo(x3, y3);
			triangle.lineTo(x4, y4);
			triangle.close();
			myCanvas.drawPath(triangle, paint);

		}

		// 计算
		public double[] rotateVec(int px, int py, double ang, boolean isChLen,
				double newLen) {
			double mathstr[] = new double[2];
			// 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
			double vx = px * Math.cos(ang) - py * Math.sin(ang);
			double vy = px * Math.sin(ang) + py * Math.cos(ang);
			if (isChLen) {
				double d = Math.sqrt(vx * vx + vy * vy);
				vx = vx / d * newLen;
				vy = vy / d * newLen;
				mathstr[0] = vx;
				mathstr[1] = vy;
			}
			return mathstr;
		}

		// 判斷是不是在直線上
		public boolean tuachLine2(int x, int y, int x2, int y2, int e1, int e2) {
			// 判斷是不是在匡裡500, 400, 100, 40

			if (x > x2) {
				if (e1 > x || e1 < x2) {
					// Log.i("你的Ｘ不對", "哭哭");
					return false;
				}
			} else {
				if (e1 > x2 || e1 < x) {
					// Log.i("你的Ｘ不對", "哭哭");
					return false;
				}
			}
			if (y > y2) {
				if (e2 > y || e2 < y2) {
					// Log.i("你的Ｙ不對", "哭哭");
					return false;
				}
			} else {
				if (e2 > y2 || e2 < y) {
					// Log.i("你的Ｙ不對", "哭哭");
					return false;
				}
			}

			double m = (double) (y - y2) / (double) (x - x2);
			String Ne = Double.toString(m);
			Log.i("算出的斜率", Ne);
			double bI = y - (m * x);// 常數=-50
			Ne = Double.toString(bI);
			Log.i("算出的常數", Ne);
			int e1Arrund1 = e1 + 20;
			int e1Arrund2 = e1 - 20;
			int e2Arrund1 = e2 + 20;
			int e2Arrund2 = e2 - 20;

			// 判斷是不是在斜率上
			// 點擊的上下左右
			for (int i = e1Arrund2; i < e1Arrund1; i++) {
				double Newy = (double) (i * m) + bI;// 現在x上的線上的y點
				int Newy2 = (int) Newy;
				// s = Integer.toString(Newy2);
				// Log.i("要mach到的y", s);
				for (int j = e2Arrund2; j < e2Arrund1; j++) {
					if (j == Newy2) {
						Log.i("在直線上", "哈哈");
						return true;
					}
				}
			}

			// Log.i("你的Ｘy對", "哈哈");

			return false;
		}

		// 撈出過去的線來判斷
		public int PassLine(int e1, int e2) {
			int number = 0;
			for (int i = 0; i < fistX.size(); i++) {
				if (fistX.get(i).equals(listX.get(i))
						&& fistY.get(i).equals(listY.get(i))) {
				} else {
					if (tuachLine2(fistX.get(i), fistY.get(i), listX.get(i),
							listY.get(i), e1, e2)) {
						// 如果是在那條線上，回傳那條線的編號

						String s = Integer.toString(i);
						Log.i("回傳線段編號", s);

						return i;
					}
				}
			}
			number = 10000;
			Log.i("回傳線段編號", "沒有");
			return number;
		}

		// 移動的按到的圖
		public boolean PassPicture(int e1, int e2, float centerX,
				float centerY, Bitmap BitMapThis) {

			float BimWig = BitMapThis.getWidth() / 2;
			int RightSide = (int) centerX - (int) BimWig;
			int LeftSide = (int) centerX + (int) BimWig;
			int UpSide = (int) centerY - (int) BimWig;
			int ButtomSide = (int) centerY + (int) BimWig;
			for (int i = RightSide; i < LeftSide; i++) {
				for (int j = UpSide; j < ButtomSide; j++) {
					if (e1 == i && e2 == j) {
						Log.i("我在這張圖上", "有");
						return true;
					}
				}
			}

			Log.i("我在這張圖上", "沒有");
			return false;

		}
		// private void resetDirtyRect(float eventX, float eventY) {
		// dirtyRect.left = Math.min(lastTouchX, eventX);
		// dirtyRect.right = Math.max(lastTouchX, eventX);
		// dirtyRect.top = Math.min(lastTouchY, eventY);
		// dirtyRect.bottom = Math.max(lastTouchY, eventY);
		// }
	}

}