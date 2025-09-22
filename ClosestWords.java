/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

/**
 * ClosestWords can be improved by storing pre-calculated values in a matrix.
 * Also, the base cases where either one of the strings is empty can be autofilled, skipping
 * pointless recursions.
 * initial step -> check for word len, append matrix with values on [0,n] and [m, 0].
 * final step ->  compare [m,n] between words.
 */
public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

    /**
     * WagnerFischer uses flood filling to compute distances, which is
     * more efficient than the pre-existing recursive approach. More can be read
     * here: <a href="https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm">Wagner-Fischer Algorithm</a>
     * @param w1 word 1
     * @param w2 word 2, typically from a dictionary given by ClosestWords
     * @return a distance matrix for word 1 and 2.
     */
    int wagnerFischerDistance(String w1, String w2) {
        //first, prep matrix. In java, matrices are initialized with a default value (unlike C++)
        int[][] d = new int[w1.length()+1][w2.length()+1];
        for (int i = 0; i <= w1.length(); i++) d[i][0] = i;
        for (int j = 0; j <= w2.length(); j++) d[0][j] = j;

        for (int i = 1; i <= w1.length(); i++) {
            for (int j = 1; j <= w2.length(); j++) {
                int cost = (w1.charAt(i-1) == w2.charAt(j-1)) ? 0 : 1;    //trinary op to save space & memory B)
                d[i][j] = Math.min(Math.min(
                                        d[i-1][j] + 1,      //addition
                                        d[i][j-1] + 1),     //deletion
                                        d[i-1][j-1] + cost  //substitution
                );
            }
        }

        return d[w1.length()][w2.length()];
    }

  int distance(String w1, String w2) {
    return wagnerFischerDistance(w1, w2);
    //return partDist(w1, w2, w1.length(), w2.length());
  }

  public ClosestWords(String w, List<String> wordList) {
    for (String s : wordList) {
      int dist = distance(w, s);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
