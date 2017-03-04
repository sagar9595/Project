/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stickynotes;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author Sagar
 */
public class StickyNotes {

    private final LinkedList<Note> notes;

    public StickyNotes() {
        this.notes = new LinkedList<>();
    }
    /*Used to delete a note*/
    public void delete(Note note){
        notes.remove(note);
        note.dispose();/*Used to kill the JFrame object properly*/
    }
    /*Used to create new note*/
    Note create(String body){
        Note my_note = new Note(this, body);
        notes.add(my_note); // Adding new created object to LinkedList
        return my_note;
    }
    
    private String readFile(String path, Charset encoding)
    throws IOException{
        byte [] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
    
    private void loadNotes(){
        File notesFolder = new File(System.getProperty("user.home") + "/.sticky_notes/notes");
         notesFolder.mkdirs();
         File[] notesFiles = notesFolder.listFiles();
         
          for(File note : notesFiles) {
            if (note.isFile()) {
                try {
                    create(readFile(note.getPath(), Charset.forName("UTF-8")));
                } catch (IOException ex) {
                    //TODO show error dialog
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "StickyNotes: Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
     private void saveNotes() {
        File notesFolder = new File(System.getProperty("user.home") + "/.sticky_notes/notes");
        notesFolder.mkdirs();
        File[] notesFiles = notesFolder.listFiles();
        
        // deleting old notes
        for(File note : notesFiles) {
            if (note.isFile()) {
                note.delete();
            }
        }
        
        int i = 0;
        for (Note note : this.notes) {
            try {
                PrintWriter writer = new PrintWriter(notesFolder.getPath() + '/' + i++ + ".txt");
                writer.write(note.getText());
                writer.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "StickyNotes: Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
      
    public void exit() {
        System.exit(0);
    }
      public void show_All(){
        for(Note note : notes) { // this is foreach loop, it means : for each "note" as instance of "Note" in the collection "notes"
            // showing the note
            // the Note class is derived from JFrame Class so it can be shown by "setVisible(true)"
            if(note.isVisible()==false)
                note.setVisible(true);
        }
    }
      
       public boolean all_hidden(){
        boolean state = true;
        for(Note note : notes) { // this is foreach loop, it means : for each "note" as instance of "Note" in the collection "notes"
            // showing the note
            // the Note class is derived from JFrame Class so it can be shown by "setVisible(true)"
            if(note.isVisible())
                state=false;
        }
        return state;
    }
     public static void main(String[] args) {
   
        new StickyNotes().run();
    }

    void newNote(String body) {
        this.create(body).setVisible(true);
    }
      public void run() {
        // loading notes
        loadNotes();
        
        if (notes.isEmpty())
            create("");
        
        // running/showing notes
        for(Note note : notes) { // this is foreach loop, it means : for each "note" as instance of "Note" in the collection "notes"
            // showing the note
            // the Note class is derived from JFrame Class so it can be shown by "setVisible(true)"
            note.setVisible(true);
        }
        /*On exit call run which will save all the notes*/
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                saveNotes();
            }
        }));
    }
    
  
}
