package event;

import Charactor.*;

public class Event {
			public static boolean checkHit(Sheep sheep,Wave wave,int sheepSize,int waveHeight){
							if(sheep.x+sheepSize>wave.x&&sheep.x<wave.x) {
								if(sheep.y+sheepSize>=wave.y-waveHeight) {
									return true;
								}
							}
							return false;
			}
			
			public static void gameStop(Wave[] wave,Environment[] env) {

			}

}
