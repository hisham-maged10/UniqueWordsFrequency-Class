/*
...@Author Hisham Maged
*/
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
public class WordFrequency
{
	private HashMap<String,Integer> uniqueWords;
	private int maxWordCount;
	private int totalWordsCount;
	public WordFrequency()
	{
		fillUniqueWords(getFile());
		this.maxWordCount=findMaxValue();
	}
	public WordFrequency(File file)
	{
		fillUniqueWords(file);
		this.maxWordCount=findMaxValue();
	}
	public WordFrequency(String path)
	{
		fillUniqueWords((new File(path)).isFile()?new File(path):getFile());
		this.maxWordCount=findMaxValue();
	}
	public int getTotalWordsCount()
	{
		return this.totalWordsCount;
	}
	public void printTotalWordsCount()
	{
		System.out.println("Total words in the file: "+getTotalWordsCount());
	}
	public int getCountOfMaxWord()
	{
		return this.maxWordCount;
	}
	public String getMaxWord()
	{
		for(String e:uniqueWords.keySet())
			if(uniqueWords.get(e)==this.maxWordCount)
				return e;
		return null;
	}
	public int getUniqueWordsCount()
	{
		return this.uniqueWords.size();	
	}
	public void printUniqueWordsCount()
	{
		System.out.println("Number of Unique words in file: "+getUniqueWordsCount());
	}
	public void printUniqueWordsAndCount()
	{
		for(String e:uniqueWords.keySet())
			System.out.println(uniqueWords.get(e)+ " "+e);
	}
	public void printMaxWord()
	{
		System.out.println("The word that occurs most often and its count are: "+getMaxWord()+" "+getCountOfMaxWord());
	}
	private  File getFile()
	{
		JFileChooser chooser=new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		try{
		do
		{
			System.out.println("Please choose a file");
			chooser.showOpenDialog(null);
		}while(chooser.getSelectedFile() == null);
		}catch(NullPointerException ex){System.out.println("Incorrect Response!"); return getFile();}
		return chooser.getSelectedFile();
	}
	private  String[] getWords(File source)
	{
		ArrayList<String> words=new ArrayList<>();
		try(BufferedReader input=new BufferedReader(new FileReader(source)))
		{
			String line=null;
			while((line=input.readLine())!=null)
			{
				//String[] tokens=removeNonLetters(line.trim().split("\\W+"));
				String[] tokens=line.trim().split(" ");
				this.totalWordsCount=words.size();
				words.addAll(Arrays.asList(tokens));
			}
		}catch(IOException ex){ex.printStackTrace();}
		return words.toArray(new String[words.size()]);
	}
	private String[] removeNonLetters(String[] tokens)
	{
			int j=0;
			for(String e:tokens)tokens[j++]=removePunctuation(e);
			return tokens;
	}
	private  String removePunctuation(String str)
	{
		StringBuilder sb=new StringBuilder();
		for(int i=0,n=str.length();i<n;i++)
		{
			if(Character.isLetter(str.charAt(i)))
			sb.append(str.charAt(i));
			else continue;
		}
		return sb.toString();
	}
	
	private void fillUniqueWords(File file)
	{
		String[] words=getWords(file);
		uniqueWords=new HashMap<String,Integer>();
		for(String e:words)
		{
			if(e.equals("") || e.equals(" "))continue;
			if(!uniqueWords.containsKey(e.toLowerCase()))
				uniqueWords.put(e.toLowerCase(),1);
			else
				uniqueWords.put(e.toLowerCase(),uniqueWords.get(e.toLowerCase())+1);
		}
	}
	public int findMaxValue()
	{
		return Collections.max(uniqueWords.values());
	}


}