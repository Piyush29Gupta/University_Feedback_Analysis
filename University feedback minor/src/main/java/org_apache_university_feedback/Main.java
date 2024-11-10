package org_apache_university_feedback;

import com.opencsv.CSVReader;
import java.io.FileReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.PropertiesUtils;

import java.io.IOException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;


class Csv{

    private CSVReader file;

    private String[] line;

    private String header;

    public Csv() throws Exception{
        String csv_path = "C:\\Users\\Piyush Gupta\\OneDrive\\Desktop\\University feedback minor\\src\\main\\java\\org_apache_university_feedback\\College_Feedback.csv";


        file = new CSVReader(new FileReader(csv_path));

        line = file.readNext();

        header = line[0];

    }

    public String Get_data() throws Exception{
        line = file.readNext();

        if(line != null){
            return line[0];
        }

        return null;
    }

    public void csv_close() throws Exception{

        if(file!=null){
            file.close();
        }

    }


}

public class Main {
    public static void main(String[] args) throws Exception {

        try{
            Csv csv_file = new Csv();

            String dataString;

            while((dataString = csv_file.Get_data())!=null){

                // PRINT EACH ROW
//                System.out.println("ROW DATA : " + dataString);

                String remove_stopword_dataString = Perform_stopword_remove(dataString);

                // PRINT AFTER STOP WORD REMOVE
//                System.out.println("DATA AFTER STOP WORD REMOVE : " + remove_stopword_dataString);

                String lemmatization_dataString = perform_Lemmatization(remove_stopword_dataString);

                // PRINT AFTER LEMMATIZATION
//                System.out.println("DATA AFTER LEMMATIZATION : " + lemmatization_dataString);

//                System.out.println("\n \n");



            }




            csv_file.csv_close();

        }

        catch(Exception e){
            System.out.println("FOUND AN EXCEPTION");
        }

        
    }

    public static String Perform_stopword_remove(String line) throws Exception{

        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList("a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are",
                "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both",
                "but", "by", "can", "can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does",
                "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further",
                "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's",
                "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i",
                "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself",
                "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off",
                "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over",
                "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so",
                "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves",
                "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've",
                "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't",
                "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when",
                "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's",
                "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your",
                "yours", "yourself", "yourselves"
        ));

        ArrayList<String> removeStopWordsList = new ArrayList<>();


        String[] words = line.split("\\s");

        for(String word : words){
            String lowerCaseWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

            if(!stopWords.contains(lowerCaseWord)){
                removeStopWordsList.add(word);
            }

        }


        return String.join(" ",removeStopWordsList);



    }

    public static String perform_Lemmatization(String text) {

        StanfordCoreNLP pipeline = new StanfordCoreNLP(PropertiesUtils.asProperties(
                "annotators", "tokenize,ssplit,pos,lemma",
                "ssplit.isOneSentence", "true",
                "tokenize.language", "en"));

        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        StringBuilder lemmatized_Text = new StringBuilder();
        for (CoreLabel token : document.tokens()) {
            lemmatized_Text.append(token.get(CoreAnnotations.LemmaAnnotation.class)).append(" ");
        }

        return lemmatized_Text.toString().trim();
    }

//    public static int result(String text){
//
//        String[] positive_words = {
//                "happy", "joyful", "excited", "optimistic", "wonderful", "fantastic",
//                "amazing", "brilliant", "delightful", "pleasure", "inspiring",
//                "awesome", "magnificent", "grateful", "positive", "charming",
//                "successful", "determined", "loving", "peaceful", "gracious",
//                "hopeful", "creative", "caring", "uplifting", "accomplished",
//                "compassionate", "motivated", "kind", "friendly", "affectionate",
//                "generous", "thankful", "radiant", "productive", "committed",
//                "strong", "energetic", "courageous", "balanced", "brave",
//                "beautiful", "respectful", "supportive", "enthusiastic",
//                "resourceful", "loyal", "trustworthy", "confident", "sincere",
//                "harmonious", "joyous", "peaceful", "ambitious", "satisfied",
//                "flourishing", "achieving", "funny", "humorous", "affluent",
//                "prosperous", "fabulous", "thoughtful", "noble", "dynamic",
//                "spirited", "motivating", "wise", "intelligent", "reliable",
//                "innovative", "considerate", "blessed", "genuine", "heartfelt",
//                "cheerful", "eager", "gentle", "industrious", "worthy", "enlightened",
//                "hearty", "enthusiastic", "unselfish", "selfless", "heartwarming",
//                "praiseworthy", "bountiful", "exuberant", "lively", "supportive",
//                "nurturing", "motivating", "talented", "graceful", "conscientious",
//                "self-confident", "dedicated", "elevated", "compelling", "uplifting"
//        };
//
//        String[] negative_words = {
//                "sad", "angry", "disappointed", "miserable", "depressed", "hopeless",
//                "unhappy", "terrible", "awful", "horrible", "disgusting", "displeased",
//                "frustrated", "resentful", "guilty", "gloomy", "angst", "negative",
//                "painful", "irritated", "defeated", "pessimistic", "dismal", "distressed",
//                "unmotivated", "apathetic", "lonely", "embarrassed", "unfortunate",
//                "desperate", "unsuccessful", "ungrateful", "bitter", "isolated", "foolish",
//                "ashamed", "heartbroken", "unfortunate", "ugly", "doubtful", "aggressive",
//                "sorrowful", "jealous", "dejected", "helpless", "desolate", "rejected",
//                "mournful", "disillusioned", "shameful", "sickening", "apathetic",
//                "miserable", "hopeless", "unlucky", "unsatisfied", "irritating", "nervous",
//                "chaotic", "disastrous", "frustrating", "inferior", "despairing", "abandoned",
//                "melancholy", "unimportant", "disconnected", "unworthy", "untrustworthy",
//                "annoyed", "hostile", "unsettled", "troubled", "outcast", "ugly", "unpleasant",
//                "annoying", "resentful", "destructive", "unreliable", "pessimism", "revolting",
//                "stressed", "untrustworthy", "doubtful", "chaotic", "intolerable", "exasperated",
//                "disastrous", "regretful", "suspicious", "unhappy", "worried", "troubled"
//        };
//
//        String[] words = text.split(" ");
//
//        for(String word : words){
////            if(positive_words.contain)
//        }
//
//
//    }



}


// I COMMENTED IT BECAUSE STEMMING IS GIVING AGGRESSIVELY RESULT SO I CHOOSE TO PERFORM STOP WORD REMOVE AND LEMMATIZATION ONLY
/*
public static String Perform_stopword_Stemming(String data) throws Exception{

        Analyzer eng_analyzer = new EnglishAnalyzer();

        ArrayList<String> stopword_stemming = new ArrayList<>();

        var token_stemming = eng_analyzer.tokenStream("field", new StringReader(data));

        CharTermAttribute each_token = token_stemming.addAttribute(CharTermAttribute.class);

        token_stemming.reset();

        while(token_stemming.incrementToken()){

         stopword_stemming.add(each_token.toString());

        }

        eng_analyzer.close();
        token_stemming.close();

        return String.join(" ",stopword_stemming); //.trim()

    }
 */



