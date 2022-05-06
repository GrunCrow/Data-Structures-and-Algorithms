public class Drawer {
	private static void drawLine(int n, char ch) {
		for(int i=0;i<n;i++) {
			System.out.println(ch); 
		}

	}


	public static void drawPyramid(int n) {
		int k = 0;
		
		for (int i = 1; i <= n; ++i, k = 0) {
			
		      for (int space = 1; space <= n - i; ++space) {
		        System.out.print("  ");
		      }

		      while (k != 2 * i - 1) {
		    	  System.out.print("X ");
		    	  ++k;
		      }

		      System.out.println();
		}
	}
	
	
	
	
	
	
	

	
	public static void drawChristmassTree(int n) {
		int k = 0;
		
		for(int j = 1; j <= n; ++j) { //loop for the different pyramids
			
			for (int i = 1; i <= j; ++i, k = 0) { //loop for the height
				
			      for (int space = 1; space <= n - i; ++space) { //loop for the spaces and 'X's
			    	  											 //change j for n so it will set the middle of each
			    	  											//pyramid in the middle of the last one
			    	  System.out.print("  ");
			      }
	
			      while (k != 2 * i - 1) {
			    	  System.out.print("X ");
			    	  ++k;
			      }
	
			      System.out.println();
			}
		}
	}

}
