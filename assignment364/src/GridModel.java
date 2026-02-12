import java.beans.PropertyChangeSupport;
public class GridModel extends PropertyChangeSupport {
    private int size;
    private CellType[][] grid;

    private int sr = -1; // start row
    private int sc = -1; // start column
    private int er = -1; // end row
    private int ec = -1; // end colummn

    private boolean running = false;
    private boolean paused = false;
    private boolean stop = false;

    private final Object pauseLock = new Object();
    public GridModel(int initialSize) {
        super(new Object());
        reset(initialSize);
    }

    public synchronized int getSize() {
        return size;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized boolean hasStart() {
        return sr >= 0;
    }

    public synchronized boolean hasEnd() {
        return er >= 0;
    }

    public synchronized CellType getCell(int r, int c) {
        return grid[r][c];
    }

    public synchronized int getStartR() {
        return sr;
    }

    public synchronized int getStartC() {
        return sc;
    }

    public synchronized int getEndR() {
        return er;
    }

    public synchronized int getEndC() {
        return ec;
    }

    public synchronized void reset(int newSize) {
        stop = true;
        paused = false;
        running = false;
        notifyPause();

        size = newSize;
        grid = new CellType[size][size];
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                grid[r][c] = CellType.EMPTY;

        sr = -1;
        sc = -1;
        er = -1;
        ec = -1;

        stop = false;
        firePropertyChange("grid", null, null);
    }

    public synchronized void clearSearchMarks() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++){
                CellType t = grid[r][c];
                if (t == CellType.FRONTIER || t == CellType.VISITED || t == CellType.PATH) {
                    grid[r][c] = CellType.EMPTY;
                }
            }
        }
        if (hasStart()) grid[sr][sc] = CellType.START;
        if (hasEnd()) grid[er][ec] = CellType.END;
        firePropertyChange("grid", null, null);
    }

    public synchronized void setStart(int r, int c) {
        if (!inBounds(r,c)) 
            return;
        if (grid[r][c] == CellType.OBSTACLE) 
            return;
        if (r == er && c == ec) 
            return;
        if (hasStart()) 
            grid[sr][sc] = CellType.EMPTY;
        sr = r;
        sc = c;
        grid[r][c] = CellType.START;
        firePropertyChange("grid", null, null);
    }

    public synchronized void setEnd(int r, int c) {
        if (!inBounds(r,c)) 
            return;
        if (grid[r][c] == CellType.OBSTACLE) 
            return;
        if (r == sr && c == sc) 
            return;
        if (hasEnd()) 
            grid[er][ec] = CellType.EMPTY;
        er = r;
        ec = c;
        grid[r][c] = CellType.END;
        firePropertyChange("grid", null, null);
    }

    public synchronized void toggleObstacle(int r, int c) {
        if (!inBounds(r,c)) 
            return;
        if (r == sr && c == sc) 
            return;
        if (r == er && c == ec) 
            return;
        grid[r][c] = (grid[r][c] == CellType.OBSTACLE) ? CellType.EMPTY : CellType.OBSTACLE;
        firePropertyChange("grid", null, null);
    }

    public synchronized void startRun() {
        stop = false;
        paused = false;
        running = true;
        firePropertyChange("grid", null, null);
    }

    public synchronized void finishRun() {
        running = false;
        firePropertyChange("grid", null, null);
    }

    public synchronized void requestStop() {
        stop = true;
        paused = false;
        notifyPause();
    }

    public synchronized boolean shouldStop() { 
        return stop; 
    }

    public synchronized void togglePause() {
        if (!running) 
            return;
        paused = !paused;
        notifyPause();
        firePropertyChange("grid", null, null);
    }

    public void waitIfPaused() throws InterruptedException {
        synchronized (pauseLock) {
            while (true) {
                synchronized (this) {
                    if (!paused) return;
                    if (stop) return;
                }
                pauseLock.wait();
            }
        }
    }

    private void notifyPause() {
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public synchronized void markFrontier(int r, int c) {
        if (grid[r][c] == CellType.EMPTY) {
            grid[r][c] = CellType.FRONTIER;
            firePropertyChange("grid", null, null);
        }
    }

    public synchronized void markVisited(int r, int c) {
        if (grid[r][c] == CellType.FRONTIER || grid[r][c] == CellType.EMPTY) {
            grid[r][c] = CellType.VISITED;
            firePropertyChange("grid", null, null);
        }
    }

    public synchronized void markPath(int r, int c) {
        if ((r == sr && c == sc) || (r == er && c == ec)) return;
        if (grid[r][c] != CellType.OBSTACLE) {
            grid[r][c] = CellType.PATH;
            firePropertyChange("grid", null, null);
        }
    }

    public synchronized boolean walkable(int r, int c) {
        return inBounds(r,c) && grid[r][c] != CellType.OBSTACLE;
    }

    private synchronized boolean inBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }


}
