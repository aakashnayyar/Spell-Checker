package cs146F19.Nayyar.project4;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;


public class RBTTester {
	File dictionary = new File("/Users/aakash/Desktop/dictionary.txt");
	File poem = new File("/Users/aakash/Desktop/poem.txt"); 
	//RedBlackTree rbtDictionary = makeDictionary(dictionary);
	@Test
    //Test the Red Black Tree
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));  
	}
    
	@Test
	public void testAtoZ() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("A");
        rbt.insert("B");
        rbt.insert("C"); 
        rbt.insert("D");  
        rbt.insert("E");
        rbt.insert("F");
        rbt.insert("G");
        rbt.insert("H");
        rbt.insert("I");
        rbt.insert("J");
        rbt.insert("K");
        rbt.insert("L");
        rbt.insert("M");
        rbt.insert("N");
        rbt.insert("O");
        rbt.insert("P");
        rbt.insert("Q");
        rbt.insert("R");
        rbt.insert("S");
        rbt.insert("T");
        rbt.insert("U");
        rbt.insert("V");
        rbt.insert("W");
        rbt.insert("X");
        rbt.insert("Y");
        rbt.insert("Z");
        assertEquals("HDBACFEGPLJIKNMOTRQSVUXWYZ", makeString(rbt));
        String str =  
        			"Color: 1, Key:H Parent: \n" +
        			"Color: 1, Key:D Parent: H\n" +
        			"Color: 1, Key:B Parent: D\n" +
        			"Color: 1, Key:A Parent: B\n" +
        			"Color: 1, Key:C Parent: B\n" +
        			"Color: 1, Key:F Parent: D\n" +
        			"Color: 1, Key:E Parent: F\n" +
        			"Color: 1, Key:G Parent: F\n" +
        			"Color: 1, Key:P Parent: H\n" +
        			"Color: 0, Key:L Parent: P\n" +
        			"Color: 1, Key:J Parent: L\n" +
        			"Color: 1, Key:I Parent: J\n" +
        			"Color: 1, Key:K Parent: J\n" +
        			"Color: 1, Key:N Parent: L\n" +
        			"Color: 1, Key:M Parent: N\n" +
        			"Color: 1, Key:O Parent: N\n" +
        			"Color: 0, Key:T Parent: P\n"+
        			"Color: 1, Key:R Parent: T\n"+
        			"Color: 1, Key:Q Parent: R\n"+
        			"Color: 1, Key:S Parent: R\n"+
        			"Color: 1, Key:V Parent: T\n"+
        			"Color: 1, Key:U Parent: V\n"+
        			"Color: 0, Key:X Parent: V\n"+
        			"Color: 1, Key:W Parent: X\n"+
        			"Color: 1, Key:Y Parent: X\n"+
        			"Color: 0, Key:Z Parent: Y\n";
       assertEquals(str, makeStringDetails(rbt));
            
    }
	
	@Test
	public void test1node() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("A");
		assertEquals("A", makeString(rbt));
	}
	
	@Test
	public void test3nodes() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("A");
        rbt.insert("B");
        rbt.insert("C"); 
        assertEquals("BAC", makeString(rbt));
        String str =  
    			"Color: 1, Key:B Parent: \n" +
    			"Color: 0, Key:A Parent: B\n" +
    			"Color: 0, Key:C Parent: B\n";
        assertEquals(str, makeStringDetails(rbt));
	}

	@Test
	public void testPoem() throws IOException {
		long time = System.nanoTime();
		RedBlackTree rbtDictionary = makeDictionary(dictionary);
		time = System.nanoTime() - time; // make dictionary time
		System.out.println("Time to create RBT of Dictionary: " + time);
		ArrayList<String> words = poemToArr(poem);
		time = time - System.nanoTime();
		long totalTime = 0;
		int counter = 0;
		for(String word: words) {
			time = System.nanoTime();
			RedBlackTree.Node<String> wordInDictionary = rbtDictionary.lookup(word);
			time = System.nanoTime()-time; // time to look up word
			totalTime +=time;
			if(wordInDictionary!=null) {
				if(word.compareTo(wordInDictionary.key) == 0) { 
					assertEquals(word, wordInDictionary.key);
					System.out.println("Spelled Correctly: " + word); // print out the words that are correct
				}
			}
			else {
				System.out.println("Mispelled: " + word);
			}
			System.out.println("Time to look up word: " + time);
			counter++;
		}
		System.out.println("Average time to look up word: " + totalTime/counter);
	}
	
	/**
	 * Convert a poem into an ArrayList with all the words in the poem
	 * @param f a text file of the poem
	 * @return an array list including all words in the poem
	 * @throws IOException if file not found
	 */
	public ArrayList<String> poemToArr(File f) throws IOException {
		ArrayList <String> arr = new ArrayList();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine())!=null) {
			String[] str = line.split(" ");// splits the line up by word
			for(String s: str) { // add each word into an array list
				arr.add(s);
			}
		}
		return arr;
		
	}
	
	/**
	 * Converts a dictionary into a RBT
	 * @param f text file which includes all words in the dictionary
	 * @return a RBT with all words in the dictionary
	 * @throws IOException if file not found
	 */
	public RedBlackTree makeDictionary(File f) throws IOException {
		RedBlackTree rbt = new RedBlackTree();
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine())!=null) {
			rbt.insert(line); // each line is a word, so go through each line and add it to RBT
		}
		return rbt;
	}
    
	//provided
    public static String makeString(RedBlackTree t)
    {
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }
    
    
    // provided
    public static String makeStringDetails(RedBlackTree t) {
    	{
    	       class MyVisitor implements RedBlackTree.Visitor {
    	          String result = "";
    	          public void visit(RedBlackTree.Node n)
    	          {
    	        	  if(n.parent!=null&&!(n.key).equals(""))
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+ "\n";
    	        	  else {
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+""+"\n";
    	        	  }
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result; 
    	 }
    }

 }
  
