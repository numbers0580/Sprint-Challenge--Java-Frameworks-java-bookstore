package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
        List<Book> allBooks = bookService.findAll();
        for(Book b : allBooks) {
            System.out.println("ID: " + b.getBookid() + ", Title: " + b.getTitle() + ", ISBN: " + b.getIsbn());
        }

        List<Section> allSections = sectionService.findAll();
        for(Section s : allSections) {
            System.out.println("ID: " + s.getSectionid() + ", Category: " + s.getName());
        }
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void afindAll() {
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void bfindBookById() {
        assertEquals("Digital Fortress", bookService.findBookById(27).getTitle());
    }

    @Test(expected = EntityNotFoundException.class)
    public void cnotFindBookById() {
        assertEquals("Essentials of Finance", bookService.findBookById(100).getTitle());
    }

    @Test
    public void delete() {
        bookService.delete(30);
    }

    @Test
    public void esave() {
        Book justPublished = new Book("Do You Pongu", "2573956841537", 2020, sectionService.findSectionById(22));
        Book newBook = bookService.save(justPublished);

        assertEquals(newBook.getTitle(), "Do You Pongu");
    }

    @Test
    public void update()
    {
    }

    @Test
    public void deleteAll()
    {
    }
}