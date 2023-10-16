package task_a;

public class Forest {
    public int[][] forestArea;
    public final Integer sizeOfForest;
    private int rowOfPooh;
    private int columnOfPooh;

    Forest () {
        this.sizeOfForest = (int)(Math.random() * 100);
        this.forestArea = new int[this.sizeOfForest][this.sizeOfForest];
        this.rowOfPooh = (int)(Math.random() * this.sizeOfForest);
        this.columnOfPooh = (int)(Math.random() * this.sizeOfForest);
        this.fillForestArea();
    }

    private void fillForestArea () {
        for(int i = 0; i < sizeOfForest; i++){
            for(int j = 0; j < sizeOfForest; j++) {
                forestArea[i][j] = 0;
            }
        }

        forestArea[rowOfPooh][columnOfPooh] = 1;
        System.out.println("Pooh is in row: " + rowOfPooh + " column: " + columnOfPooh);
    }

}