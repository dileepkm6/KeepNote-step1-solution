package com.stackroute.keepnote.controller;


import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.repository.NoteRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/*Annotate the class with @Controller annotation. @Controller annotation is used to mark
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 * */
@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application
	 * requires us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the collection. Each note 
	 *    should contain Note Id, title, content, status and created date.
	 * 2. Add a new note which should contain the note id, title, content and status.
	 * 3. Delete an existing note.
	 * 4. Update an existing note.
	 */
	
	/* 
	 * Get the application context from resources/beans.xml file using ClassPathXmlApplicationContext() class.
	 * Retrieve the Note object from the context.
	 * Retrieve the NoteRepository object from the context.
	 */
	 ApplicationContext applicationContext=new ClassPathXmlApplicationContext("beans.xml");
	 Note note=applicationContext.getBean("note",Note.class);
	 NoteRepository noteRepository=applicationContext.getBean("noteRepository",NoteRepository.class);
	
	/*Define a handler method to read the existing notes by calling the getAllNotes() method 
	 * of the NoteRepository class and add it to the ModelMap which is an implementation of Map 
	 * for use when building model data for use with views. it should map to the default URL i.e. "/" */
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String readNotes(ModelMap modelMap)
	{
		modelMap.addAttribute("noteList",noteRepository.getAllNotes());
		return "index";
	}

	
	/*Define a handler method which will read the Note data from request parameters and
	 * save the note by calling the addNote() method of NoteRepository class. Please note 
	 * that the createdAt field should always be auto populated with system time and should not be accepted 
	 * from the user. Also, after saving the note, it should show the same along with existing 
	 * notes. Hence, reading notes has to be done here again and the retrieved notes object 
	 * should be sent back to the view using ModelMap.
	 * This handler method should map to the URL "/saveNote". 
	*/
	@PostMapping("/saveNote")
	public String retrieveNotes(HttpServletRequest request,ModelMap modelMap)
	{
		try
        {
            int noteId=Integer.parseInt(request.getParameter("noteId"));
            String noteTitle=request.getParameter("noteTitle");
            String noteContent=request.getParameter("noteContent");
            String noteStatus=request.getParameter("noteStatus");
            Note note=new Note();
            note.setNoteId(noteId);
            note.setNoteTitle(noteTitle);
            note.setNoteContent(noteContent);
            note.setNoteStatus(noteStatus);
            note.setCreatedAt(LocalDateTime.now());
            noteRepository.addNote(note);
            List<Note> arrayList=noteRepository.getAllNotes();
			Collections.sort(arrayList, new Comparator<Note>() {
				public int compare(Note note1, Note note2) {
					return (-1)*note1.getCreatedAt().compareTo(note2.getCreatedAt());
				}
			});
            modelMap.addAttribute("noteList",arrayList);
        }
	    catch (NumberFormatException e)
        {
            modelMap.addAttribute("numberFormatExc","Please provide integer value ");
        }
		return "index";
	}
	
	
	/* Define a handler method to delete an existing note by calling the deleteNote() method 
	 * of the NoteRepository class
	 * This handler method should map to the URL "/deleteNote" 
	*/
	@GetMapping("/deleteNote")
	public String deletingNote(HttpServletRequest request,ModelMap modelMap)
	{
		int noteId=Integer.parseInt(request.getParameter("noteId"));
		noteRepository.deleteNote(noteId);
		modelMap.addAttribute("noteList",noteRepository.getAllNotes());
		return "redirect:"+"/";
	}
}