/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private int trials_cnt;
	private double perc_frac_threshold[];
	
	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials)
	{
		if (n <= 0)
			throw new IllegalArgumentException ("illegal total points count number");
		if (trials <= 0)
			throw new IllegalArgumentException ("illegal total trials count number");
		
		// StdRandom.setSeed(74);
		trials_cnt = trials;
		perc_frac_threshold = new double[trials];
		
		for(int it = 0; it < trials_cnt; ++it){
			
			Percolation perc_model = new Percolation(n);
			
			// int sites_cnt = 0;
			int opened_cnt = 0;
			while (!perc_model.percolates()) {
				
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				perc_model.open(row, col);
				// ++sites_cnt;
				opened_cnt = perc_model.numberOfOpenSites();
//				System.out.printf("Opened %d points (tried %d times)\n", opened_cnt, sites_cnt);
			}
			perc_frac_threshold[it] = (double) opened_cnt / n / n;
			// System.out.printf("Calculated %d experiments\n", it + 1);
		}
	}
	
	// sample mean of percolation threshold
	public double mean()
	{
		return StdStats.mean(perc_frac_threshold);
	}
	
	// sample standard deviation of percolation threshold
	public double stddev()
	{
		return StdStats.stddev(perc_frac_threshold);
	}
	
	// low endpoint of 95% confidence interval
	public double confidenceLo()
	{
		return mean() - 1.96 * stddev() /  Math.sqrt(trials_cnt);
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi()
	{
		return mean() + 1.96 * stddev() /  Math.sqrt(trials_cnt);
	}
	
	// test client (see below)
	public static void main(String[] args)
	{
		int grid_size = Integer.parseInt(args[0]);
		int trials_cnt = Integer.parseInt(args[1]);
		
		PercolationStats perc_stats = new PercolationStats(grid_size, trials_cnt);
		System.out.printf("mean                    = %.16f\n", perc_stats.mean());
		System.out.printf("stddev                  = %.16f\n", perc_stats.stddev());
		System.out.printf("95%% confidence interval = [%.16f, %.16f]\n", perc_stats.confidenceLo(),
		                  perc_stats.confidenceHi());
		
	}
}
