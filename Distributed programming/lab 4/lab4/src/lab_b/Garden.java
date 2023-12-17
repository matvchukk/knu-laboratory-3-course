package lab_b;

public class Garden {
    private final String[][] condition;


    public Garden(int sizeX, int sizeY) {
        condition = new String[sizeX][sizeY];
        for (var section : condition) {
            for (int y = 0; y < getGardenHeight(); y++) {
                section[y] = "1";
            }
        }
    }


    public void gardenIsWithering() {
        for (var section : condition) {
            for (int y = 0; y < getGardenHeight(); y++) {
                if(Math.random() > 0.7){
                    section[y] = "0";
                }
            }
        }
    }

    public void gardenIsBlooming() {
        for (var section : condition) {
            for (int y = 0; y < getGardenHeight(); y++) {
                if (section[y] == "0") {
                    section[y] = "1";
                }
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int y = 0; y < getGardenHeight(); y++) {
            for (int x = 0; x < getGardenWidth(); x++) {
                str.append(" ").append(condition[x][y]).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public int getGardenWidth() {
        return condition.length;
    }

    public int getGardenHeight() {
        return condition[0].length;
    }
}
