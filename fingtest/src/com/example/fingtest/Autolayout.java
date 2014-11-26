package com.example.fingtest;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

public class Autolayout {
	
	
	
	public void MyActivitySize(Activity asss){
		DisplayMetrics metrics = new DisplayMetrics();
		asss.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		double width=metrics.widthPixels;
		double height=metrics.heightPixels;
	}
	public void MyViewSize(View MyView){
		
	}
	/**
	 * MyAndroidProportionalForActivity 根據螢幕大小設置（長）＆（寬） asss＝Activity
	 * MyView=要被改變的view Pwidth=佔螢幕的寬比利 （for example Pwidth=0.5） Pheigh＝“”
	 * */
	public void MyAndroidProportionalForActivity(Activity asss, View MyView,
			double Pwidth, double Pheigh) {
		DisplayMetrics metrics = new DisplayMetrics();
		asss.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		if (Pwidth != 0) {
			MyView.getLayoutParams().width = (int) (metrics.widthPixels * Pwidth);
		}
		if (Pheigh != 0) {
			MyView.getLayoutParams().height = (int) (metrics.heightPixels * Pheigh);
		}
	}


	/**
	 * MyAndroidProportionalForView 根據父view大小設置（長）＆（寬）小心他要做完一次才執行 asss＝Activity
	 * MyView=要被改變的view Pwidth=佔父view的寬比利 （for example Pwidth=0.5） Pheigh＝“”
	 * */
	public void MyAndroidProportionalForView(Activity asss, View ParentView,
			View MyView, double Pwidth, double Pheigh) {
		int ViewWidth = ParentView.getWidth();
		int ViewHeight = ParentView.getHeight();
		if (Pwidth != 0) {
			MyView.getLayoutParams().width = (int) (ViewWidth * Pwidth);
		}
		if (Pheigh != 0) {
			MyView.getLayoutParams().height = (int) (ViewHeight * Pheigh);
		}

	}

	// 長寬比根據寬
	public void UntiToUnitProportionalForActivity(Activity asss, View MyView,
			double Pwidth, int MyWigth, int MyHeigh) {
		DisplayMetrics metrics = new DisplayMetrics();
		asss.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		if (Pwidth != 0) {
			int pa = (int) MyHeigh / MyWigth;
			//
			MyView.getLayoutParams().width = (int) (metrics.widthPixels * Pwidth);
			// MyView.getLayoutParams().height=MyView.getLayoutParams().width;

			MyView.getLayoutParams().height = (int) ((metrics.widthPixels * Pwidth) * pa);

		}
		// if(Pheigh!=0){
		// MyView.getLayoutParams().height=(int) (metrics.heightPixels*Pheigh);}
	}

	// 長寬比根據高
	public void UntiToUnitHeightForActivity(Activity asss, View MyView,
			double Pheight, int MyWigth, int MyHeigh) {
		DisplayMetrics metrics = new DisplayMetrics();
		asss.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		if (Pheight != 0) {
			int pa = (int) MyWigth / MyHeigh;
			//
			MyView.getLayoutParams().height = (int) (metrics.heightPixels
					* Pheight / 5);
			MyView.getLayoutParams().width = (int) ((metrics.heightPixels
					* Pheight / 5) * pa);
			// MyView.getLayoutParams().height=MyView.getLayoutParams().width;

			// MyView.getLayoutParams().height=(int)((metrics.widthPixels*Pwidth)*pa);

		}
		// if(Pheigh!=0){
		// MyView.getLayoutParams().height=(int) (metrics.heightPixels*Pheigh);}
	}
	
	/**
	 * 回傳螢幕大小（寬） asss＝Activity
	 * MyView=要被改變的view Pwidth=佔螢幕的寬比利 （for example Pwidth=0.5） 
	 * */
	public int MyAndroidreturnpadd(Activity asss, 
			double Pwidth) {
		DisplayMetrics metrics = new DisplayMetrics();
		asss.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		return (int) (metrics.widthPixels * Pwidth);
		
	}

}
