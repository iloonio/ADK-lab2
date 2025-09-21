/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;


public class ClosestWordsV3 implements ClosestWordsInterface {

    private LinkedList<String> closestWords = null;
    private int closestDistance = -1;

    public ClosestWordsV3(String w, List<String> wordList) {
        int maximumWordLength = 40; // Given in the assignment
        int wlen = w.length();

        int[][] M = new int[wlen + 1][maximumWordLength + 1];
        for (int i = 0; i <= wlen; i++)
            M[i][0] = i;
        for (int j = 1; j <= maximumWordLength; j++)
            M[0][j] = j;

        String priviousw2 = null;

        for (String w2 : wordList) {
            M = distanceMatrix(w, w2, M, priviousw2);
            int dist = M[wlen][w2.length()];
            if (dist < this.closestDistance || this.closestDistance == -1) {
                this.closestDistance = dist;
                this.closestWords = new LinkedList<String>();
                this.closestWords.add(w2);
            } else if (dist == this.closestDistance) {
                this.closestWords.add(w2);
            }
            priviousw2 = w2;
        }   
    }

    private int[][] distanceMatrix(String w1, String w2, int[][] M, String priviousw2) {
        int w1len = w1.length();
        int w2len = w2.length();
        int numberOfSameLetters = 0;

        if (priviousw2 != null) {
            int minimalLen = Math.min(priviousw2.length(), w2len);
            while (numberOfSameLetters < minimalLen && priviousw2.charAt(numberOfSameLetters) == w2.charAt(numberOfSameLetters)) {
                numberOfSameLetters++;
            }
        }

        for (int i = 1; i <= w1len; i++) {
            for (int j = numberOfSameLetters + 1; j <= w2len; j++) {
                int res = M[i-1][j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);
                int addLetter = M[i-1][j] + 1;
                int deleteLetter = M[i][j-1] + 1;
                M[i][j] = Math.min(res, Math.min(addLetter, deleteLetter));
            }
        }
        return M;
    }

    public int getMinDistance() {
        return this.closestDistance;
    }   

    public List<String> getClosestWords() {
        return this.closestWords;
    }
  

}   