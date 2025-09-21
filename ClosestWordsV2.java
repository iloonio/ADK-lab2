/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWordsV2 implements ClosestWordsInterface {
  private LinkedList<String> closestWords = null;

  private int closestDistance = -1;

  private int partDist(String w1, String w2, int w1len, int w2len) {
    int[][] M = new int[w1len + 1][w2len + 1];
    for (int i = 0; i <= w1len; i++)
      M[i][0] = i;
    for (int j = 0; j <= w2len; j++)
      M[0][j] = j;
      
    for (int i = 1; i <= w1len; i++) {
      for (int j = 1; j <= w2len; j++) {
        int res = M[i-1][j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);
        int addLetter = M[i-1][j] + 1;
        int deleteLetter = M[i][j-1] + 1;
        M[i][j] = Math.min(res, Math.min(addLetter, deleteLetter));
      }
    }
    return M[w1len][w2len];
} 

  int distance(String w1, String w2) {
    //return partDist(w1, w2, w1.length(), w2.length());
    return partDist(w1, w2, w1.length(), w2.length());
  }

  public ClosestWordsV2(String w, List<String> wordList) {
    for (String s : wordList) {
      int dist = distance(w, s);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      } else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  public int getMinDistance() {
    return closestDistance;
  }

  public List<String> getClosestWords() {
    return closestWords;
  }
}
