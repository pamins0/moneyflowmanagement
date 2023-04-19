package com.emorphis.cashmanagement.util;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		int array[] = { 4, 5, 8, 7, 5, 8, 7 };
		int subArray[] = { 4, 8, 7 };
		int rtn=checkArray(array, subArray);
		System.out.println("Return is ::"+rtn);
	}

	private static int checkArray(int[] array, int[] subArray) {
		List<Integer> list = new ArrayList<>();
		List<Integer> subList = new ArrayList<>();

		for (int a : array) {
			list.add(a);
		}

		for (int b : subArray) {
			subList.add(b);
		}

		boolean flag = list.containsAll(subList);

		System.out.println("flag::" + flag);
		if (flag = true) {
			int index=-1;
			int start=-1;
			int last=-1;
			for (int s : subList) {
				
				index= list.indexOf(s);
				if(start==-1){
					start=index;
				}else{
					if(start+1==index){
						start=index;
					}else{
						return -1;
					}
				}
				System.out.println("Index :::" + index);
			}
			return index;
		} else {

			return -1;
		}
	}
}
