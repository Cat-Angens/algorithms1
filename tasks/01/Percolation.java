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
	public Percolation(int n){
		
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
	public void open(int row_u, int col_u){
		
		int row = row_u - 1;
		int col = col_u - 1;
		
		int idx_i = get_idx_by_ij(row, col);
		
		if(!sites_blocked[idx_i])
			return;
		
		sites_blocked[idx_i] = true;
		group_sizes[idx_i] = 1;
		roots[idx_i] = idx_i;
		for (int i = row - 1; i < row + 2; i+=2)
		{
			
			for (int j = col - 1; j < col + 2; j+=2)
			{
				
				int idx_j = get_idx_by_ij(i, j);
				
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
					roots[idx_j] = root_new;
				}
				else
				{
					root_new = root_j;
					root_old = root_i;
					roots[idx_i] = root_new;
				}
				
				group_sizes[root_new] += group_sizes[root_old];
				group_sizes[root_old] = 0;
				
			}
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
		int opened_bottom_cnt = 0;
		for(int idx = (n - 1) * n; idx < n * n; idx++){
			
			if (!sites_blocked[idx]){
				
				opened_bottom_roots[opened_bottom_cnt] = get_root(idx);
				++opened_bottom_cnt;
			}
		}
		if(opened_bottom_cnt == 0)
			return false;
		
		for(int ix_top = 0; ix_top < n; ix_top++){
			
			if(!sites_blocked[ix_top]){
				
				for(int idx_bot_i = 0; idx_bot_i < opened_bottom_cnt; idx_bot_i++){
					
					int root_bot = opened_bottom_roots[idx_bot_i];
					if(get_root(ix_top) == root_bot)
						return true;
				}
			}
		}
		
		return false;
	}
	
	// test client (optional)
	public static void main(String[] args) {
		
	}
}
