/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static java.lang.Math.sqrt;

public class PercolationStats {
	
	private int trials_cnt;
	private int perc_nn[];
	
	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials)
	{
		StdRandom.setSeed(74);
		trials_cnt = trials;
		perc_nn = new int[trials];
		
		for(int it = 0; it < trials_cnt; ++it){
			
			Percolation perc_model = new Percolation(n);
			
			int sites_cnt = 0;
			while (!perc_model.percolates()) {
				
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				perc_model.open(row, col);
				++sites_cnt;
				System.out.printf("Opened %d point\n", sites_cnt);
			}
			perc_nn[it] = sites_cnt;
			System.out.printf("Calculated %d experiments\n", it);
		}
	}
	
	// sample mean of percolation threshold
	public double mean()
	{
		// double sum_nn = 0.0;
		// for (int nn : perc_nn)
		// {
		// 	sum_nn += nn;
		// }
		// return sum_nn / trials_cnt;
		return StdStats.mean(perc_nn);
	}
	
	// sample standard deviation of percolation threshold
	public double stddev()
	{
		// double mean_nn = mean();
		// double stddev_nn = 0.0;
		// for (int nn : perc_nn)
		// {
		// 	stddev_nn += (nn - mean_nn) * (nn - mean_nn);  
		// }
		// stddev_nn /= trials_cnt;
		//
		// return stddev_nn;
		return StdStats.stddev(perc_nn);
	}
	
	// low endpoint of 95% confidence interval
	public double confidenceLo()
	{
		return mean() - 1.96 * stddev() / sqrt(trials_cnt);
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi()
	{
		return mean() + 1.96 * stddev() / sqrt(trials_cnt);
	}
	
	// test client (see below)
	public static void main(String[] args)
	{
		int grid_size = Integer.parseInt(args[0]);
		int trials_cnt = Integer.parseInt(args[1]);
		
		PercolationStats perc_stats = new PercolationStats(grid_size, trials_cnt);
		System.out.printf("mean                    = %.16f\n", perc_stats.mean());
		System.out.printf("stddev                  = %.16f\n", perc_stats.mean());
		System.out.printf("95%% confidence interval = [%.16f, %.16f]\n", perc_stats.confidenceLo(),
		                  perc_stats.confidenceHi());
		
	}
}
