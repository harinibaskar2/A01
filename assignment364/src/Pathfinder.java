import java.util.ArrayDeque;
public class Pathfinder implements Runnable {
    private final GridModel model;

    public Pathfinder(GridModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        if (!model.hasStart() || !model.hasEnd()) return;

        model.clearSearchMarks();
        model.startRun();

        int n = model.getSize();
        int sr = model.getStartR();
        int sc = model.getStartC();
        int er = model.getEndR();
        int ec = model.getEndC();

        boolean[][] seen = new boolean[n][n];
        int[][] pr = new int[n][n];
        int[][] pc = new int[n][n];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                pr[r][c] = -1;
                pc[r][c] = -1;
            }
        }
        
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr, sc});
        seen[sr][sc] = true;

        boolean found = false;
        while (!q.isEmpty()) {
            if (model.shouldStop()) 
                break;

            try { 
                model.waitIfPaused(); 
            }
            catch (InterruptedException e) { 
                break; 
            }

            int[] cur = q.removeFirst();
            int r = cur[0], c = cur[1];

            if (!(r == sr && c == sc) && !(r == er && c == ec)) {
                model.markVisited(r, c);
            }

            if (r == er && c == ec) { found = true; break; }

            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= n || nc < 0 || nc >= n) 
                    continue;
                if (seen[nr][nc]) 
                    continue;
                if (!model.walkable(nr, nc)) 
                    continue;
                seen[nr][nc] = true;
                pr[nr][nc] = r;
                pc[nr][nc] = c;

                if (!(nr == er && nc == ec) && !(nr == sr && nc == sc)) {
                    model.markFrontier(nr, nc);
                }

                q.addLast(new int[]{nr, nc});

            } try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                break;
            }

        }

        if (found && !model.shouldStop()) {
                int r = er;
                int c = ec;
                while (!(r == sr && c == sc)) {
                    int rr = pr[r][c], cc = pc[r][c];
                if (rr == -1) 
                    break;
                if (!(r == er && c == ec)) 
                    model.markPath(r, c);
                r = rr; c = cc;
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ignored) {}
                }
            }
            model.finishRun();

    }
}
