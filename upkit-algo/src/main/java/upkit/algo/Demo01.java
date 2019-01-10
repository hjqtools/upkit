package upkit.algo;

import java.util.Arrays;

public class Demo01 {

	public static void main(String[] args) {
		int[] arr = { 2, 5, 1, 4, 6, 8, 9 ,10,66,123445,123,76 ,11};
		int ou = 0;
		int odd = 0;
		for (int i = 0; i < arr.length; i++) {

			if (arr[i] % 2 == 0) {
//				ou = i;
				if(i <= ou) {
					ou = i;
				}
				continue;
			}

			if (arr[i] % 2 == 1) {
				odd = i;
				
				if(odd > ou) {
					int tmp = arr[ou];
					arr[ou] = arr[odd];
					arr[odd] = tmp;
					
					ou = ou + 1;
				}
				

			}

		}

		System.out.println(Arrays.toString(arr));
	}
}
