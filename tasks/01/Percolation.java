/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
	
	private int n;
	private boolean sites_blocked[];
	private int roots[];
	private int group_sizes[];
	
	private int get_root(int idx){
		
		while(idx != roots[idx]){
			
			roots[idx] = roots[roots[idx]];
			idx = roots[idx];
		}
		return idx;
	}
	
	private int get_idx_by_ij(int i, int j){
		return j * n + i;
	}
	
	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n_){
		
		n = n_;
		sites_blocked = new boolean[n * n];
		roots = new int[n * n];
		group_sizes = new int[n * n];
		
		for(int idx = 0; idx < n*n; idx++){
			
			sites_blocked[idx] = true;
			roots[idx] = idx;
			group_sizes[idx] = 0;
		}
	}
	
	// opens the site (row, col) if it is not open already
	public void open(int row_i_1, int col_i_1){
		
		int row_i = row_i_1 - 1;
		int col_i = col_i_1 - 1;
		
		int idx_i = get_idx_by_ij(row_i, col_i);
		
		if(!sites_blocked[idx_i])
			return;
		
		sites_blocked[idx_i] = false;
		group_sizes[idx_i] = 1;
		roots[idx_i] = idx_i;
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
			
			int idx_j = get_idx_by_ij(row_j, col_j);
				
			if(sites_blocked[idx_j])
				continue;
			
			int root_i = get_root(idx_i);
			int root_j = get_root(idx_j);
			
			if(root_i == root_j)
				continue;
			
			int size_i = group_sizes[root_i];
			int size_j = group_sizes[root_j];
			
			int root_new, root_old;
			if(size_i > size_j)
			{
				root_new = root_i;
				root_old = root_j;
				roots[root_j] = root_new;
			}
			else
			{
				root_new = root_j;
				root_old = root_i;
				roots[root_i] = root_new;
			}
			
			group_sizes[root_new] += group_sizes[root_old];
			group_sizes[root_old] = 0;
		}
	}
	
	// is the site (row, col) open?
	public boolean isOpen(int row_u, int col_u){
		
		int row = row_u - 1;
		int col = col_u - 1;
		
		return !sites_blocked[get_idx_by_ij(row, col)];
	}
	
	// is the site (row, col) full?
	public boolean isFull(int row_u, int col_u){
		
		int row = row_u - 1;
		int col = col_u - 1;
		
		int idx_i = get_idx_by_ij(row, col);
		int root_i = get_root(idx_i);
		for(int ix = 0; ix < n; ix++){
			
			if (get_root(ix) == root_i)
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
		
		int opened_bottom_roots[] = new int[n];
		for(int ix = 0; ix < n; ix++)
		{
			opened_bottom_roots[ix] = -1;
		}
		int opened_bottom_cnt = 0;
		for(int ix = 0; ix < n; ix++){
			
			int idx = get_idx_by_ij(ix, n - 1);
			if (!sites_blocked[idx]){
				
				opened_bottom_roots[opened_bottom_cnt] = get_root(idx);
				++opened_bottom_cnt;
			}
		}
		if(opened_bottom_cnt == 0)
			return false;
		
		for(int ix_top = 0; ix_top < n; ix_top++){
			
			int idx_top = get_idx_by_ij(ix_top, 0);
			if(!sites_blocked[idx_top]){
				
				for(int idx_bot_i = 0; idx_bot_i < opened_bottom_cnt; idx_bot_i++){
					
					int root_bot = opened_bottom_roots[idx_bot_i];
					if(get_root(idx_top) == root_bot)
						return true;
				}
			}
		}
		
		return false;
	}
	
	public void printf(String filename) {
		
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
				System.out.printf("%d ", get_root(get_idx_by_ij(ix, iy)));
			}
			System.out.print("\n");
		}
		
	}
	
	// test client (optional)
	public static void main(String[] args) {
		
	}
}
