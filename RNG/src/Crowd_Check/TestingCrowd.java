package Crowd_Check;

public class TestingCrowd {

	public static void main(String[] args) {
		for(int j = 0; j < 100; j++) {
			
			int delayIndex = 0;
			int[] low = {3,5,2,3};
			int[] high = {7,8,8,6};
			int frame = 0;
			
			int startFrame = 1000;
			boolean[] data = new boolean[2000];
			while(frame < 1200) {
				for(int i = frame; i <= frame + 14; i += 2) {
					data[i] = true;
				}
				frame += 14;
				if(Math.random() < 0.5) {
					frame += low[delayIndex];
				}else {
					frame += high[delayIndex];
				}
				delayIndex++;
				if(delayIndex == 4) {
					delayIndex = 0;
				}
			}
			for(int i = 1000; i < 1100; i++) {
				System.out.print(data[i] ? "1," : "0,");
			}
			System.out.println();
		}
	}		

}
