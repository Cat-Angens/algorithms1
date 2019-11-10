/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int n;
	private boolean sites_blocked[];
	private WeightedQuickUnionUF sites;
	private int opened_bottom_roots[];
	
	private int get_root(int idx){
		
		return sites.find(idx);
	}
	
	private int get_idx_by_ij(int i, int j){
		return j * n + i;
	}
	
	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n_){
		
		if (n_ <= 0)
			throw new IllegalArgumentException ("illegal total points count number");
		
		n = n_;
		sites_blocked = new boolean[n * n];
		sites = new WeightedQuickUnionUF(n * n);
		opened_bottom_roots = new int[n];
		
		for(int idx = 0; idx < n*n; idx++){
			
			sites_blocked[idx] = true;
			
		}
	}
	
	// opens the site (row, col) if it is not open already
	public void open(int row_i_1, int col_i_1){
		
		if (row_i_1 <= 0 || row_i_1 > n)
			throw new IllegalArgumentException ("row index i out of bounds");
		if (col_i_1 <= 0 || col_i_1 > n)
			throw new IllegalArgumentException ("col index i out of bounds");
		
		int row_i = row_i_1 - 1;
		int col_i = col_i_1 - 1;
		
		int idx_i = get_idx_by_ij(col_i, row_i);
		
		if(!sites_blocked[idx_i])
			return;
		
		sites_blocked[idx_i] = false;
		
		int row_j = row_i;
		int col_j = col_i - 1;
		for (int adj_i = 0; adj_i < 4; adj_i++)
		{
			int delta_row = 1 - (adj_i / 2 + adj_i % 2) % 2 * 2;
			int delta_col = 1 - 2 * (adj_i / 2);
			row_j += delta_row;
			col_j += delta_col;
			
			if(row_j < 0 || row_j > n - 1 || col_j < 0 || col_j > n - 1)
				continue;
			
			int idx_j = get_idx_by_ij(col_j, row_j);
			
			if(sites_blocked[idx_j])
				continue;
			
			sites.union(idx_i, idx_j);
		}
	}
	
	// is the site (row, col) open?
	public boolean isOpen(int row_u, int col_u){
		
		if (row_u <= 0 || row_u > n)
			throw new IllegalArgumentException ("row index i out of bounds");
		if (col_u <= 0 || col_u > n)
			throw new IllegalArgumentException ("col index i out of bounds");
		
		int row = row_u - 1;
		int col = col_u - 1;
		
		return !sites_blocked[get_idx_by_ij(col, row)];
	}
	
	// is the site (row, col) full?
	public boolean isFull(int row_u, int col_u){
		
		if (row_u <= 0 || row_u > n)
			throw new IllegalArgumentException ("row index i out of bounds");
		if (col_u <= 0 || col_u > n)
			throw new IllegalArgumentException ("col index i out of bounds");
		
		int row = row_u - 1;
		int col = col_u - 1;
		
		int idx_i = get_idx_by_ij(col, row);
		
		if(sites_blocked[idx_i])
			return false;
		
		for(int ix = 0; ix < n; ix++){
			
			int idx_j = get_idx_by_ij(ix, 0);
			
			if(sites_blocked[idx_j])
				continue;
			
			if (sites.connected(idx_i, idx_j))
				return true;
		}
		
		return false;
	}
	
	// returns the number of open sites
	public int numberOfOpenSites(){
		
		int opens_cnt = 0;
		for(int idx = 0; idx < n*n; idx++){
			
			if (!sites_blocked[idx])
				opens_cnt++;
		}
		return opens_cnt;
	}
	
	// does the system percolate?
	public boolean percolates(){
		
		for(int ix = 0; ix < n; ix++)
		{
			opened_bottom_roots[ix] = -1;
		}
		int opened_bottom_cnt = 0;
		for(int ix = 0; ix < n; ix++){
			
			int idx_bot = get_idx_by_ij(ix, n - 1);
			if (!sites_blocked[idx_bot]){
				
				opened_bottom_roots[opened_bottom_cnt] = sites.find(idx_bot);
				++opened_bottom_cnt;
			}
		}
		if(opened_bottom_cnt == 0)
			return false;
		
		for(int ix = 0; ix < n; ix++){
			
			int idx_top = get_idx_by_ij(ix, 0);
			
			if(!sites_blocked[idx_top]){
				
				int root_top = sites.find(idx_top);
				
				for(int idx_bot_i = 0; idx_bot_i < opened_bottom_cnt; idx_bot_i++){
					
					if(root_top == opened_bottom_roots[idx_bot_i])
						return true;
				}
			}
		}
		
		return false;
	}
	
	private void printf() {
		
		for(int iy = 0; iy < n; iy++)
		{
			for(int ix = 0; ix < n; ix++)
			{
				System.out.printf("%d ", sites_blocked[get_idx_by_ij(ix, iy)] ? 0 : 1);
			}
			System.out.print("\n");
		}
		
		for(int iy = 0; iy < n; iy++)
		{
			for(int ix = 0; ix < n; ix++)
			{
				int idx = get_idx_by_ij(ix, iy);
				System.out.printf("%d ", sites.find(idx));
			}
			System.out.print("\n");
		}
		
	}
	
	// test client (optional)
	public static void main(String[] args) {
		
		Percolation perc = new Percolation(6);
		perc.open(1, 6);
		System.out.printf("isFull(1,6): %d\n", perc.isFull(1, 6) ? 1 : 0);
		System.out.printf("isOpen(1,6): %d\n", perc.isOpen(1, 6) ? 1 : 0);
		
	}
}
